package com.sumscope.cdh.webbond;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.generated.WebbondTrade;
import com.sumscope.cdh.webbond.request.BondFilter;
import com.sumscope.cdh.webbond.model.*;
import com.sumscope.cdh.webbond.qpid.BondQpidConsumer;
import com.sumscope.cdh.webbond.utils.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Repository
{
    private static final Logger logger = LoggerFactory.getLogger(Repository.class);

    private final RestLoadTool RESTLOADTOOL = new RestLoadTool();

    private volatile IndexedCollection<Bond> indexedBonds = new ConcurrentIndexedCollection<>();

    private final Map<String, WebbondBbo> bboMap = new ConcurrentHashMap<>();
    private final Set<WebbondBbo> bboSet = new ConcurrentSkipListSet<>((bbo1, bbo2) ->
    {
        int result = ObjectUtils.compare(bbo2.getUpdateTime(), bbo1.getUpdateTime());
        if (result != 0)
        {
            return result;
        }
        else
        {
            String bbo1Key = String.format("%s.%s.%s", bbo1.getBondKey(), bbo1.getListedMarket(), bbo1.getBrokerId());
            String bbo2Key = String.format("%s.%s.%s", bbo2.getBondKey(), bbo2.getListedMarket(), bbo2.getBrokerId());
            result = ObjectUtils.compare(bbo2Key, bbo1Key);
            if (result != 0)
            {
                return result;
            }
            else
            {
                return 0;
            }
        }
    });

    private final Map<String, WebbondTrade> tradeMap = new ConcurrentHashMap<>();
    private final Set<WebbondTrade> tradeSet = new ConcurrentSkipListSet<>((trade1, trade2) ->
    {
        int result = ObjectUtils.compare(trade2.getCreateDateTime(), trade1.getCreateDateTime());
        if (result != 0)
        {
            return result;
        }
        else
        {
            String trade1Key = String.format("%s.%s.%s", trade1.getBondKey(), trade1.getListedMarket(), trade1.getTradeId());
            String trade2Key = String.format("%s.%s.%s", trade2.getBondKey(), trade2.getListedMarket(), trade2.getTradeId());
            result = ObjectUtils.compare(trade2Key, trade1Key);
            if (result != 0)
            {
                return result;
            }
            else
            {
                return 0;
            }
        }
    });

    private volatile Map<String, UserAccount> userAccounts = new ConcurrentHashMap<>();

    private ObjectMapper mapper;
    private ObjectNode staticDictionaryRootNode;
    private ObjectNode dictionaryRootNode = null;
    private String dictionaryStr;

    @Autowired
    private Config config;

    public Map<String, UserAccount> getUserAccounts()
    {
        return userAccounts;
    }

    public Map<String, WebbondBbo> getBboMap()
    {
        return bboMap;
    }

    public Set<WebbondBbo> getSortedBboSet()
    {
        return bboSet;
    }

    public Map<String, WebbondTrade> getTradeMap()
    {
        return tradeMap;
    }

    public Set<WebbondTrade> getSortedTradeSet()
    {
        return tradeSet;
    }

    public Repository()
    {
        ObjectNode staticDictionaryRootNode1;
        mapper = JsonTool.createObjectMapper();
        try
        {
            staticDictionaryRootNode1 = ModuleResources.getStaticDictionary(mapper);
        }
        catch (IOException e)
        {
            staticDictionaryRootNode1 = null;
            e.printStackTrace();
        }
        staticDictionaryRootNode = staticDictionaryRootNode1;
    }

    @PostConstruct
    private void start() throws Exception
    {
        generateIndexedBonds(indexedBonds);
        reload(RepoReloadFlag.ALL);
        registerQpidBondNotify();
    }

    public void reload(RepoReloadFlag flags) throws Exception
    {
        if (flags.contains(RepoReloadFlag.DICTIONARY))
        {
            ObjectNode newRootNode = staticDictionaryRootNode.deepCopy();

            try (TimeMeasure timeMeasure = new TimeMeasure("Generate dictionary"))
            {
                DictionaryTool.generateDictionary(config, mapper, newRootNode);
            }

            synchronized (this)
            {
                dictionaryRootNode = newRootNode;
                dictionaryStr = JsonTool.nodeToString(mapper, dictionaryRootNode);
            }
        }

        if (flags.contains(RepoReloadFlag.BONDS))
        {
            if (dictionaryRootNode == null)
                reload(RepoReloadFlag.DICTIONARY);
            Map<String, Bond> newBondMap = new HashMap<>();
            BondsTool.generateBonds(config, newBondMap, dictionaryRootNode);
            for (Bond bond : newBondMap.values())
            {
                indexedBonds.remove(bond);
                indexedBonds.add(bond);
            }
            logger.info("reload bond sizes: {}", indexedBonds.size());
        }

        if (flags.contains(RepoReloadFlag.BESTQUOTES))
        {
            RESTLOADTOOL.loadData(WebbondBbo.class,
                    "application.restful.api.webbond_bbo",
                    (WebbondBbo bbo) -> String.format("%s.%s.%s", bbo.getBondKey(), bbo.getListedMarket(), bbo.getBrokerId()),
                    bboMap,
                    config);
            bboSet.addAll(bboMap.values());
            logger.info("load bbo size {}", bboMap.size());
        }

        if (flags.contains(RepoReloadFlag.USER_ACCOUNTS))
        {
            this.userAccounts = UserAccountsTool.generateUserAccounts(config);
            logger.info("Generated user accounts sizes: {}", userAccounts.size());
        }

        if (flags.contains(RepoReloadFlag.TRADESTODAY))
        {
            RESTLOADTOOL.loadData(WebbondTrade.class, "application.restful.api.webbond_trade",
                    (WebbondTrade trade) -> String.format("%s.%s.%s", trade.getBondKey(), trade.getListedMarket(), trade.getTradeId()),
                    tradeMap, config);
            tradeSet.addAll(tradeMap.values());
            logger.info("load trade size {}", tradeMap.size());
        }

    }

    private void generateIndexedBonds(IndexedCollection<Bond> indexedBonds) throws Exception
    {
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SUB_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SUB_TYPE_LEVEL1));
        indexedBonds.addIndex(NavigableIndex.onAttribute(Bond.BOND_RESIDUAL_MATURITY));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_RESIDUAL_MATURITY_LABEL));
        indexedBonds.addIndex(NavigableIndex.onAttribute(Bond.BOND_MATURITY_DATE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_PERPETUAL_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_CODE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_MUNICIPAL_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_RATING));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_RATING));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_RATING_AUGMENT));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_FINANCIAL_BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_CORPORATE_BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_COUPON_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_OPTION_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SECTOR));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SECTOR_LEVEL1));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_PROVINCE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_YEAR));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SPECIAL_TYPES));
    }

    private void registerQpidBondNotify()
    {
        // qpid for bonds-updating-notify
        ExecutorService qpidBondNotifierExecutor = Executors.newSingleThreadExecutor();
        QpidReceiver qpidReceiver = new QpidReceiver(config,
                new BondQpidConsumer(config, indexedBonds, dictionaryRootNode));
        qpidBondNotifierExecutor.submit(qpidReceiver);
    }

    public Collection<String> filterBonds(BondFilter filter)
    {
        return BondFilterTool.filterBonds(indexedBonds, filter);
    }

    public Collection<Bond> searchBonds(BondSearchRequest bondSearchRequest)
    {
        String lowerCaseText = bondSearchRequest.getSearchText() != null ? bondSearchRequest.getSearchText().toLowerCase() : "";
        TreeMap<Integer, Bond> treeMap = new TreeMap<>();
        int i = 0;
        for (Bond bond : indexedBonds)
        {
            if (StringUtils.contains(bond.bondCode, "X"))
            {
                continue;
            }
            if (bond.bondCode == null ? false : bond.bondCode.toLowerCase().contains(lowerCaseText))
            {
                treeMap.put(0 + i, bond);
                i++;
            }
            else if (bond.short_name == null ? false : bond.short_name.toLowerCase().contains(lowerCaseText))
            {
                treeMap.put(10 + i, bond);
                i++;
            }
            else if (bond.pinyin == null ? false : bond.pinyin.toLowerCase().contains(lowerCaseText))
            {
                treeMap.put(20 + i, bond);
                i++;
            }
            else if (bond.pinyin_full == null ? false : bond.pinyin_full.toLowerCase().contains(lowerCaseText))
            {
                treeMap.put(30 + i, bond);
                i++;
            }
            if (treeMap.size() == 8)
            {
                break;
            }

        }
        return treeMap.values();
    }

    public String getDictionaryString()
    {
        return dictionaryStr;
    }

    public void clearCache()
    {
        indexedBonds.clear();
        userAccounts.clear();
        bboMap.clear();
        bboSet.clear();
        tradeMap.clear();
        tradeSet.clear();
    }

}
