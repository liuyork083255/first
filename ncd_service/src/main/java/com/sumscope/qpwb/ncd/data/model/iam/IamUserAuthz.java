package com.sumscope.qpwb.ncd.data.model.iam;

public class IamUserAuthz {
    private String bizCompanyId;
    private Integer authzValue;

    public String getBizCompanyId() {
        return bizCompanyId;
    }

    public void setBizCompanyId(String bizCompanyId) {
        this.bizCompanyId = bizCompanyId;
    }

    public Integer getAuthzValue() {
        return authzValue;
    }

    public void setAuthzValue(Integer authzValue) {
        this.authzValue = authzValue;
    }
}
