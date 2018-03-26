package com.sumscope.cdh.realtime.transfer.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import com.sumscope.cdh.realtime.transfer.sender.BboSender;
import com.sumscope.cdh.realtime.transfer.sender.SingleBboSender;
import com.sumscope.cdh.realtime.transfer.sender.TradeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

/**
 * Created by liu.yang on 2017/9/30.
 */
@Component
public class TargetSenderToMQHandler implements EventHandler<TargetEventModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetSenderToMQHandler.class);

    @Autowired
    private BboSender bboSender;
    @Autowired
    private SingleBboSender singleBboSender;
    @Autowired
    private TradeSender tradeSender;

    @Override
    public void onEvent(TargetEventModel event, long sequence, boolean endOfBatch) throws Exception {
        switch (event.getBondType()){
            case BBO:
                if(!CollectionUtils.isEmpty(event.getTargetBboDBModelList())){
                    event.getTargetBboDBModelList().forEach(msg ->
                    {
                        try {
                            bboSender.sendMessage(JSON.toJSONString(msg, SerializerFeature.WriteMapNullValue).getBytes("UTF-8"));
                        } catch (IOException e) {
                            LOGGER.error("send bbo_2 error. message={}    exception={}",JSON.toJSONString(msg, SerializerFeature.WriteMapNullValue),e.getMessage());
                        }
                    });
                    LOGGER.info("send bbo_2 message size=>{}",event.getTargetBboDBModelList().size());
                }
                break;
            case TRADE:
                try {
                    if(!CollectionUtils.isEmpty(event.getTargetTradeDBModelList())){
                        tradeSender.sendMessage(JSON.toJSONString(event.getTargetTradeDBModelList()).getBytes("UTF-8"));
                        LOGGER.info("send trade message size=>{}",event.getTargetTradeDBModelList().size());
                    }
                } catch (Exception e) {
                    LOGGER.error(String.format("send trade message fail,exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(event.getTargetTradeDBModelList())));
                }
                break;
            case SINGLEBBO:
                try{
                    if(!CollectionUtils.isEmpty(event.getTargetSingleBboDBModelList())){
                        singleBboSender.sendMessage(JSON.toJSONString(event.getTargetSingleBboDBModelList()).getBytes("UTF-8"));
                        LOGGER.info("send bbo_1 message size=>{}",event.getTargetSingleBboDBModelList().size());
                    }
                }catch (Exception e){
                    LOGGER.error("send bbo_1 error.message={}    exception={}",JSON.toJSONString(event.getTargetSingleBboDBModelList()),e.getMessage());
                }
                break;
            default:
                LOGGER.error("no match Model type.");
        }
    }
}
