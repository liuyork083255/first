package com.sumscope.cdh.webbond.model;

import java.util.Collection;

public class RequestBase implements Trimable
{
    private String token;

    public Collection<String> tradeBrokerIds;
    public Collection<String> quoteBrokerIds;

    public RequestBase()
    {

    }

    public RequestBase(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @Override
    public void trimToEmpty()
    {
        if (token != null)
        {
            token.trim();
        }
    }
}
