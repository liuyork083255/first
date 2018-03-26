package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by mengyang.sun on 2018/01/19.
 */
@ApiModel(value = "经济商期限信息")
public class BrokerLimitDTO implements Serializable {
    @ApiModelProperty(value = "期限1m", position = 1)
    private QuoteInfoDTO m1;
    @ApiModelProperty(value = "期限3m", position = 2)
    private QuoteInfoDTO m3;
    @ApiModelProperty(value = "期限6m", position = 3)
    private QuoteInfoDTO m6;
    @ApiModelProperty(value = "期限9m", position = 4)
    private QuoteInfoDTO m9;
    @ApiModelProperty(value = "期限1y", position = 5)
    private QuoteInfoDTO y1;

    public QuoteInfoDTO getM1() {
        return m1;
    }

    public void setM1(QuoteInfoDTO m1) {
        this.m1 = m1;
    }

    public QuoteInfoDTO getM3() {
        return m3;
    }

    public void setM3(QuoteInfoDTO m3) {
        this.m3 = m3;
    }

    public QuoteInfoDTO getM6() {
        return m6;
    }

    public void setM6(QuoteInfoDTO m6) {
        this.m6 = m6;
    }

    public QuoteInfoDTO getM9() {
        return m9;
    }

    public void setM9(QuoteInfoDTO m9) {
        this.m9 = m9;
    }

    public QuoteInfoDTO getY1() {
        return y1;
    }

    public void setY1(QuoteInfoDTO y1) {
        this.y1 = y1;
    }

}
