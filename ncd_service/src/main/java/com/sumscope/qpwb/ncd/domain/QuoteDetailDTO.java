package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "报价明细列表(时间、价格)")
public class QuoteDetailDTO implements Serializable {
    @ApiModelProperty(value = "时间", position = 1)
    private String time;
    @ApiModelProperty(value = "价格", position = 2)
    private BigDecimal price;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
