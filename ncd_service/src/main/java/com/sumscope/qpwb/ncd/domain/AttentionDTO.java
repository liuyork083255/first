package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "用户关注列表")
public class AttentionDTO {
    @ApiModelProperty(value = "关注列表")
    private List<String> attentionList = new ArrayList<>();

    public List<String> getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(List<String> attentionList) {
        this.attentionList = attentionList;
    }
}
