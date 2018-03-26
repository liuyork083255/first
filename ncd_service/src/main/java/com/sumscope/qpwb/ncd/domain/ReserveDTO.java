package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(value = "获取broker查看的页面")
public class ReserveDTO {
    @ApiModelProperty(value = "序列号", position = 1)
    private String id;
    @ApiModelProperty(value = "用户所属机构名称", position = 2)
    private String institutionName;
    @ApiModelProperty(value = "交易员名称", position = 3)
    private String trader;
    @ApiModelProperty(value = "剩余期限", position = 4)
    private String limit;
    @ApiModelProperty(value = "发行银行名称", position = 5)
    private String bankName;
    @ApiModelProperty(value = "交易价格", position = 6)
    private BigDecimal price;
    @ApiModelProperty(value = "日期时间", position = 7)
    private String dateTime;
    @ApiModelProperty(value = "是否为固息", position = 8)
    private boolean fixRate;

    @ApiModelProperty(value="用户qq", position = 8)
    private String qq;
    @ApiModelProperty(value="用户移动电话", position = 9)
    private String mobile;
    @ApiModelProperty(value="用户固定电话", position = 10)
    private String telephone;
    @ApiModelProperty(value="经济商id", position = 11)
    private String brokerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public boolean isFixRate() {
        return fixRate;
    }

    public void setFixRate(boolean fixRate) {
        this.fixRate = fixRate;
    }
}
