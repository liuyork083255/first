package com.sumscope.cdh.realtime.transfer;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by liu.yang on 2017/9/27.
 */
@Component
public class Start {

    private static final Logger LOGGER = LoggerFactory.getLogger(Start.class);

    @Autowired
    @Qualifier("SourceDisruptor")
    private Disruptor<SourceEventModel> sourceDisruptor;
    @Autowired
    @Qualifier("TargetDisruptor")
    private Disruptor<TargetEventModel> targetDisruptor;

    @PostConstruct
    public void start(){
        sourceDisruptor.start();
        targetDisruptor.start();
        LOGGER.info("disruptor start success");
    }

    @PreDestroy
    public void close(){
        sourceDisruptor.shutdown();
        targetDisruptor.shutdown();
        LOGGER.info("disruptor close success");
    }

    public static void main(String[] args) {
        SpringApplication.run(DataStandardApplication.class,args);
    }
}
