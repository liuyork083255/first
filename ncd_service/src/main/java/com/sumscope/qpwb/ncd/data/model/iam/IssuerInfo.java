package com.sumscope.qpwb.ncd.data.model.iam;

public class IssuerInfo {
    private String username;
    private String companyCode;
    private String qq;
    private String telephone;
    private String mobile;
    private String customerOrgName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCustomerOrgName() {
        return customerOrgName;
    }

    public void setCustomerOrgName(String customerOrgName) {
        this.customerOrgName = customerOrgName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
