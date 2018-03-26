package com.sumscope.qpwb.ncd.global.enums;

public enum ReserveStatusEnum {
    FINISH("finish", "询满"),
    WITHOUT("without", "未预定"),
    PENDING("pending", "已预订"),;

    private ReserveStatusEnum(String statusCode, String status){
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
