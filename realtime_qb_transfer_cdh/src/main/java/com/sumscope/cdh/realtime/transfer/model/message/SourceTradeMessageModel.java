package com.sumscope.cdh.realtime.transfer.model.message;

/**
 * 这个 model 是处理从 QB 接过来的行情
 * Created by liu.yang on 2017/9/30.
 */
public class SourceTradeMessageModel extends FatherMessageModel{
    private String MS_COMPANY_NAME;
    private String MS_GOODS_ID;

    private SonTradeMessageModel MS_BODY;

    public String getMS_GOODS_ID() {
        return MS_GOODS_ID;
    }

    public void setMS_GOODS_ID(String MS_GOODS_ID) {
        this.MS_GOODS_ID = MS_GOODS_ID;
    }

    public SonTradeMessageModel getMS_BODY() {
        return MS_BODY;
    }

    public void setMS_BODY(SonTradeMessageModel MS_BODY) {
        this.MS_BODY = MS_BODY;
    }

    public String getMS_COMPANY_NAME() {
        return MS_COMPANY_NAME;
    }

    public void setMS_COMPANY_NAME(String MS_COMPANY_NAME) {
        this.MS_COMPANY_NAME = MS_COMPANY_NAME;
    }
}
