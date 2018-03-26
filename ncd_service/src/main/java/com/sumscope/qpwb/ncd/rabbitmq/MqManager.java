package com.sumscope.qpwb.ncd.rabbitmq;

import com.sumscope.cdh.sumscopemq4j.*;
import com.sumscope.qpwb.ncd.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengzhang.wang on 2017/3/8.
 */
@org.springframework.stereotype.Component
public class MqManager
{
    private static final Logger logger = LoggerFactory.getLogger(MqManager.class);

    private String host;
    private int port;
    private Map<String, Sender> senders = new HashMap<String, Sender>();
    private Map<String, Receiver> receivers = new HashMap<String, Receiver>();

    @Autowired
    private MqConfig mqConfig;

    @PostConstruct
    public void init()
    {
        this.host = mqConfig.getRabbitmqHost();
        this.port = mqConfig.getRabbitmqPort();
    }

    private static CreateOptions.SenderType getSenderType(String type)
    {
        CreateOptions.SenderType senderType = CreateOptions.SenderType.TOPIC;
        type = type.toLowerCase();
        switch (type)
        {
            case "queue":
                senderType = CreateOptions.SenderType.QUEUE;
                break;
            case "fanout":
                senderType = CreateOptions.SenderType.FANOUT;
                break;
            default:
                break;
        }
        return senderType;
    }

    private static void setExchangeName(CreateOptions options, CreateOptions.SenderType type, String exchange)
    {
        switch (type)
        {
            case QUEUE:
                options.setQueueName(exchange);
                break;
            case FANOUT:
                options.setExchangeName(exchange);
                break;
            case TOPIC:
                //FIXME
                //options.setQueueName(exchange);
                //options.setExchangeName(exchange);
                break;
            default:
                throw new RuntimeException("Unsupported exchange type: " + type);
        }
    }

    public synchronized Sender getSender(String exchange, CreateOptions.SenderType type)
    {
        Sender sender = senders.get(exchange);
        if (sender == null)
        {
            CreateOptions options = new CreateOptions();
            options.setHost(host);
            options.setPort(port);
            options.setSenderType(type);
            setExchangeName(options, type, exchange);
            try
            {
                sender = SenderFactory.newSender(options);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Error creating sender: " + exchange, e);
            }
            senders.put(exchange, sender);
        }
        return sender;
    }

    public synchronized Sender getSender(String exchange, String type)
    {
        return getSender(exchange, getSenderType(type));
    }

    public synchronized Receiver getReceiver(String exchange, CreateOptions.SenderType type, boolean durable, MqReceiverCallback mqReceiverCallback, boolean isSingleThread)
    {
        Receiver receiver = receivers.get(exchange);
        if (receiver == null)
        {
            CreateOptions options = new CreateOptions();
            options.setHost(host);
            options.setPort(port);
            options.setSenderType(type);
            options.setDurable(durable);
            options.setSingleThread(isSingleThread);
            setExchangeName(options, type, exchange);
            try
            {
                receiver = ReceiverFactory.newReceiver(options, mqReceiverCallback);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Error creating receiver: " + exchange, e);
            }
            receivers.put(exchange, receiver);
        }
        return receiver;
    }

    public synchronized Receiver getReceiver(String exchange, String type, boolean durable, MqReceiverCallback mqReceiverCallback,boolean isSingleThread)
    {
        return getReceiver(exchange, getSenderType(type), durable, mqReceiverCallback, isSingleThread);
    }

    public void close()
    {
        for (Map.Entry<String, Sender> e : senders.entrySet())
        {
            e.getValue().close();
        }
        for (Map.Entry<String, Receiver> e : receivers.entrySet())
        {
            e.getValue().stop();
        }
        logger.info("close finished");
    }
}
