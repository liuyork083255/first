package com.sumscope.cdh.realtime.handler;

import com.sumscope.cdh.realtime.common.RestfulUtil;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class UpdateFieldHandler extends AbstractHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFieldHandler.class);

    @Autowired
    private RestfulUtil restfulUtil;

    @Override
    void doEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {
        event.getTarget().forEach((bond) ->
        {
            try {
                String key = (String)bond.get("bondCode");
                HashMap<String, String> value = restfulUtil.getValue(key);
                bond.put("bondKey",value.get("BondKey"));
                bond.put("listedMarket",value.get("ListedMarket"));

                bond.put("highestPrice",bond.get("HighestPrice"));
                bond.remove("HighestPrice");
                bond.put("openPrice",bond.get("OpenPrice"));
                bond.remove("OpenPrice");
                bond.put("preClosePrice",bond.get("PreClosePrice"));
                bond.remove("PreClosePrice");
                bond.put("updateTime",bond.get("UpdateTime"));
                bond.remove("UpdateTime");
                bond.put("updateMillisec",bond.get("UpdateMillisec"));
                bond.remove("UpdateMillisec");
                bond.put("volume",bond.get("Volume"));
                bond.remove("Volume");
                bond.put("closePrice",bond.get("ClosePrice"));
                bond.remove("ClosePrice");
                bond.put("name",bond.get("Name"));
                bond.remove("Name");
                bond.put("tradingDay",bond.get("TradingDay"));
                bond.remove("TradingDay");
                bond.put("openInterest",bond.get("OpenInterest"));
                bond.remove("OpenInterest");
                bond.put("lowestPrice",bond.get("LowestPrice"));
                bond.remove("LowestPrice");
                bond.put("turnover",bond.get("Turnover"));
                bond.remove("Turnover");
                bond.put("code",bond.get("Code"));
                bond.remove("Code");
                bond.put("lastPrice",bond.get("LastPrice"));
                bond.remove("LastPrice");
                bond.put("market",bond.get("Market"));
                bond.remove("Market");

                bond.put("brokerId","e");

            } catch (Exception e) {
                LOGGER.error("FillFieldHandler error.exception=>{}",e.getMessage());
            }
        });
    }
}
