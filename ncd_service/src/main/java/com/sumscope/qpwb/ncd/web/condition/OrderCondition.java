package com.sumscope.qpwb.ncd.web.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "用户预定请求体")
public class OrderCondition {
    @ApiModelProperty(value = "用户id", position = 1)
    @NotBlank(message = "userId can not be null")
    private String userId;
    @ApiModelProperty(value = "经济商id", position = 2)
    @NotBlank(message = "brokerId can not be null")
    private String brokerId;
    @ApiModelProperty(value = "经济商名称", position = 3)
    @NotBlank(message = "brokerName can not be null")
    private String brokerName;
    @ApiModelProperty(value = "发行银行", position = 4)
    @NotBlank(message = "issuerName can not be null")
    private String issuerName;
    @ApiModelProperty(value = "报价id", position = 5)
    @NotNull(message = "quoteOfferId can not be null")
    private String quoteOfferId;
    @ApiModelProperty(value = "机构id", position = 6)
    @NotBlank(message = "issuerId can not be null")
    private String issuerId;
    @ApiModelProperty(value = "报价时间", position = 7)
    @NotBlank(message = "quoteTime can not be null")
    private String quoteTime;
    @ApiModelProperty(value = "用户名称", position = 8)
    private String userName;
    @ApiModelProperty(value = "用户所属机构", position = 9)
    private String customerOrgName;
    @ApiModelProperty(value = "用户qq", position = 10)
    private String qq;
    @ApiModelProperty(value = "用户移动电话", position = 11)
    private String mobile;
    @ApiModelProperty(value = "用户固定电话", position = 12)
    private String telephone;
    @ApiModelProperty(value = "期限", position = 13)
    private String limit;
    @ApiModelProperty(value = "价格", position = 14)
    private BigDecimal price;
    @ApiModelProperty(value = "用户所属机构代码", position = 15)
    private String companyCode;
    @ApiModelProperty(value = "是否为固定", position = 16)
    private boolean fixRate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getQuoteOfferId() {
        return quoteOfferId;
    }

    public void setQuoteOfferId(String quoteOfferId) {
        this.quoteOfferId = quoteOfferId;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(String quoteTime) {
        this.quoteTime = quoteTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerOrgName() {
        return customerOrgName;
    }

    public void setCustomerOrgName(String customerOrgName) {
        this.customerOrgName = customerOrgName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public boolean isFixRate() {
        return fixRate;
    }

    public void setFixRate(boolean fixRate) {
        this.fixRate = fixRate;
    }
}
