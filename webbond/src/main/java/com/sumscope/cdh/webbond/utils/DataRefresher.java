package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.sumscopemq4j.MqReceiverCallback;
import com.sumscope.cdh.webbond.Config;
import com.sumscope.cdh.webbond.MqManager;
import com.sumscope.cdh.webbond.RepoReloadFlag;
import com.sumscope.cdh.webbond.Repository;
import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.generated.WebbondTrade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by chengzhang.wang on 2017/3/3.
 */
@Component
@EnableScheduling
public class DataRefresher
{
    static final Logger logger = LoggerFactory.getLogger(DataRefresher.class);

    @Autowired
    private Repository repo;

    @Autowired
    private Config config;

    private final ObjectMapper mapper = JsonTool.createObjectMapper();

    @Autowired
    private MqManager mqManager;

    //每小时reload一次静态数据
    @Scheduled(cron = "0 0 * * * *")
    private void reloadTask()
    {
        Utils.executeWithTimeout(() ->
        {
            try
            {
                logger.info("reload static info");
                repo.reload(RepoReloadFlag.DICTIONARY);
                repo.reload(RepoReloadFlag.BONDS);
                return true;
            }
            catch (Exception e)
            {
                logger.error("reload static information failed", e);
                return false;
            }
        }, 10, TimeUnit.MINUTES);
    }

    //每5分钟reload一次账户数据
    @Scheduled(cron = "0 0/5 * * * *")
    private void reloadAccount()
    {
        Utils.executeWithTimeout(() ->
        {
            try
            {
                logger.info("reload account info");
                repo.reload(RepoReloadFlag.USER_ACCOUNTS);
                return true;
            }
            catch (Exception e)
            {
                logger.error("reload static information failed", e);
                return false;
            }
        }, 5, TimeUnit.MINUTES);
    }

    //每晚0点清空缓存
    @Scheduled(cron = "0 0 0 * * *")
    private void clearTask()
    {
        logger.info("clear repository cache");
        repo.clearCache();
    }

    @PostConstruct
    private void start() throws IOException
    {
        mqManager.getReceiver(config.getRabbitmqBboInputExchangeName(), config.getRabbitmqBboInputExchangeType(), new MqReceiverCallback()
        {
            @Override
            public boolean processString(String s)
            {
                return false;
            }

            @Override
            public boolean processBytes(byte[] bytes)
            {
                try
                {
                    logger.debug("Received bbo message: {}", new String(bytes));
                    WebbondBbo newWebbondBbo = mapper.readValue(bytes, WebbondBbo.class);
                    String bboKey = String.format("%s.%s.%s", newWebbondBbo.getBondKey(), newWebbondBbo.getListedMarket(), newWebbondBbo.getBrokerId());
                    WebbondBbo toUpdateBbo = repo.getBboMap().get(bboKey);
                    if (null == toUpdateBbo)
                    {
                        repo.getSortedBboSet().add(newWebbondBbo);
                        repo.getBboMap().put(bboKey, newWebbondBbo);
                    }
                    else
                    {
                        repo.getSortedBboSet().remove(toUpdateBbo);
                        repo.getSortedBboSet().add(newWebbondBbo);
                        repo.getBboMap().put(bboKey, newWebbondBbo);
                    }
                }
                catch (IOException e)
                {
                    logger.error("got exception", e);
                }
                return false;
            }
        }, true).receive();
        mqManager.getReceiver(config.getRabbitmqTradeFilterInputExchangeName(), config.getRabbitmqTradeFilterInputExchangeType(), new MqReceiverCallback()
        {
            @Override
            public boolean processString(String s)
            {
                return false;
            }

            @Override
            public boolean processBytes(byte[] bytes)
            {
                try
                {
                    logger.debug("Received trade message: {}", new String(bytes));
                    List<WebbondTrade> webbondTrades = mapper.readValue(bytes, mapper.getTypeFactory().constructCollectionType(List.class, WebbondTrade.class));
                    for (WebbondTrade entity : webbondTrades)
                    {
                        String key = String.format("%s.%s.%s", entity.getBondKey(), entity.getListedMarket(), entity.getTradeId());
                        if (StringUtils.equals(entity.getTransType(), "delete"))
                        {
                            WebbondTrade toDeleteTrade = repo.getTradeMap().get(key);
                            if (null != toDeleteTrade)
                            {
                                repo.getSortedTradeSet().remove(toDeleteTrade);
                                repo.getTradeMap().remove(key);
                            }
                        }
                        else
                        {
                            WebbondTrade toUpdateTrade = repo.getTradeMap().get(key);
                            if (null == toUpdateTrade)
                            {
                                repo.getSortedTradeSet().add(entity);
                                repo.getTradeMap().put(key, entity);
                            }
                            else
                            {
                                repo.getSortedTradeSet().remove(toUpdateTrade);
                                repo.getSortedTradeSet().add(entity);
                                repo.getTradeMap().put(key, entity);
                            }
                        }
                    }
                }
                catch (IOException e)
                {
                    logger.error("got exception", e);
                }
                return false;
            }
        }, true).receive();
    }
}
