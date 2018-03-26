package com.sumscope.cdh.webbond.generated;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by chengzhang.wang on 2017/7/26.
 */
@Entity
@Table(name = "webbond_trade", catalog = "")
@IdClass(WebbondTradePK.class)
public class WebbondTrade implements Serializable
{
    private String bondKey;
    private String listedMarket;
    private String tradeId;
    private String brokerId;
    private String brokerName;
    private String dealType;
    private String remaintTime;
    private String bondCode;
    private String shortName;
    private String dealPrice;
    private String cdcValuation;
    private String csiValuation;
    private String issuerBondRating;
    private String expection;
    private String ratingInstitutionName;
    private String updateTime;
    private Integer remaintTimeValue;
    private Double dealPriceValue;
    private Double cdcValuationValue;
    private Double csiValuationValue;
    private Integer status;
    private Integer dealStatus;
    private String updateDateTime;
    private String createDateTime;
    private String transType;
    private String pinyinFull;
    private String ratingInstitutionPinyin;
    private String expectionValue;
    private Integer expectionSortValue;
    private Integer restSymbolDays;
    private BigDecimal cleanPrice;
    private BigDecimal ytm;
    private Integer restSymbolDaysExchange;

    @Id
    @Column(name = "bondKey", nullable = false, length = 100)
    public String getBondKey() {
        return bondKey;
    }

    public void setBondKey(String bondKey) {
        this.bondKey = bondKey;
    }

    @Id
    @Column(name = "listedMarket", nullable = false, length = 10)
    public String getListedMarket() {
        return listedMarket;
    }

    public void setListedMarket(String listedMarket) {
        this.listedMarket = listedMarket;
    }

