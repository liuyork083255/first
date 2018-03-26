package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by liu.yang on 2018/1/19.
 */
@ApiModel(value = "预定消息体")
public class OrderDTO implements Serializable {
    @ApiModelProperty(value = "状态码", position = 1)
    private Integer status;
    @ApiModelProperty(value = "机构代码", position = 2)
    private String institutionCode;
    @ApiModelProperty(value = "剩余期限", position = 3)
    private String limit;
    @ApiModelProperty(value = "经纪商名称", position = 4)
    private String broker;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
