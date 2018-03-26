package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Issuers {
    private String id;
    private String fullName;
    private String shortName;
    private String firstLevelOrder;
    private String secondLevelOrder;
    private BigDecimal totalAsset;
    private BigDecimal netAsset;
    private BigDecimal revenue;
    private BigDecimal netProfit;
    private BigDecimal ldp;
    private BigDecimal ccar;
    private BigDecimal badRatio;
    private String rate;
    private Timestamp indexDate;
    private String code;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "first_level_order")
    public String getFirstLevelOrder() {
        return firstLevelOrder;
    }

    public void setFirstLevelOrder(String firstLevelOrder) {
        this.firstLevelOrder = firstLevelOrder;
    }

    @Basic
    @Column(name = "second_level_order")
    public String getSecondLevelOrder() {
        return secondLevelOrder;
    }

    public void setSecondLevelOrder(String secondLevelOrder) {
        this.secondLevelOrder = secondLevelOrder;
    }

    @Basic
    @Column(name = "total_asset")
    public BigDecimal getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
        this.totalAsset = totalAsset;
    }

    @Basic
    @Column(name = "net_asset")
    public BigDecimal getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(BigDecimal netAsset) {
        this.netAsset = netAsset;
    }

    @Basic
    @Column(name = "revenue")
    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Basic
    @Column(name = "net_profit")
    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    @Basic
    @Column(name = "ldp")
    public BigDecimal getLdp() {
        return ldp;
    }

    public void setLdp(BigDecimal ldp) {
        this.ldp = ldp;
    }

    @Basic
    @Column(name = "ccar")
    public BigDecimal getCcar() {
        return ccar;
    }

    public void setCcar(BigDecimal ccar) {
        this.ccar = ccar;
    }

    @Basic
    @Column(name = "bad_ratio")
    public BigDecimal getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(BigDecimal badRatio) {
        this.badRatio = badRatio;
    }

    @Basic
    @Column(name = "rate")
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "index_date")
    public Timestamp getIndexDate() {
        return indexDate;
    }

    public void setIndexDate(Timestamp indexDate) {
        this.indexDate = indexDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issuers issuers = (Issuers) o;
        return Objects.equals(id, issuers.id) &&
                Objects.equals(fullName, issuers.fullName) &&
                Objects.equals(shortName, issuers.shortName) &&
                Objects.equals(firstLevelOrder, issuers.firstLevelOrder) &&
                Objects.equals(secondLevelOrder, issuers.secondLevelOrder) &&
                Objects.equals(totalAsset, issuers.totalAsset) &&
                Objects.equals(netAsset, issuers.netAsset) &&
                Objects.equals(revenue, issuers.revenue) &&
                Objects.equals(netProfit, issuers.netProfit) &&
                Objects.equals(ldp, issuers.ldp) &&
                Objects.equals(ccar, issuers.ccar) &&
                Objects.equals(badRatio, issuers.badRatio) &&
                Objects.equals(rate, issuers.rate) &&
                Objects.equals(indexDate, issuers.indexDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, fullName, shortName, firstLevelOrder, secondLevelOrder, totalAsset, netAsset, revenue, netProfit, ldp, ccar, badRatio, rate, indexDate);
    }


    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
