package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "报价信息")
public class QuoteInfoDTO implements Serializable{
    @ApiModelProperty(value = "价格", position = 1)
    private BigDecimal price;
    @ApiModelProperty(value = "发行数量", position = 2)
    private String volume;
    @ApiModelProperty(value = "价格变动", position = 3)
    private BigDecimal change;
    @ApiModelProperty(value = "是否讯满", position = 4)
    private boolean available;
//    @ApiModelProperty(value = "报价明细（时间、价格）", position = 5)
//    private List<QuoteDetailDTO> detail;
    @ApiModelProperty(value = "报价时间", position = 6)
    private String quoteTime;
    @ApiModelProperty(value = "状态（讯满:finish|未预定:without|已预订:pending）", position = 7)
    private String status;
    @ApiModelProperty(value = "备注", position = 8)
    private String mark;
//    @ApiModelProperty(value = "明细id(在明细页面用来获取订阅状态)", position = 9)
//    private Long detailId;
    @ApiModelProperty(value = "报价id(用来获取时间和报价列表)", position = 10)
    private String offerId;


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(String quoteTime) {
        this.quoteTime = quoteTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

//    public Long getDetailId() {
//        return detailId;
//    }
//
//    public void setDetailId(Long detailId) {
//        this.detailId = detailId;
//    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
}
