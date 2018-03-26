package com.sumscope.cdh.webbond;

import org.apache.commons.lang3.StringUtils;

public enum RepoReloadFlag
{

    NONE("None", 0),
    DICTIONARY("Dictionary", 1 << 0),
    BONDS("Bonds", 1 << 1),
    USER_ACCOUNTS("UserAccounts", 1 << 2),
    STATICINFO("StaticInfo", (1 << 3) - 1),

    BESTQUOTES("BestQuotes", 1 << 4),
    TRADESTODAY("TradesToday", 1 << 5),
    ALL("All", (1 << 6) - 1);

    String name;
    int value;

    RepoReloadFlag(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public boolean contains(RepoReloadFlag flag)
    {
        return (value & flag.value) == flag.value;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static RepoReloadFlag parseString(String s)
    {
        for (RepoReloadFlag v : values())
        {
            if (StringUtils.endsWithIgnoreCase(v.toString(), s))
            {
                return v;
            }
        }
        return NONE;
    }
}