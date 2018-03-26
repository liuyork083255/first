package com.sumscope.cdh.realtime.model.restful;

/**
 * Created by liu.yang on 2017/10/18.
 */
public class BondCacheModel {
    private String Bond_ID;
    private String Bond_Key;
    private String Listed_Market;

    public String getBond_ID() {
        return Bond_ID;
    }

    public void setBond_ID(String bond_ID) {
        Bond_ID = bond_ID;
    }

    public String getBond_Key() {
        return Bond_Key;
    }

    public void setBond_Key(String bond_Key) {
        Bond_Key = bond_Key;
    }

    public String getListed_Market() {
        return Listed_Market;
    }

    public void setListed_Market(String listed_Market) {
        Listed_Market = listed_Market;
    }
}
