package com.sumscope.cdh.realtime.transfer.config;

import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.Executors;

/**
 * Created by liu.yang on 2017/9/28.
 */
@Configuration
public class DisruptorConfig {

    @Autowired
    private Environment configParam;

    @Bean(name = "SourceDisruptor")
    public Disruptor getSourceDisruptor(){
        return new Disruptor<>(
                SourceEventModel::new,
                Integer.parseInt(configParam.getProperty("ring.buffer.source.size")),
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new LiteBlockingWaitStrategy());
    }

    @Bean(name = "TargetDisruptor")
    public Disruptor getTargetDisruptor(){
        return new Disruptor<>(
                TargetEventModel::new,
                Integer.parseInt(configParam.getProperty("ring.buffer.target.size")),
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new LiteBlockingWaitStrategy());
    }

}
