package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by liu.yang on 2018/1/19.
 */
@ApiModel(value = "单一状态返回体")
public class NullDTO implements Serializable {

    public NullDTO(String msg){
        this.message = msg;
    }
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
