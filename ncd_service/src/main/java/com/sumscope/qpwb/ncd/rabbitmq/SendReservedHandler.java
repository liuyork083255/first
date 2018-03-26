package com.sumscope.qpwb.ncd.rabbitmq;

import com.sumscope.cdh.sumscopemq4j.Sender;
import com.sumscope.qpwb.ncd.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SendReservedHandler {
    private static final Logger logger = LoggerFactory.getLogger(SendReservedHandler.class);
    private Sender notificationSender;
    @Autowired
    private MqManager mqManager;
    @Autowired
    private MqConfig mqConfig;

    @PostConstruct
    private void init() {
        notificationSender = mqManager.getSender(mqConfig.getSendNcdReservedExchangeName(),
                mqConfig.getRabbitmqOutputExchangeType());
    }

    public void sendMqMsg(byte[] msg) {
        try {
            logger.info("send reserved msg: {}", new String(msg));
            notificationSender.send(msg);
        } catch (Exception ex) {
            logger.error("Failed to send mq message, err:{}", ex.getMessage());
        }
    }
}
