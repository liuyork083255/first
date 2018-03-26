package com.sumscope.cdh.realtime.transfer.restful;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by liu.yang on 2017/10/31.
 */
public enum Outlook {

    UNKNOWN("--", "--"), RWT("RWT", "列入观察名单"), NEG("NEG", "负面"), POS("POS", "正面"), STB("STB", "稳定");

    private final String zh;
    private final String value;

    Outlook(String value, String zh)
    {
        this.zh = zh;
        this.value = value;
    }

    public String getZh()
    {
        return zh;
    }

    public String getValue()
    {
        return value;
    }

    public static Outlook fromValue(String value)
    {
        for (Outlook o : values())
        {
            if (StringUtils.equals(o.getValue(), value))
                return o;
        }
        return UNKNOWN;
    }

}
