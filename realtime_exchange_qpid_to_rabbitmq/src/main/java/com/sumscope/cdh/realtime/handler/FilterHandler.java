package com.sumscope.cdh.realtime.handler;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.common.RestfulUtil;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class FilterHandler implements EventHandler<BondEventModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandler.class);

    private static final String SH = "18515";
    private static final String SZ = "23123";
    private static String TYPE = "";

    @Value("${logging.level.write.log}")
    private String isWriteLog;
    @Autowired
    private RestfulUtil restfulUtil;

    @Override
    public void onEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {
        if(event.getSource().get("List") == null || !(event.getSource().get("List") instanceof ArrayList))
        {
            LOGGER.error("the message is illegal format.{}", JSON.toJSONString(event.getSource()));
            event.setFlag(false);
            return;
        }

        try
        {
            ArrayList<HashMap<String,Object>> list = (ArrayList)event.getSource().get("List");
            LOGGER.info("source message size =>{}",list.size());
            StringBuilder sb = new StringBuilder();
            List<HashMap<String, Object>> collect = list.stream().filter((bond) ->
            {
                boolean flag = false;

                Object _market = bond.get("Market") == null ? bond.get("market") : bond.get("Market");
                String market = String.valueOf(_market);

                if(SH.equals(market))
                {
                    TYPE = "SH";
                    Object _code = bond.get("Code") == null ? bond.get("code") : bond.get("Code");
                    String code = sb.append(_code).append(".SH").toString();
                    if(restfulUtil.containsKey(code))
                    {
                        flag = true;
                        bond.put("bondCode",code);
                    }
                }
                else if(SZ.equals(market))
                {
                    TYPE = "SZ";
                    Object _code = bond.get("Code") == null ? bond.get("code") : bond.get("Code");
                    String code = sb.append(_code).append(".SZ").toString();
                    if(restfulUtil.containsKey(code))
                    {
                        flag = true;
                        bond.put("bondCode",code);
                    }
                }
                else
                {
                    LOGGER.info("message do not come from SH or SZ.Market={}",bond.get("Market"));
                }

                sb.delete(0,sb.length());
                return flag;
            }).collect(Collectors.toList());

            if(collect.size() == 0)
            {
                event.setFlag(false);
                return;
            }
            LOGGER.info("filter {} result list size =>{}",TYPE,collect.size());
            event.setTarget(collect);
        }
        catch (Exception e)
        {
            LOGGER.error("filterHandler catch error.exception=>{}",e.getMessage());
        }
    }
}
