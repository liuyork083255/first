package com.sumscope.cdh.webbond.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chengzhang.wang on 2017/3/22.
 */
public class FilterByBondRequest extends PageableRequest
{
    @JsonProperty("bondKey")
    public String bondKeyListedMarket;
}
