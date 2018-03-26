package com.sumscope.qpwb.ncd.global.enums;

/**
 * Created by mengyang.sun on 2018/01/22.
 */
public enum MiddleQuoteStatusEnum {
    VALID("1", "有效"),
    INVALID("2", "无效"),;

    private MiddleQuoteStatusEnum(String statusCode, String status){
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
