package com.sumscope.cdh.realtime.transfer.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by liu.yang on 2017/11/28.
 */
@Configuration
public class SpringRabbitMQConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringRabbitMQConfig.class);

    @Value("${rabbitmq.receiver.transfer.bbo.queue}")
    private String bboQueueName;
    @Value("${rabbitmq.receiver.exchange.bbo}")
    private String bboExchangeName;
    @Value("${rabbitmq.receiver.transfer.bbo.queue.autodelete}")
    private String bboQueueAutodelete;


    @Value("${rabbitmq.receiver.transfer.single.bbo.queue}")
    private String singleBboQueueName;
    @Value("${rabbitmq.receiver.exchange.single.bbo}")
    private String singleBboExchangeName;
    @Value("${rabbitmq.receiver.transfer.single.bbo.queue.autodelete}")
    private String singleBboQueueAutodelete;

    @Value("${rabbitmq.receiver.trade.from}")
    private String tradeFrom;
    @Value("${rabbitmq.receiver.transfer.trade.queue}")
    private String tradeQueueName;
    @Value("${rabbitmq.receiver.exchange.trade}")
    private String tradeExchangeName;
    @Value("${rabbitmq.receiver.transfer.trade.queue.autodelete}")
    private String tradeQueueAutodelete;

    @Value("${rabbitmq.sender.exchange.bbo}")
    private String senderBboExchangeName;
    @Value("${rabbitmq.sender.exchange.single.bbo}")
    private String senderSingleBboExchangeName;
    @Value("${rabbitmq.sender.exchange.trade}")
    private String senderTradeExchangeName;

    @PostConstruct
    public void init(){
        if(!StringUtils.equalsIgnoreCase(tradeFrom,"qb") && !StringUtils.equalsIgnoreCase(tradeFrom,"cdh")){
            throw new RuntimeException("not find type exchange from qb or cdh.please check rabbitmq.receiver.trade.from value");
        }
        LOGGER.info("trade setting from {}",tradeFrom);
    }

    private Queue getQueue(String queueName,String autoDelete){
        if(StringUtils.equalsIgnoreCase("false",autoDelete)){
            LOGGER.info("queue={} autoDelete = false",queueName);
            return new Queue(queueName,false);
        }else{
            LOGGER.info("queue={} autoDelete = true",queueName);
            return new Queue(queueName,false,false,true);
        }
    }

    @Bean
    public Queue bboQueue(){
        return getQueue(bboQueueName,bboQueueAutodelete);
    }

    @Bean
    public Queue singleQueue(){
        return getQueue(singleBboQueueName,singleBboQueueAutodelete);
    }

    @Bean
    public Queue tradeQueue(){
        return getQueue(tradeQueueName,tradeQueueAutodelete);
    }

    @Bean
    public FanoutExchange bboFanoutExchange(){return new FanoutExchange(bboExchangeName,false,false);}

    @Bean
    public FanoutExchange singleBboFanoutExchange(){return new FanoutExchange(singleBboExchangeName,false,false);}

    @Bean
    public FanoutExchange tradeFanoutExchange(){return new FanoutExchange(tradeExchangeName,false,false);}

    @Bean
    public FanoutExchange senderBboExchange(){
        return new FanoutExchange(senderBboExchangeName,false,false);
    }

    @Bean
    public FanoutExchange senderSingleBboExchange(){
        return new FanoutExchange(senderSingleBboExchangeName,false,false);
    }

    @Bean
    public FanoutExchange senderTradeExchangeName(){
        return new FanoutExchange(senderTradeExchangeName,false,false);
    }


    @Bean
    public Binding bindingBbo(Queue bboQueue,FanoutExchange bboFanoutExchange){
        return BindingBuilder.bind(bboQueue).to(bboFanoutExchange);
    }

    @Bean
    public Binding bindingSingleBbo(Queue singleQueue,FanoutExchange singleBboFanoutExchange){
        return BindingBuilder.bind(singleQueue).to(singleBboFanoutExchange);
    }

    @Bean
    public Binding bindingTrade(Queue tradeQueue,FanoutExchange tradeFanoutExchange){
        return BindingBuilder.bind(tradeQueue).to(tradeFanoutExchange);
    }

}
