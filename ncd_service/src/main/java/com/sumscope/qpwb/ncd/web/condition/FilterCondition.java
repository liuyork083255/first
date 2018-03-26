package com.sumscope.qpwb.ncd.web.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by liu.yang on 2018/1/11.
 */
@ApiModel(value = "保存用户过滤条件")
public class FilterCondition {
    @ApiModelProperty(value = "评级", position = 1)
    @NotEmpty(message = "rate(评级) can not be null")
    private List<String> rate;
    @ApiModelProperty(value = "银行类型", position = 2)
    @NotEmpty(message = "institutionType(银行类型) can not be null")
    private List<String> institutionType;
    @ApiModelProperty(value = "总资产", position = 3)
    private String totalAsset;
    @ApiModelProperty(value = "净资产", position = 4)
    private String netAsset;
    @ApiModelProperty(value = "营收", position = 5)
    private String revenue;
    @ApiModelProperty(value = "净利润", position = 6)
    private String netProfit;
    @ApiModelProperty(value = "存贷比", position = 7)
    private String ldp;
    @ApiModelProperty(value = "核心资本充足率", position = 8)
    private String ccar;
    @ApiModelProperty(value = "不良率", position = 9)
    private String badRatio;
    @ApiModelProperty(value = "用户id", position = 10)
    @NotBlank(message = "用户id 不能为空")
    private String userId;
    @ApiModelProperty(value = "经济商id", position = 11)
    private String brokerId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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

    public String getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public String getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(String netAsset) {
        this.netAsset = netAsset;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getLdp() {
        return ldp;
    }

    public void setLdp(String ldp) {
        this.ldp = ldp;
    }

    public String getCcar() {
        return ccar;
    }

    public void setCcar(String ccar) {
        this.ccar = ccar;
    }

    public String getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(String badRatio) {
        this.badRatio = badRatio;
    }
}
