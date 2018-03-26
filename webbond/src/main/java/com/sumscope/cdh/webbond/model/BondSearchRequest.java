package com.sumscope.cdh.webbond.model;

/**
 * Created by chengzhang.wang on 2017/2/20.
 */
public class BondSearchRequest extends RequestBase
{
    private String searchText;

    public String getSearchText()
    {
        return searchText;
    }

    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
    }
}
