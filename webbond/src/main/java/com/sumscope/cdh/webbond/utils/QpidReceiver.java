package com.sumscope.cdh.webbond.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.qpid.client.message.JMSBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public final class QpidReceiver implements Callable<Object> {
    private volatile boolean isStopped = false;
    private volatile boolean needRecreateConsumer = true;
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh");


    Properties properties;
    Connection connection;
    Session session;
    Context context;
    ConnectionFactory connectionFactory;
    MessageConsumer messageConsumer;

    IQpidConfig qpidConfig;
    Consumer<String> messageHandler;

    public QpidReceiver(IQpidConfig qpidConfig, Consumer<String> messageHandler) {
        this.qpidConfig = qpidConfig;
        this.messageHandler = messageHandler;
    }

    @Override
    public Object call() {
        while (!isStopped) {
            if (needRecreateConsumer) {
                try {
                    messageConsumer = retryCreateMessageConsumer();
                    if (messageConsumer != null) {
                        logger.info("QpidReceiver: created qpid consumer successfully");
                        consumeLoop(messageConsumer);
                    }
                } catch (Exception e) {
                    LogTool.logException(e, "Unknown error exiting thread of QpidReceiver");
                }
            }
            try {
                Thread.sleep(qpidConfig.getQpidReconnectInterval());
            } catch (InterruptedException e) {
            }
        }
        logger.info("Quit thread......");
        return null;
    }

    private void consumeLoop(MessageConsumer messageConsumer) {
        isStopped = false;
        needRecreateConsumer = false;
        while (!isStopped && !needRecreateConsumer) {
            try {
                String message = blockingReceiveOneMessage(messageConsumer);
                logger.info(message);
                messageHandler.accept(message); // delegate out the message
            } catch (Exception e) {
                LogTool.logException(e, "QpidReceiver: Fetch or process message failed");
            }
        }
        if (needRecreateConsumer) {
            logger.warn("exit consumeLoop due to qpid connection lost, will recreate consumer");
            messageConsumer = null;
        }
    }

    private MessageConsumer retryCreateMessageConsumer() throws InterruptedException {
        int reconnectMax = qpidConfig.getQpidReconnectCount();
        int retry = 0;
        MessageConsumer newMessageConsumer = null;
        while (retry <= reconnectMax && !isStopped) {
            try {
                newMessageConsumer = createMessageConsumer();
                break;
            } catch (Exception e) {
                String s = String.format("QpidReceiver: create-consumer failed, sleep %s milli-seconds and re-create..., stack trace: %s",
                        qpidConfig.getQpidReconnectInterval(), ExceptionUtils.getStackTrace(e));
                logger.warn(s);
                if (retry == 0)
                    LogTool.logException(e, "Failed to create consumer for some reason, will try recreate...");
                Thread.sleep(qpidConfig.getQpidReconnectInterval());
                retry++;
            }
        }
        if (newMessageConsumer == null)
            LogTool.logException(new Exception("QpidReceiver: create-consumer failed after " + reconnectMax + " retries"), "");
        return  newMessageConsumer;
    }

    private MessageConsumer createMessageConsumer() throws Exception {
        properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "org.apache.qpid.jndi.PropertiesFileInitialContextFactory");
        properties.setProperty("connectionfactory.qpidConnectionfactory", qpidConfig.getQpidFactory());
        properties.setProperty("destination.address", qpidConfig.getQpidAddress());

        logger.info(String.format("QpidReceiver: start qpid connection, address: %s, destination: %s",
                qpidConfig.getQpidFactory(), qpidConfig.getQpidAddress()));

        context = new InitialContext(properties);
        connectionFactory = (ConnectionFactory) context.lookup("qpidConnectionfactory");
        connection = connectionFactory.createConnection();
        connection.setExceptionListener(new ExceptionListener() {
            @Override
            public void onException(JMSException exception) {
                if (isStopped)
                    return;
                logger.warn("*******qpid connect is lost!");
                needRecreateConsumer = true;
                messageConsumer = null;
            }
        });
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        logger.info("QpidReceiver: connected qpid successfully");
        return session.createConsumer((Destination) context.lookup("address"));
    }

    private String blockingReceiveOneMessage(MessageConsumer messageConsumer) throws Exception {
        Message message = null;
        while (!isStopped && !needRecreateConsumer) {
            message = messageConsumer.receive(1000);
            if (message != null)
                break;
        }
        if (needRecreateConsumer)
            logger.warn("QpidReceiver: need recreate consumer");
        else if (isStopped)
            logger.warn("QpidReceiver: stop receive message");

        logger.info("QpidReceiver: recieved one qpid message...");
        JMSBytesMessage bytesMessage = (JMSBytesMessage) message;

        int length = new Long(bytesMessage.getBodyLength()).intValue();
        logger.info("QpidReceiver: length:" + Integer.toString(length));

        final byte[] bytes = new byte[length];
        bytesMessage.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void stop() {
        logger.info("QpidReceiver: start stopping qpid...");
        isStopped = true;
        try {
            connection.close();
        } catch (Exception e) {
        }
    }
}
