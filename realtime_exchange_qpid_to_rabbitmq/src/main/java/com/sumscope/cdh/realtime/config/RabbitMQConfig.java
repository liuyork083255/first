package com.sumscope.cdh.realtime.config;

import com.sumscope.cdh.sumscopemq4j.CreateOptions;
import com.sumscope.cdh.sumscopemq4j.Sender;
import com.sumscope.cdh.sumscopemq4j.SenderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Configuration
public class RabbitMQConfig {
    @Autowired
    private Environment configParam;

    /**
     * common rabbitMQ sender config
     * @return
     */
    @Bean
    public CreateOptions getCreateOptions(){
        CreateOptions createOptions = new CreateOptions();
        createOptions.setHost(configParam.getProperty("rabbitmq.host"));
        createOptions.setPort(Integer.parseInt(configParam.getProperty("rabbitmq.port")));
        createOptions.setSenderType(CreateOptions.SenderType.FANOUT);
        return createOptions;
    }

    /**
     * config Sender
     * @param createOptions
     * @return
     * @throws Exception
     */
    @Bean
    public Sender getSender(CreateOptions createOptions) throws Exception{
        createOptions.setRequestedHeartbeat(Integer.parseInt(configParam.getProperty("rabbitmq.sender.heart")));
        createOptions.setExchangeName(configParam.getProperty("rabbitmq.sender.bond.exchange"));
        return SenderFactory.newSender(createOptions);
    }
}
