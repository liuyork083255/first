package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * Created by mengyang.sun on 2018/01/19.
 */
@ApiModel(value = "机构明细信息）")
public class IssuerDetailDTO implements Serializable {
    @ApiModelProperty(value = "经济商code", position = 1)
    private String code;
    @ApiModelProperty(value = "经济商详细信息", position = 2)
    private BrokerLimitDTO  brokerDetail;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BrokerLimitDTO getBrokerDetail() {
        return brokerDetail;
    }

    public void setBrokerDetail(BrokerLimitDTO brokerDetail) {
        this.brokerDetail = brokerDetail;
    }

}
