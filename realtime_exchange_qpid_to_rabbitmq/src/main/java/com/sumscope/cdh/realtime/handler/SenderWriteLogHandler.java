package com.sumscope.cdh.realtime.handler;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import com.sumscope.cdh.sumscopemq4j.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class SenderWriteLogHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenderWriteLogHandler.class);

    @Autowired
    private Sender sender;

    private List<HashMap<String, Object>> bondBuffer = new ArrayList<>();

    @Override
    void doEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {

        bondBuffer.addAll(event.getTarget());

        if(endOfBatch || bondBuffer.size() > 500)
        {
            try
            {
                sender.send(JSON.toJSONString(bondBuffer).getBytes(Charset.forName("UTF-8")));
                LOGGER.info("send bond success.size=>{}",bondBuffer.size());
                LOGGER.info("send {} results:",bondBuffer.size());
                bondBuffer.forEach(msg ->
                {
                    if(StringUtils.equals(String.valueOf(msg.get("market")),"18515"))    {
                        LOGGER.info("SH => {}",JSON.toJSONString(msg));
                    }else if(StringUtils.equals(String.valueOf(msg.get("market")),"23123")){
                        LOGGER.info("SZ => {}",JSON.toJSONString(msg));
                    }else{
                        LOGGER.info("NoType => {}",JSON.toJSONString(msg));
                    }
                });
            }
            catch (Exception e)
            {
                LOGGER.error("send bond error. exception=>{}  message=>{}",e.getMessage(), JSON.toJSONString(bondBuffer));
            }
            finally
            {
                bondBuffer.clear();
            }
        }
    }
}
