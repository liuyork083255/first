package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu.yang on 2018/1/19.
 */
@ApiModel(value = "获取页面基本信息")
public class PageBasicDTO  implements Serializable {
    @ApiModelProperty(value = "起息日期", position = 1)
    private String issueDate;
    @ApiModelProperty(value = "发行日期", position = 2)
    private String startDate;
    @ApiModelProperty(value = "经纪商名称", position = 3)
    private List<BrokerDTO> brokers;
    @ApiModelProperty(value = "期限到期日 <到期日=今天+2个工作日+期限>", position = 4)
    private Map<String,HolidayDTO> dueDate;
    @ApiModelProperty(value = "评级列表")
    private List<String> rates;
    @ApiModelProperty(value = "机构代码", position = 5)
    private Map<String,List<String>> groupItems = new HashMap<>();
    @ApiModelProperty(value="经济商报表url", position = 6)
    private String BrokerReportUrl;

    @ApiModelProperty(value="用户qq", position = 7)
    private String qq;
    @ApiModelProperty(value="用户移动电话", position = 8)
    private String mobile;
    @ApiModelProperty(value="用户固定电话", position = 9)
    private String telephone;
    @ApiModelProperty(value="用户名", position = 10)
    private String userName;
    @ApiModelProperty(value="用户所属机构", position = 11)
    private String customerOrgName;
    @ApiModelProperty(value="用户所属机构代码", position = 12)
    private String companyCode;


    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<BrokerDTO> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<BrokerDTO> brokers) {
        this.brokers = brokers;
    }

    public Map<String, HolidayDTO> getDueDate() {
        return dueDate;
    }

    public void setDueDate(Map<String, HolidayDTO> dueDate) {
        this.dueDate = dueDate;
    }

    public List<String> getRates() {
        return rates;
    }

    public void setRates(List<String> rates) {
        this.rates = rates;
    }

    public Map<String, List<String>> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(Map<String, List<String>> groupItems) {
        this.groupItems = groupItems;
    }

    public String getBrokerReportUrl() {
        return BrokerReportUrl;
    }

    public void setBrokerReportUrl(String brokerReportUrl) {
        BrokerReportUrl = brokerReportUrl;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerOrgName() {
        return customerOrgName;
    }

    public void setCustomerOrgName(String customerOrgName) {
        this.customerOrgName = customerOrgName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
