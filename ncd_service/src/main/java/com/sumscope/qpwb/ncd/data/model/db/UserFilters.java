package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_filters", schema = "web_ncd", catalog = "")
public class UserFilters {
    private String userId;
    private String brokerId;
    private String rating;
    private String institutionType;
    private String totalAsset;
    private String netAsset;
    private String revenue;
    private String netProfit;
    private String ldp;
    private String ccar;
    private String badRatio;
    @Transient
    private long autoId;

    @Id
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    @Column(name = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "institution_type")
    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    @Basic
    @Column(name = "total_asset")
    public String getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    @Basic
    @Column(name = "net_asset")
    public String getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(String netAsset) {
        this.netAsset = netAsset;
    }

    @Basic
    @Column(name = "revenue")
    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    @Basic
    @Column(name = "net_profit")
    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    @Basic
    @Column(name = "ldp")
    public String getLdp() {
        return ldp;
    }

    public void setLdp(String ldp) {
        this.ldp = ldp;
    }

    @Basic
    @Column(name = "ccar")
    public String getCcar() {
        return ccar;
    }

    public void setCcar(String ccar) {
        this.ccar = ccar;
    }

    @Basic
    @Column(name = "bad_ratio")
    public String getBadRatio() {
        return badRatio;
    }

    public void setBadRatio(String badRatio) {
        this.badRatio = badRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFilters that = (UserFilters) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(brokerId, that.brokerId) &&
                Objects.equals(rating, that.rating) &&
                Objects.equals(institutionType, that.institutionType) &&
                Objects.equals(totalAsset, that.totalAsset) &&
                Objects.equals(netAsset, that.netAsset) &&
                Objects.equals(revenue, that.revenue) &&
                Objects.equals(netProfit, that.netProfit) &&
                Objects.equals(ldp, that.ldp) &&
                Objects.equals(ccar, that.ccar) &&
                Objects.equals(badRatio, that.badRatio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, brokerId, rating, institutionType, totalAsset, netAsset, revenue, netProfit, ldp, ccar, badRatio);
    }

    @Column(name = "auto_id")
    @Transient
    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }
}
