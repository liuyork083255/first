package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu.yang on 2018/1/19.
 */
@ApiModel(value = "获取用户筛选条件")
public class FilterDTO implements Serializable {
    @ApiModelProperty(value = "评级类型", position = 1)
    private List<String> rate;
    @ApiModelProperty(value = "机构类型", position = 2)
    private List<String> institutionType;
    @ApiModelProperty(value = "总资产范围", position = 3)
    private List<BigDecimal> totalAsset;
    @ApiModelProperty(value = "净资产范围", position = 4)
    private List<BigDecimal> netAsset;
    @ApiModelProperty(value = "营收范围", position = 5)
    private List<BigDecimal> revenue;
    @ApiModelProperty(value = "净利润范围", position = 6)
    private List<BigDecimal> netProfit;
    @ApiModelProperty(value = "存贷比范围 %", position = 7)
    private List<BigDecimal> ldp;
    @ApiModelProperty(value = "核心资本充足率范围 %", position = 8)
    private List<BigDecimal>  ccar;
    @ApiModelProperty(value = "不良率范围 %", position = 9)
    private List<BigDecimal> badRatio;
    @ApiModelProperty(value = "判断当前用户是否有保存过滤条件", position = 10)
    private boolean saveFlag;

    public FilterDTO(){
        rate = new ArrayList<>();rate.add("ALL");
        institutionType = new ArrayList<>();institutionType.add("ALL");
        totalAsset = new ArrayList<>();totalAsset.add(BigDecimal.ZERO);totalAsset.add(BigDecimal.ZERO);
        netAsset = new ArrayList<>();netAsset.add(BigDecimal.ZERO);netAsset.add(BigDecimal.ZERO);
        revenue = new ArrayList<>();revenue.add(BigDecimal.ZERO);revenue.add(BigDecimal.ZERO);
        netProfit = new ArrayList<>();netProfit.add(BigDecimal.ZERO);netProfit.add(BigDecimal.ZERO);
        ldp = new ArrayList<>();ldp.add(BigDecimal.ZERO);ldp.add(BigDecimal.ZERO);
        ccar = new ArrayList<>();ccar.add(BigDecimal.ZERO);ccar.add(BigDecimal.ZERO);
        badRatio = new ArrayList<>();badRatio.add(BigDecimal.ZERO);badRatio.add(BigDecimal.ZERO);
    }

    public List<String> getRate() {
        return rate;
    }

    public void setRate(List<String> rate) {
        this.rate = rate;
    }

    public List<String> getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(List<String> institutionType) {
        this.institutionType = institutionType;
    }

    public List<BigDecimal> getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(List<BigDecimal> totalAsset) {
        this.totalAsset = totalAsset;
    }

    public List<BigDecimal> getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(List<BigDecimal> netAsset) {
        this.netAsset = netAsset;
    }

    public List<BigDecimal> getRevenue() {
        return revenue;
    }

    public void setRevenue(List<BigDecimal> revenue) {
        this.revenue = revenue;
    }

    public List<BigDecimal> getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(List<BigDecimal> netProfit) {
        this.netProfit = netProfit;
    }

    public List<BigDecimal> getLdp() {
        return ldp;
    }

    public void setLdp(List<BigDecimal> ldp) {
        this.ldp = ldp;
    }

    public List<BigDecimal> getCcar() {
        return ccar;
    }

    public void setCcar(List<BigDecimal> ccar) {
        this.ccar = ccar;
    }

    public List<BigDecimal> getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(List<BigDecimal> badRatio) {
        this.badRatio = badRatio;
    }

    public boolean isSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(boolean saveFlag) {
        this.saveFlag = saveFlag;
    }
}
