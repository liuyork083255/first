package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "quote_details", schema = "wf_web_ncd", catalog = "")
public class QuoteDetails {
    private long id;
    private Long flowId;
    private String offerId;
    private String issueDeptId;
    private String goodsLevel;
    private String issueDay;
    private String terminal;
    private BigDecimal issuePrice;
    private String issuePriceFlag;
    private String planedIssueAmount;
    private String remark;
    private String goodStatus;
    private String status;
    private Timestamp createTime;
    private Timestamp modifyTime;
    private String enterpriseType;
    private Date issueDate;
    private String description;
    private String priceChange;
    private String actualIssueAmount;
    private String brokerId;
    private String brokerName;
    private String flagVip;
    private String priceType;
    private Byte nonBank;
    private Byte available;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "flow_id")
    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    @Basic
    @Column(name = "offer_id")
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Basic
    @Column(name = "issue_dept_id")
    public String getIssueDeptId() {
        return issueDeptId;
    }

    public void setIssueDeptId(String issueDeptId) {
        this.issueDeptId = issueDeptId;
    }

    @Basic
    @Column(name = "goods_level")
    public String getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(String goodsLevel) {
        this.goodsLevel = goodsLevel;
    }

    @Basic
    @Column(name = "issue_day")
    public String getIssueDay() {
        return issueDay;
    }

    public void setIssueDay(String issueDay) {
        this.issueDay = issueDay;
    }

    @Basic
    @Column(name = "terminal")
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Basic
    @Column(name = "issue_price")
    public BigDecimal getIssuePrice() {
        return issuePrice;
    }

    public void setIssuePrice(BigDecimal issuePrice) {
        this.issuePrice = issuePrice;
    }

    @Basic
    @Column(name = "issue_price_flag")
    public String getIssuePriceFlag() {
        return issuePriceFlag;
    }

    public void setIssuePriceFlag(String issuePriceFlag) {
        this.issuePriceFlag = issuePriceFlag;
    }

    @Basic
    @Column(name = "planed_issue_amount")
    public String getPlanedIssueAmount() {
        return planedIssueAmount;
    }

    public void setPlanedIssueAmount(String planedIssueAmount) {
        this.planedIssueAmount = planedIssueAmount;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "good_status")
    public String getGoodStatus() {
        return goodStatus;
    }

    public void setGoodStatus(String goodStatus) {
        this.goodStatus = goodStatus;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "enterprise_type")
    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    @Basic
    @Column(name = "issue_date")
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "price_change")
    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    @Basic
    @Column(name = "actual_issue_amount")
    public String getActualIssueAmount() {
        return actualIssueAmount;
    }

    public void setActualIssueAmount(String actualIssueAmount) {
        this.actualIssueAmount = actualIssueAmount;
    }

    @Basic
    @Column(name = "broker_id")
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "broker_name")
    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    @Basic
    @Column(name = "flag_vip")
    public String getFlagVip() {
        return flagVip;
    }

    public void setFlagVip(String flagVip) {
        this.flagVip = flagVip;
    }

    @Basic
    @Column(name = "price_type")
    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    @Basic
    @Column(name = "non_bank")
    public Byte getNonBank() {
        return nonBank;
    }

    public void setNonBank(Byte nonBank) {
        this.nonBank = nonBank;
    }

    @Basic
    @Column(name = "available")
    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteDetails that = (QuoteDetails) o;
        return id == that.id &&
                Objects.equals(flowId, that.flowId) &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(issueDeptId, that.issueDeptId) &&
                Objects.equals(goodsLevel, that.goodsLevel) &&
                Objects.equals(issueDay, that.issueDay) &&
                Objects.equals(terminal, that.terminal) &&
                Objects.equals(issuePrice, that.issuePrice) &&
                Objects.equals(issuePriceFlag, that.issuePriceFlag) &&
                Objects.equals(planedIssueAmount, that.planedIssueAmount) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(goodStatus, that.goodStatus) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(enterpriseType, that.enterpriseType) &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(priceChange, that.priceChange) &&
                Objects.equals(actualIssueAmount, that.actualIssueAmount) &&
                Objects.equals(brokerId, that.brokerId) &&
                Objects.equals(brokerName, that.brokerName) &&
                Objects.equals(flagVip, that.flagVip) &&
                Objects.equals(priceType, that.priceType) &&
                Objects.equals(nonBank, that.nonBank) &&
                Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, flowId, offerId, issueDeptId, goodsLevel, issueDay, terminal, issuePrice, issuePriceFlag, planedIssueAmount, remark, goodStatus, status, createTime, modifyTime, enterpriseType, issueDate, description, priceChange, actualIssueAmount, brokerId, brokerName, flagVip, priceType, nonBank, available);
    }
}
