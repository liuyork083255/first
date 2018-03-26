package com.sumscope.qpwb.ncd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqConfig {
    @Value("${application.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${application.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.send.gateway.exchange.name}")
    private String sendNcdFilterExchangeName;

    @Value("${application.rabbitmq.outputExchangeType}")
    private String rabbitmqOutputExchangeType;

    @Value("${application.rabbitmq.bam.user.authz.change.notf.exchange.name}")
    private String bamExchangeName;

    @Value("${application.rabbitmq.ncd.reserved.exchange.name}")
    private String sendNcdReservedExchangeName;


    public String getRabbitmqHost() {
        return rabbitmqHost;
    }

    public void setRabbitmqHost(String rabbitmqHost) {
        this.rabbitmqHost = rabbitmqHost;
    }

    public int getRabbitmqPort() {
        return rabbitmqPort;
    }

    public void setRabbitmqPort(int rabbitmqPort) {
        this.rabbitmqPort = rabbitmqPort;
    }

    public String getRabbitmqOutputExchangeType() {
        return rabbitmqOutputExchangeType;
    }

    public void setRabbitmqOutputExchangeType(String rabbitmqOutputExchangeType) {
        this.rabbitmqOutputExchangeType = rabbitmqOutputExchangeType;
    }

    public String getBamExchangeName() {
        return bamExchangeName;
    }

    public void setBamExchangeName(String bamExchangeName) {
        this.bamExchangeName = bamExchangeName;
    }

    public String getSendNcdReservedExchangeName() {
        return sendNcdReservedExchangeName;
    }

    public void setSendNcdReservedExchangeName(String sendNcdReservedExchangeName) {
        this.sendNcdReservedExchangeName = sendNcdReservedExchangeName;
    }
}
