package com.sumscope.cdh.realtime.config;

import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.Executors;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Configuration
public class DisruptorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisruptorConfig.class);
    private static final int RING_BUFFER_DEFAULT_SIZE = 1024 * 1024 * 4;

    @Autowired
    private Environment configParam;

    @Bean
    public Disruptor getDisruptor(){
        int ringBufferSize;
        try {
            ringBufferSize = Integer.parseInt(configParam.getProperty("ring.buffer.source.size"));
        } catch (NumberFormatException e) {
            LOGGER.info("NumberFormat ring buffer size error. ringBufferSize={}",RING_BUFFER_DEFAULT_SIZE);
            ringBufferSize = RING_BUFFER_DEFAULT_SIZE;
        }

        return new Disruptor<>(
                BondEventModel::new,
                ringBufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new LiteBlockingWaitStrategy());
    }
}
