package com.sumscope.qpwb.ncd.global.enums;

/**
 * Created by mengyang.sun on 2018/01/22.
 */
public enum MiddlerQuoteGoodStatusEnum {
    NORMAL("0", "正常"),
    REFFER("1", "reffer"),
    DELETE("2", "删除"),;

    private MiddlerQuoteGoodStatusEnum(String statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }

    private String statusCode;

    private String status;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
