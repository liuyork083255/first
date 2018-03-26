package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mengyang.sun on 2018/01/19.
 */
@ApiModel(value = "报价列表（报价机构信息、限非银、含发行量报价）")
public class MatrixDTO implements Serializable {
    @ApiModelProperty(value = "报价机构列表", position = 1)
    private List<QuoteIssuerDTO> matrix;
    @ApiModelProperty(value = "限非银列表", position = 2)
    private List<NonBankDTO> nonBank;
    @ApiModelProperty(value = "含发行量报价列表", position = 3)
    private List<ContainVolumeDTO> containVolume;

    public List<NonBankDTO> getNonBank() {
        return nonBank;
    }

    public void setNonBank(List<NonBankDTO> nonBank) {
        this.nonBank = nonBank;
    }

    public List<ContainVolumeDTO> getContainVolume() {
        return containVolume;
    }

    public void setContainVolume(List<ContainVolumeDTO> containVolume) {
        this.containVolume = containVolume;
    }

    public List<QuoteIssuerDTO> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<QuoteIssuerDTO> matrix) {
        this.matrix = matrix;
    }
}
