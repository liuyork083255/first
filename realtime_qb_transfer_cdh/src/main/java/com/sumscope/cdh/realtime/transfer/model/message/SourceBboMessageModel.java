package com.sumscope.cdh.realtime.transfer.model.message;

/**
 * Created by liu.yang on 2017/9/30.
 */
public class SourceBboMessageModel extends FatherMessageModel {
    private String MS_lastDealPrice;
    private long MS_BestVersion;
    private String MS_lastDealNetPrice;
    private SonBboMessageModel MS_BODY;

    public String getMS_lastDealPrice() {
        return MS_lastDealPrice;
    }

    public void setMS_lastDealPrice(String MS_lastDealPrice) {
        this.MS_lastDealPrice = MS_lastDealPrice;
    }

    public long getMS_BestVersion() {
        return MS_BestVersion;
    }

    public void setMS_BestVersion(long MS_BestVersion) {
        this.MS_BestVersion = MS_BestVersion;
    }

    public String getMS_lastDealNetPrice() {
        return MS_lastDealNetPrice;
    }

    public void setMS_lastDealNetPrice(String MS_lastDealNetPrice) {
        this.MS_lastDealNetPrice = MS_lastDealNetPrice;
    }

    public SonBboMessageModel getMS_BODY() {
        return MS_BODY;
    }

    public void setMS_BODY(SonBboMessageModel MS_BODY) {
        this.MS_BODY = MS_BODY;
    }
}
