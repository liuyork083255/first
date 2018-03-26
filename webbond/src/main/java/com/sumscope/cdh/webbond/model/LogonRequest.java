package com.sumscope.cdh.webbond.model;

/**
 * Created by chengzhang.wang on 2017/2/17.
 */
public class LogonRequest extends RequestBase
{
    public String userName;
    public String passWord;
    public Boolean encrypted = false;

    @Override
    public void trimToEmpty()
    {
        super.trimToEmpty();
        userName = userName.trim();
        passWord = passWord.trim();
    }
}
