package com.sumscope.cdh.webbond.model;

import java.util.Collection;

/**
 * Created by chengzhang.wang on 2017/2/17.
 */
public class LogonResponse
{

    private String token;
    private String displayName;
    private Collection<String> authedBrokers;
    private boolean cdcAuthed;

    public LogonResponse(String token, String displayName, Collection<String> authedBrokers, boolean cdcAuthed, String message)
    {
        this.token = token;
        this.displayName = displayName;
        this.authedBrokers = authedBrokers;
        this.cdcAuthed = cdcAuthed;
    }


    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public boolean isCdcAuthed()
    {
        return cdcAuthed;
    }

    public void setCdcAuthed(boolean cdcAuthed)
    {
        this.cdcAuthed = cdcAuthed;
    }

    public Collection<String> getAuthedBrokers()
    {
        return authedBrokers;
    }

    public void setAuthedBrokers(Collection<String> authedBrokers)
    {
        this.authedBrokers = authedBrokers;
    }

}
