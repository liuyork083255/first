package com.sumscope.cdh.realtime.transfer.receiver;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.transfer.mail.EmailUtil;
import com.sumscope.cdh.realtime.transfer.model.handler.BondType;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/11/28.
 */
@Component
public class ReceiverMQ {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverMQ.class);

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    @Qualifier("SourceDisruptor")
    private Disruptor<SourceEventModel> sourceDisruptor;


    @RabbitListener(queues = "${rabbitmq.receiver.transfer.bbo.queue}")
    public void receiveBbo(byte[] message){
        sendMessage(new String(message),BondType.BBO);
    }

    @RabbitListener(queues = "${rabbitmq.receiver.transfer.single.bbo.queue}")
    public void receiveSingleBbo(byte[] message){
        sendMessage(new String(message),BondType.SINGLEBBO);
    }

    @RabbitListener(queues = "${rabbitmq.receiver.transfer.trade.queue}")
    public void receiveTrade(byte[] message){
        sendMessage(new String(message),BondType.TRADE);
    }

    private void sendMessage(String message,BondType bondType){
        /*
         * 全局同步变量 控制邮件是否警报无行情异常
         */
        if(!emailUtil.getMailFlag()) emailUtil.setMailFlag(true);
        try {
            switch (bondType){
                case BBO:
                    LOGGER.info("message coming from [bbo_2]");
                    sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(message);event.setBondType(BondType.BBO);}, message);
                    break;
                case SINGLEBBO:
                    LOGGER.info("message coming from [bbo_1]");
                    sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(message);event.setBondType(BondType.SINGLEBBO);}, message);
                    break;
                case TRADE:
                    LOGGER.info("message coming from [trade]");
                    sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(message);event.setBondType(BondType.TRADE);}, message);
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("publish bondType={} event error.  exception={}      message={}",bondType.getValue(),e.getMessage(),message);
        }
    }
}