    @Id
    @Column(name = "tradeId", nullable = false, length = 200)
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Basic
    @Column(name = "brokerId", nullable = true, length = 10)
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "brokerName", nullable = true, length = 200)
    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    @Basic
    @Column(name = "dealType", nullable = true, length = 20)
    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    @Basic
    @Column(name = "remaintTime", nullable = true, length = 20)
    public String getRemaintTime() {
        return remaintTime;
    }

    public void setRemaintTime(String remaintTime) {
        this.remaintTime = remaintTime;
    }

    @Basic
    @Column(name = "bondCode", nullable = true, length = 20)
    public String getBondCode() {
        return bondCode;
    }

    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    @Basic
    @Column(name = "shortName", nullable = true, length = 200)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "dealPrice", nullable = true, length = 100)
    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    @Basic
    @Column(name = "cdcValuation", nullable = true, length = 100)
    public String getCdcValuation() {
        return cdcValuation;
    }

    public void setCdcValuation(String cdcValuation) {
        this.cdcValuation = cdcValuation;
    }

    @Basic
    @Column(name = "csiValuation", nullable = true, length = 100)
    public String getCsiValuation() {
        return csiValuation;
    }

    public void setCsiValuation(String csiValuation) {
        this.csiValuation = csiValuation;
    }

    @Basic
    @Column(name = "issuerBondRating", nullable = true, length = 20)
    public String getIssuerBondRating() {
        return issuerBondRating;
    }

    public void setIssuerBondRating(String issuerBondRating) {
        this.issuerBondRating = issuerBondRating;
    }

    @Basic
    @Column(name = "expection", nullable = true, length = 20)
    public String getExpection() {
        return expection;
    }

    public void setExpection(String expection) {
        this.expection = expection;
    }

    @Basic
    @Column(name = "ratingInstitutionName", nullable = true, length = 200)
    public String getRatingInstitutionName() {
        return ratingInstitutionName;
    }

    public void setRatingInstitutionName(String ratingInstitutionName) {
        this.ratingInstitutionName = ratingInstitutionName;
    }

    @Basic
    @Column(name = "updateTime", nullable = true, length = 20)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "remaintTimeValue", nullable = true)
    public Integer getRemaintTimeValue() {
        return remaintTimeValue;
    }

    public void setRemaintTimeValue(Integer remaintTimeValue) {
        this.remaintTimeValue = remaintTimeValue;
    }

    @Basic
    @Column(name = "dealPriceValue", nullable = true, precision = 6)
    public Double getDealPriceValue() {
        return dealPriceValue;
    }

    public void setDealPriceValue(Double dealPriceValue) {
        this.dealPriceValue = dealPriceValue;
    }

    @Basic
    @Column(name = "cdcValuationValue", nullable = true, precision = 6)
    public Double getCdcValuationValue() {
        return cdcValuationValue;
    }

    public void setCdcValuationValue(Double cdcValuationValue) {
        this.cdcValuationValue = cdcValuationValue;
    }

    @Basic
    @Column(name = "csiValuationValue", nullable = true, precision = 6)
    public Double getCsiValuationValue() {
        return csiValuationValue;
    }

    public void setCsiValuationValue(Double csiValuationValue) {
        this.csiValuationValue = csiValuationValue;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "dealStatus", nullable = true)
    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    @Basic
    @Column(name = "updateDateTime", nullable = true, length = 100)
    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Basic
    @Column(name = "createDateTime", nullable = true, length = 100)
    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "transType", nullable = true, length = 100)
    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    @Basic
    @Column(name = "pinyinFull", nullable = true, length = 1024)
    public String getPinyinFull() {
        return pinyinFull;
    }

    public void setPinyinFull(String pinyinFull) {
        this.pinyinFull = pinyinFull;
    }

    @Basic
    @Column(name = "ratingInstitutionPinyin", nullable = true, length = 1024)
    public String getRatingInstitutionPinyin() {
        return ratingInstitutionPinyin;
    }

    public void setRatingInstitutionPinyin(String ratingInstitutionPinyin) {
        this.ratingInstitutionPinyin = ratingInstitutionPinyin;
    }

    @Basic
    @Column(name = "expectionValue", nullable = true, length = 200)
    public String getExpectionValue() {
        return expectionValue;
    }

    public void setExpectionValue(String expectionValue) {
        this.expectionValue = expectionValue;
    }

    @Basic
    @Column(name = "expectionSortValue", nullable = true)
    public Integer getExpectionSortValue() {
        return expectionSortValue;
    }

    public void setExpectionSortValue(Integer expectionSortValue) {
        this.expectionSortValue = expectionSortValue;
    }

    @Basic
    @Column(name = "restSymbolDays", nullable = true)
    public Integer getRestSymbolDays() {
        return restSymbolDays;
    }

    public void setRestSymbolDays(Integer restSymbolDays) {
        this.restSymbolDays = restSymbolDays;
    }

    @Basic
    @Column(name = "cleanPrice", nullable = true, precision = 4)
    public BigDecimal getCleanPrice() {
        return cleanPrice;
    }

    public void setCleanPrice(BigDecimal cleanPrice) {
        this.cleanPrice = cleanPrice;
    }

    @Basic
    @Column(name = "ytm", nullable = true, precision = 4)
    public BigDecimal getYtm() {
        return ytm;
    }

    public void setYtm(BigDecimal ytm) {
        this.ytm = ytm;
    }

    @Basic
    @Column(name = "restSymbolDaysExchange", nullable = true)
    public Integer getRestSymbolDaysExchange() {
        return restSymbolDaysExchange;
    }

    public void setRestSymbolDaysExchange(Integer restSymbolDaysExchange) {
        this.restSymbolDaysExchange = restSymbolDaysExchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebbondTrade that = (WebbondTrade) o;

        if (bondKey != null ? !bondKey.equals(that.bondKey) : that.bondKey != null) return false;
        if (listedMarket != null ? !listedMarket.equals(that.listedMarket) : that.listedMarket != null) return false;
        if (tradeId != null ? !tradeId.equals(that.tradeId) : that.tradeId != null) return false;
        if (brokerId != null ? !brokerId.equals(that.brokerId) : that.brokerId != null) return false;
        if (brokerName != null ? !brokerName.equals(that.brokerName) : that.brokerName != null) return false;
        if (dealType != null ? !dealType.equals(that.dealType) : that.dealType != null) return false;
        if (remaintTime != null ? !remaintTime.equals(that.remaintTime) : that.remaintTime != null) return false;
        if (bondCode != null ? !bondCode.equals(that.bondCode) : that.bondCode != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (dealPrice != null ? !dealPrice.equals(that.dealPrice) : that.dealPrice != null) return false;
        if (cdcValuation != null ? !cdcValuation.equals(that.cdcValuation) : that.cdcValuation != null) return false;
        if (csiValuation != null ? !csiValuation.equals(that.csiValuation) : that.csiValuation != null) return false;
        if (issuerBondRating != null ? !issuerBondRating.equals(that.issuerBondRating) : that.issuerBondRating != null)
            return false;
        if (expection != null ? !expection.equals(that.expection) : that.expection != null) return false;
        if (ratingInstitutionName != null ? !ratingInstitutionName.equals(that.ratingInstitutionName) : that.ratingInstitutionName != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (remaintTimeValue != null ? !remaintTimeValue.equals(that.remaintTimeValue) : that.remaintTimeValue != null)
            return false;
        if (dealPriceValue != null ? !dealPriceValue.equals(that.dealPriceValue) : that.dealPriceValue != null)
            return false;
        if (cdcValuationValue != null ? !cdcValuationValue.equals(that.cdcValuationValue) : that.cdcValuationValue != null)
            return false;
        if (csiValuationValue != null ? !csiValuationValue.equals(that.csiValuationValue) : that.csiValuationValue != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (dealStatus != null ? !dealStatus.equals(that.dealStatus) : that.dealStatus != null) return false;
        if (updateDateTime != null ? !updateDateTime.equals(that.updateDateTime) : that.updateDateTime != null)
            return false;
        if (createDateTime != null ? !createDateTime.equals(that.createDateTime) : that.createDateTime != null)
            return false;
        if (transType != null ? !transType.equals(that.transType) : that.transType != null) return false;
        if (pinyinFull != null ? !pinyinFull.equals(that.pinyinFull) : that.pinyinFull != null) return false;
        if (ratingInstitutionPinyin != null ? !ratingInstitutionPinyin.equals(that.ratingInstitutionPinyin) : that.ratingInstitutionPinyin != null)
            return false;
        if (expectionValue != null ? !expectionValue.equals(that.expectionValue) : that.expectionValue != null)
            return false;
        if (expectionSortValue != null ? !expectionSortValue.equals(that.expectionSortValue) : that.expectionSortValue != null)
            return false;
        if (restSymbolDays != null ? !restSymbolDays.equals(that.restSymbolDays) : that.restSymbolDays != null)
            return false;
        if (cleanPrice != null ? !cleanPrice.equals(that.cleanPrice) : that.cleanPrice != null) return false;
        if (ytm != null ? !ytm.equals(that.ytm) : that.ytm != null) return false;
        if (restSymbolDaysExchange != null ? !restSymbolDaysExchange.equals(that.restSymbolDaysExchange) : that.restSymbolDaysExchange != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bondKey != null ? bondKey.hashCode() : 0;
        result = 31 * result + (listedMarket != null ? listedMarket.hashCode() : 0);
        result = 31 * result + (tradeId != null ? tradeId.hashCode() : 0);
        result = 31 * result + (brokerId != null ? brokerId.hashCode() : 0);
        result = 31 * result + (brokerName != null ? brokerName.hashCode() : 0);
        result = 31 * result + (dealType != null ? dealType.hashCode() : 0);
        result = 31 * result + (remaintTime != null ? remaintTime.hashCode() : 0);
        result = 31 * result + (bondCode != null ? bondCode.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (dealPrice != null ? dealPrice.hashCode() : 0);
        result = 31 * result + (cdcValuation != null ? cdcValuation.hashCode() : 0);
        result = 31 * result + (csiValuation != null ? csiValuation.hashCode() : 0);
        result = 31 * result + (issuerBondRating != null ? issuerBondRating.hashCode() : 0);
        result = 31 * result + (expection != null ? expection.hashCode() : 0);
        result = 31 * result + (ratingInstitutionName != null ? ratingInstitutionName.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (remaintTimeValue != null ? remaintTimeValue.hashCode() : 0);
        result = 31 * result + (dealPriceValue != null ? dealPriceValue.hashCode() : 0);
        result = 31 * result + (cdcValuationValue != null ? cdcValuationValue.hashCode() : 0);
        result = 31 * result + (csiValuationValue != null ? csiValuationValue.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dealStatus != null ? dealStatus.hashCode() : 0);
        result = 31 * result + (updateDateTime != null ? updateDateTime.hashCode() : 0);
        result = 31 * result + (createDateTime != null ? createDateTime.hashCode() : 0);
        result = 31 * result + (transType != null ? transType.hashCode() : 0);
        result = 31 * result + (pinyinFull != null ? pinyinFull.hashCode() : 0);
        result = 31 * result + (ratingInstitutionPinyin != null ? ratingInstitutionPinyin.hashCode() : 0);
        result = 31 * result + (expectionValue != null ? expectionValue.hashCode() : 0);
        result = 31 * result + (expectionSortValue != null ? expectionSortValue.hashCode() : 0);
        result = 31 * result + (restSymbolDays != null ? restSymbolDays.hashCode() : 0);
        result = 31 * result + (cleanPrice != null ? cleanPrice.hashCode() : 0);
        result = 31 * result + (ytm != null ? ytm.hashCode() : 0);
        result = 31 * result + (restSymbolDaysExchange != null ? restSymbolDaysExchange.hashCode() : 0);
        return result;
    }
}
