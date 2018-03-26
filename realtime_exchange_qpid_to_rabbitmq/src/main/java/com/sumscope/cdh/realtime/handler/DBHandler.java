package com.sumscope.cdh.realtime.handler;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.mapper.BondMapper;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class DBHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBHandler.class);

    @Autowired
    private BondMapper bondMapper;

    private List<HashMap<String, Object>> bondBuffer = new ArrayList<>();

    @Override
    void doEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {
        bondBuffer.addAll(event.getTarget());

        if(endOfBatch || bondBuffer.size() > 100)
        {
            try
            {
                bondMapper.insertBondModel(bondBuffer);
                LOGGER.info("insert SH bond size=>{}",bondBuffer.size());
            }
            catch (Exception e)
            {
                LOGGER.error("insert SH bond error.exception=>{},message=>{}",e.getMessage(), JSON.toJSONString(bondBuffer));
            }
            finally
            {
                bondBuffer.clear();
            }
        }
    }
}
