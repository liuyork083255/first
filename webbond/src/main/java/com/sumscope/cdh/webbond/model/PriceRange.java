package com.sumscope.cdh.webbond.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by chengzhang.wang on 2017/3/17.
 */
public class PriceRange
{
    private Type op;
    private String value;

    public Type getOp()
    {
        return op;
    }

    public void setOp(Type op)
    {
        this.op = op;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public enum Type
    {
        NON("non"), GE("ge"), LE("le");

        private final String value;

        @JsonValue
        public String getValue()
        {
            return value;
        }

        Type(String value)
        {
            this.value = value;
        }

        public Type getType(String type)
        {
            for (Type Type : values())
            {
                if (Type.value.equals(type))
                {
                    return Type;
                }
            }
            return NON;
        }
    }
}
