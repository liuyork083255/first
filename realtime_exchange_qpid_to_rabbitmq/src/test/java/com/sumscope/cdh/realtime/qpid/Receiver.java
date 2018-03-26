package com.sumscope.cdh.realtime.qpid;

import com.alibaba.fastjson.JSON;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;
import org.apache.qpid.client.message.AMQPEncodedMapMessage;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by liu.yang on 2017/11/6.
 */
public class Receiver {

    private static Connection qpidConnection;

    public static void main(String[] args) {
        connect();
    }

    public static void connect(){
        try
        {
            qpidConnection = new AMQConnection("amqp://guest:guest@clientid/test?failover='singlebroker'&brokerlist='tcp://192.168.1.238:5672?tcp_nodelay='true'&connecttimeout='5000''");
            qpidConnection.setExceptionListener(e ->
            {
                throw new RuntimeException(String.format("qpid connection error.exception:[%s]",e.getMessage()));
            });
            qpidConnection.start();
            Session session = qpidConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = new AMQAnyDestination("stock.incremental;{create:always,delete:always,node:{type:topic,x-declare:{type:fanout,auto-delete:True}}}");
            MessageConsumer messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener((msg) ->
            {
                AMQPEncodedMapMessage mapMessage = (AMQPEncodedMapMessage) msg;
                System.out.println("message coming ...");

            });
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
