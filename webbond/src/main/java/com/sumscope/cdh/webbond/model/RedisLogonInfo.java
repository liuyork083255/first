package com.sumscope.cdh.webbond.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by chengzhang.wang on 2017/2/23.
 */
public class RedisLogonInfo
{
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public int expiresIn;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("username")
    public String userName;
    @JsonProperty("userId")
    public String userId;
    @JsonProperty("scope")
    public String scope;
    @JsonProperty("cdcAuthed")
    public boolean cdcAuthed;
    @JsonProperty("authorizedBrokers")
    public Collection<String> authorizedBrokers = new HashSet<>();
}
