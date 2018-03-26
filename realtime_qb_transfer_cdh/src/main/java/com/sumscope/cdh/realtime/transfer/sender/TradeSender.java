package com.sumscope.cdh.realtime.transfer.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/11/28.
 */
@Component
public class TradeSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeSender.class);

    @Value("${rabbitmq.sender.exchange.trade}")
    private String senderExchange;
    @Autowired
    private RabbitTemplate template;

    public void sendMessage(byte[] msg){
        try {
            template.convertAndSend(senderExchange,"",msg);
        } catch (Exception e) {
            LOGGER.error("send trade error.    exception={}   msg={}",e.getMessage(),msg);
        }
    }
}
