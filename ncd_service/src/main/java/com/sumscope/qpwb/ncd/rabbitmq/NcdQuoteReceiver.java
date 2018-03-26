package com.sumscope.qpwb.ncd.rabbitmq;

import com.sumscope.cdh.sumscopemq4j.CreateOptions;
import com.sumscope.cdh.sumscopemq4j.MqReceiverCallback;
import com.sumscope.cdh.sumscopemq4j.Receiver;
import com.sumscope.cdh.sumscopemq4j.ReceiverFactory;
import com.sumscope.qpwb.ncd.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NcdQuoteReceiver implements Receiver {
    protected static final Logger logger = LoggerFactory.getLogger(NcdQuoteReceiver.class);

    @Autowired
    MqConfig mqConfig;

    @Autowired
    BamAuthChangeHandler bamAuthHandler;


    private Receiver bamReceiver;

    public void start() {
        MqReceiverCallback bamMqReceiverCallback = new MqReceiverCallback() {
            @Override
            public boolean processString(String s) {
                logger.info("Received bam auth change message: {}", s);
                bamAuthHandler.handleBamAuthChange(s);
                return true;
            }

            @Override
            public boolean processBytes(byte[] bytes) {
                return processString(new String(bytes));
            }
        };

        CreateOptions bamOptions = new CreateOptions();
        bamOptions.setHost(mqConfig.getRabbitmqHost());
        bamOptions.setPort(mqConfig.getRabbitmqPort());
        bamOptions.setSenderType(CreateOptions.SenderType.FANOUT);
        bamOptions.setExchangeName(mqConfig.getBamExchangeName());
        bamOptions.setDurable(true);

        try {
            bamReceiver = ReceiverFactory.newReceiver(bamOptions, bamMqReceiverCallback);
            logger.info("Starting to listen to bam auth change data.");
            bamReceiver.receive();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void stop() {
        bamReceiver.stop();
    }

    @Override
    public void receive() {
        bamReceiver.receive();
    }
}
