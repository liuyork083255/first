package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by mengyang.sun on 2018/01/19.
 */
@ApiModel(value = "报价银行机构列表")
public class QuoteIssuerDTO implements Serializable {
    @ApiModelProperty(value = "评级", position = 1)
    private String rate;
    @ApiModelProperty(value = "机构代码", position = 2)
    private String issuerCode;
    @ApiModelProperty(value = "机构名称", position = 3)
    private String issuerName;
    @ApiModelProperty(value = "是否推荐", position = 5)
    private boolean recommend;
    @ApiModelProperty(value = "期限为M1", position = 6)
    private QuoteInfoDTO M1;
    @ApiModelProperty(value = "期限为M3", position = 7)
    private QuoteInfoDTO M3;
    @ApiModelProperty(value = "期限为M6", position = 8)
    private QuoteInfoDTO M6;
    @ApiModelProperty(value = "期限为M9", position = 9)
    private QuoteInfoDTO M9;
    @ApiModelProperty(value = "期限为Y1", position = 10)
    private QuoteInfoDTO Y1;
    @ApiModelProperty(value = "机构全称", position = 11)
    private String fullName;
    @ApiModelProperty(value= "发行日期", position = 12)
    private String quoteDate;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }


    public QuoteInfoDTO getM1() {
        return M1;
    }

    public void setM1(QuoteInfoDTO m1) {
        M1 = m1;
    }

    public QuoteInfoDTO getM3() {
        return M3;
    }

    public void setM3(QuoteInfoDTO m3) {
        M3 = m3;
    }

    public QuoteInfoDTO getM6() {
        return M6;
    }

    public void setM6(QuoteInfoDTO m6) {
        M6 = m6;
    }

    public QuoteInfoDTO getM9() {
        return M9;
    }

    public void setM9(QuoteInfoDTO m9) {
        M9 = m9;
    }

    public QuoteInfoDTO getY1() {
        return Y1;
    }

    public void setY1(QuoteInfoDTO y1) {
        Y1 = y1;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }
}
