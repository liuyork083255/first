package com.sumscope.cdh.webbond.rabbitmq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by chengzhang.wang on 2017/2/27.
 */
public class RabbitMqMessage
{
    private String token;
    private Type type;

    public RabbitMqMessage(String token, Type type)
    {
        this.token = token;
        this.type = type;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }


    public enum Type
    {
        LOGON("login"),
        LOGOUT("logout"),
        BBOUPDATE("bond_bbo_filter"),
        TRADEUPDATE("bond_trade_filter");

        private final String value;

        @JsonValue
        public String getValue() {
            return this.value;
        }

        Type(String value)
        {
            this.value = value;
        }

    }



}
