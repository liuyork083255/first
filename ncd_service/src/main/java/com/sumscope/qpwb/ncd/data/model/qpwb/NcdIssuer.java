package com.sumscope.qpwb.ncd.data.model.qpwb;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mengyang.sun on 2018/01/19
 */
public class NcdIssuer implements Serializable {
    @ApiModelProperty(value = "机构代码", position = 1)
    private String issuerCode;
    @ApiModelProperty(value = "机构简称", position = 2)
    private String shortName;
    @ApiModelProperty(value = "机构全称", position = 3)
    private String fullName;
    @ApiModelProperty(value = "机构类型", position = 4)
    private String institutionType;
    @ApiModelProperty(value = "评级类型", position = 5)
    private String rate;
    @ApiModelProperty(value = "截止日期", position = 6)
    private String indexDate;
    @ApiModelProperty(value = "总资产 亿", position = 7)
    private String totalAsset;
    @ApiModelProperty(value = "净资产 亿", position = 8)
    private String netAsset;
    @ApiModelProperty(value = "营收 亿", position = 9)
    private String revenue;
    @ApiModelProperty(value = "净利润 亿", position = 10)
    private String netProfit;
    @ApiModelProperty(value = "存贷比 %", position = 11)
    private String ldp;
    @ApiModelProperty(value = "核心资本充足率 %", position = 12)
    private String ccar;
    @ApiModelProperty(value = "不良率 %", position = 13)
    private String badRatio;
    @ApiModelProperty(value = "机构ID", position = 14)
    private String issuerId;

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIndexDate() {
        return indexDate;
    }

    public void setIndexDate(String indexDate) {
        this.indexDate = indexDate;
    }

    public String getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public String getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(String netAsset) {
        this.netAsset = netAsset;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getLdp() {
        return ldp;
    }

    public void setLdp(String ldp) {
        this.ldp = ldp;
    }

    public String getCcar() {
        return ccar;
    }

    public void setCcar(String ccar) {
        this.ccar = ccar;
    }

    public String getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(String badRatio) {
        this.badRatio = badRatio;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

}
