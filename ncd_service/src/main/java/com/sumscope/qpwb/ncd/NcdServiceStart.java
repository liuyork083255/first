package com.sumscope.qpwb.ncd;

import com.sumscope.qpwb.ncd.data.service.NcdIssuersService;
import com.sumscope.qpwb.ncd.rabbitmq.NcdQuoteReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * Created by liu.yang on 2018/1/19.
 */
@Component
public class NcdServiceStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(NcdServiceStart.class);
    @Autowired
    NcdIssuersService ncdIssuersService;
    @Autowired
    NcdQuoteReceiver rabbitmqNcdReceiver;

    public void start(){
        rabbitmqNcdReceiver.start();
        LOGGER.info("ncd server start ...");
    }

    @PreDestroy
    public void close(){
        rabbitmqNcdReceiver.stop();
        LOGGER.info("ncd server closed");
    }

    public static void main(String[] args) {
        NcdServiceStart start = SpringApplication.run(NcdServiceApplication.class, args).getBean(NcdServiceStart.class);
        start.start();
    }
}
