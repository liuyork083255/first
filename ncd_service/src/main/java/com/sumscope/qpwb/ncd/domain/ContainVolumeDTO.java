package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by mengyang.sun on 2018/01/19.
 */
@ApiModel(value = "含发行量报价信息")
public class ContainVolumeDTO implements Serializable {
    @ApiModelProperty(value = "机构名称", position = 1)
    private String issuerName;
    @ApiModelProperty(value = "机构代码", position = 2)
    private String issuerCode;
    @ApiModelProperty(value = "期限", position = 3)
    private String limit;
    @ApiModelProperty(value = "报价评级", position = 4)
    private String quoteRate;
    @ApiModelProperty(value = "发行量", position = 5)
    private String volume;
    @ApiModelProperty(value = "报价时间", position = 6)
    private String quoteTime;
    @ApiModelProperty(value = "评级", position = 7)
    private String rate;
    @ApiModelProperty(value= "发行日期", position = 8)
    private String quoteDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ApiModelProperty(value = "机构全称", position = 7)
    private String fullName;

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getQuoteRate() {
        return quoteRate;
    }

    public void setQuoteRate(String quoteRate) {
        this.quoteRate = quoteRate;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(String quoteTime) {
        this.quoteTime = quoteTime;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }
}
