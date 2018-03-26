package com.sumscope.cdh.webbond.test;

import com.sumscope.cdh.sumscopemq4j.MqReceiverCallback;
import com.sumscope.cdh.webbond.Config;
import com.sumscope.cdh.webbond.MqManager;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chengzhang.wang on 2017/3/30.
 */

@Configuration
@PropertySource(value="application.properties")
class MqTestConfig {
    @Bean
    Config config(){ return new Config();}
    @Bean
    MqManager mqManager(){return new MqManager();}
}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MqTestConfig.class)
public class MqTest
{
    @Autowired
    Config config;
    @Autowired
    MqManager mqManager;

    @org.junit.Test
    public void threadTest() throws InterruptedException
    {
        mqManager.getReceiver(config.getRabbitmqBboInputExchangeName(), config.getRabbitmqBboInputExchangeType(), new MqReceiverCallback()
        {
            @Override
            public boolean processString(String message)
            {
                return false;
            }

            @Override
            public boolean processBytes(byte[] message)
            {
                System.out.println(Thread.currentThread().getId());
                return false;
            }
        },true).receive();
        Thread.sleep(100000000);
    }
}
