package com.sumscope.cdh.webbond.model;

import org.apache.commons.lang3.StringUtils;

public class GetDictionaryRequest extends RequestBase
{
    private String user;
    private String userToken;

    public String getUser() {
        return user;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public void trimToEmpty() {
        user = StringUtils.trimToEmpty(user);
        userToken = StringUtils.trimToEmpty(userToken);
    }
}



