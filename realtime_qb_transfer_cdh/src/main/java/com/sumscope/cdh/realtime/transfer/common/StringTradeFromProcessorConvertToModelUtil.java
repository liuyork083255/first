package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.db.SourceTradeDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.TargetTradeDBModel;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.message.SourceTradeMessageModel;
import com.sumscope.cdh.realtime.transfer.restful.RestfulUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是处理从 QB 接入的 trade 行情
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class StringTradeFromProcessorConvertToModelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTradeFromProcessorConvertToModelUtil.class);

    @Autowired
    private RestfulUtil restfulUtil;

    public void convert(SourceEventModel eventModel){
        try{
            String _message = eventModel.getMessage();
            List<TargetTradeDBModel> sourceTradeMessageModel = JSON.parseArray(_message, TargetTradeDBModel.class);
            eventModel.setTargetTradeDBModelList(getDBModel(sourceTradeMessageModel));
        }catch (Exception e){
            LOGGER.error(String.format("catch string convert to trade model exception.exception=>[%s]   message=>[%s]",e.getMessage(),eventModel.getMessage()));
            eventModel.setFlag(false);
        }
    }

    private List<TargetTradeDBModel> getDBModel(List<TargetTradeDBModel> sourceTradeMessageModel){
        if(sourceTradeMessageModel == null || sourceTradeMessageModel.size() == 0)
            throw new RuntimeException("sourceTradeMessageModel size is 0 or null");

        sourceTradeMessageModel.forEach((source) -> {

            source.setBusinessCode(restfulUtil.getBusinessCode(source.getBondKey() + "_" + source.getListedMarket()));
        });
        return sourceTradeMessageModel;
    }
}


















