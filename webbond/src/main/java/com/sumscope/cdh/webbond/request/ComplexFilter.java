package com.sumscope.cdh.webbond.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sumscope.cdh.webbond.model.BidOfrValue;

/**
 * Created by chengzhang.wang on 2017/2/6.
 */
public class ComplexFilter extends BondFilter
{
    public String bboOrderByColumn;
    public String tradeOrderByColumn;
    public OrderType bboOrderType;
    public OrderType tradeOrderType;
    public Boolean validQuote;
    public Boolean isBothSides;
    public Double bothSidesDiff;
    public BidOfrValue bidOfrValue;

    public void check() throws Exception
    {
        super.check();
    }

    public enum OrderType
    {
        DESC(-1), NORMAL(0), ASC(1);

        private final int value;

        @JsonValue
        public int getValue()
        {
            return this.value;
        }

        OrderType(int value)
        {
            this.value = value;
        }

        @JsonCreator
        public static OrderType fromValue(int n)
        {
            for (OrderType v : values())
            {
                if (v.getValue() == n)
                {
                    return v;
                }
            }
            return NORMAL;
        }
    }
}
