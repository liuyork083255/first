package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "经济商模型")
public class BrokerDTO {
    @ApiModelProperty(value = "经济商code",position = 1)
    private String key;
    @ApiModelProperty(value = "经济商名称",position = 2)
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
