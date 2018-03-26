package com.sumscope.cdh.webbond.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Administrator on 2017/2/27.
 */
public class UserAccount
{
    @JsonProperty("USERNAME")
    public String userAccountName;
    @JsonProperty("id")
    public String userAccountId;
    @JsonProperty("DISPLAY_NAME")
    public String displayName;
    public Collection<String> authedBrokers;
    public boolean cdcAuthed;
}
