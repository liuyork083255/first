package com.sumscope.cdh.webbond.qpid;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.webbond.utils.ICdhRestfulConfig;
import com.sumscope.cdh.webbond.utils.LogTool;
import com.sumscope.cdh.webbond.model.Bond;
import com.sumscope.cdh.webbond.utils.BondsTool;
import com.sumscope.cdh.webbond.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class BondQpidConsumer implements Consumer<String>
{

    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    private final Set<Bond> bonds;
    private final JsonNode dictionaryRootNode;
    private final ICdhRestfulConfig config;

    public BondQpidConsumer(ICdhRestfulConfig config, Set<Bond> bonds, JsonNode dictionaryRootNode)
    {
        this.bonds = bonds;
        this.dictionaryRootNode = dictionaryRootNode;
        this.config = config;
    }

    // accept will be called when received a qpid message...
    @Override
    public void accept(String message)
    {
        Utils.executeWithTimeout(() ->
        {
            List<String> bondIds;
            try
            {
                bondIds = getUpdatedListFromMessage(message);
                if (bondIds == null || bondIds.isEmpty()) {
                    return true;
                }
                HashMap<String, Bond> bondMap = new HashMap();
                BondsTool.updateBondsWithCodes(config, bondMap, dictionaryRootNode, bondIds);
                for (Bond bond : bondMap.values())
                {
                    bonds.remove(bond);
                    bonds.add(bond);
                }
                logger.info("BondQpidConsumer: update bond's count: " + Integer.toString(bondIds.size()));
                logger.info("Now bond's count is: " + bonds.size());
                return true;
            }
            catch (Exception e)
            {
                LogTool.logException(e, "BondQpidConsumer: failed to fetch updated-bonds");
                return false;
            }
        }, 10, TimeUnit.MINUTES);

    }

    static List<String> getUpdatedListFromMessage(String message)
            throws Exception
    {

        List<String> bondIds = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        String[] tags = {"UpdatedRecords", "AddedRecords"};
        for (String tag : tags)
        {
            JsonNode updatedItems = mapper.readTree(message).path(tag);
            for (JsonNode updatedItem : updatedItems)
            {
                bondIds.add(updatedItem.path("Bond_Key").asText());
            }
        }
        if (bondIds.size() == 0)
        {
            logger.info("BondQpidConsumer: No bond IDs returned from bond-updated-notification");
            return null;
        }
        return bondIds;
    }
}

