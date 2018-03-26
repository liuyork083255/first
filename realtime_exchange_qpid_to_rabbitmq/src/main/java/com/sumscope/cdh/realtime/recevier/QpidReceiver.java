package com.sumscope.cdh.realtime.recevier;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.client.message.AMQPEncodedMapMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class QpidReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(QpidReceiver.class);

    private Connection qpidConnection;

    @Autowired
    private Environment configParam;
    @Autowired
    private Disruptor<BondEventModel> disruptor;

    public void start(){
        try
        {
            qpidConnection = new AMQConnection(configParam.getProperty("qpid.url"));
            qpidConnection.setExceptionListener(e -> LOGGER.error(e.getMessage()));
            qpidConnection.start();
            Session session = qpidConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = new AMQAnyDestination(configParam.getProperty("qpid.topic"));
            MessageConsumer messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener((msg) ->
            {
                LOGGER.debug("message coming ...");
                if(msg instanceof AMQPEncodedMapMessage)
                {
                    AMQPEncodedMapMessage mapMessage = (AMQPEncodedMapMessage) msg;
                    disruptor.publishEvent((event, seq, arg) ->
                    {
                        event.setSource(mapMessage.getMap());event.setFlag(true);
                    }, mapMessage.getMap());
                }
                else
                {
                    LOGGER.error("the message is illegal format.type=>{}.message=>{}",
                            msg.getClass().getName(),
                            JSON.toJSONString(msg)
                            );
                }
            });
        }
        catch (Exception e)
        {
            LOGGER.error(String.format("qpid run fail.exception:[%s]",e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
    }

    public void close(){
        if(null != qpidConnection)
        {
            try
            {
                qpidConnection.close();
            }
            catch (JMSException e)
            {
                LOGGER.error("close connection failed for exception:", e);
            }
        }
    }
}
