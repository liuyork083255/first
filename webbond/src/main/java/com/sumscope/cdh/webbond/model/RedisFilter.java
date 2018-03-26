package com.sumscope.cdh.webbond.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by chengzhang.wang on 2017/3/1.
 */
public class RedisFilter
{
    @JsonProperty("bondKeys")
    public Collection<String> bondKeyListedMarkets;
    public Collection<String> tradeBrokerIds;
    public Collection<String> bboBrokerIds;

    public RedisFilter(Collection<String> bondKeyListedMarkets,Collection<String> bboBrokerIds,Collection<String> tradeBrokerIds)
    {
        this.bondKeyListedMarkets = bondKeyListedMarkets;
        this.tradeBrokerIds = tradeBrokerIds;
        this.bboBrokerIds = bboBrokerIds;
    }
}
