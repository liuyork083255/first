package com.sumscope.cdh.realtime.transfer.model.db;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by liu.yang on 2017/9/27.
 */
public class TargetTradeDBModel {
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
    private String settlementDate;

    private String businessCode;

    public String getBondKey() {
        return bondKey;
    }

    public void setBondKey(String bondKey) {
        this.bondKey = bondKey;
    }

    public String getListedMarket() {
        return listedMarket;
    }

    public void setListedMarket(String listedMarket) {
        this.listedMarket = listedMarket;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getRemaintTime() {
        return remaintTime;
    }

    public void setRemaintTime(String remaintTime) {
        this.remaintTime = remaintTime;
    }

    public String getBondCode() {
        return bondCode;
    }

    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getCdcValuation() {
        return cdcValuation;
    }

    public void setCdcValuation(String cdcValuation) {
        this.cdcValuation = cdcValuation;
    }

    public String getCsiValuation() {
        return csiValuation;
    }

    public void setCsiValuation(String csiValuation) {
        this.csiValuation = csiValuation;
    }

    public String getIssuerBondRating() {
        return issuerBondRating;
    }

    public void setIssuerBondRating(String issuerBondRating) {
        this.issuerBondRating = issuerBondRating;
    }

    public String getExpection() {
        return expection;
    }

    public void setExpection(String expection) {
        this.expection = expection;
    }

    public String getRatingInstitutionName() {
        return ratingInstitutionName;
    }

    public void setRatingInstitutionName(String ratingInstitutionName) {
        this.ratingInstitutionName = ratingInstitutionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRemaintTimeValue() {
        return remaintTimeValue;
    }

    public void setRemaintTimeValue(Integer remaintTimeValue) {
        this.remaintTimeValue = remaintTimeValue;
    }

    public Double getDealPriceValue() {
        return dealPriceValue;
    }

    public void setDealPriceValue(Double dealPriceValue) {
        this.dealPriceValue = dealPriceValue;
    }

    public Double getCdcValuationValue() {
        return cdcValuationValue;
    }

    public void setCdcValuationValue(Double cdcValuationValue) {
        this.cdcValuationValue = cdcValuationValue;
    }

    public Double getCsiValuationValue() {
        return csiValuationValue;
    }

    public void setCsiValuationValue(Double csiValuationValue) {
        this.csiValuationValue = csiValuationValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPinyinFull() {
        return pinyinFull;
    }

    public void setPinyinFull(String pinyinFull) {
        this.pinyinFull = pinyinFull;
    }

    public String getRatingInstitutionPinyin() {
        return ratingInstitutionPinyin;
    }

    public void setRatingInstitutionPinyin(String ratingInstitutionPinyin) {
        this.ratingInstitutionPinyin = ratingInstitutionPinyin;
    }

    public String getExpectionValue() {
        return expectionValue;
    }

    public void setExpectionValue(String expectionValue) {
        this.expectionValue = expectionValue;
    }

    public Integer getExpectionSortValue() {
        return expectionSortValue;
    }

    public void setExpectionSortValue(Integer expectionSortValue) {
        this.expectionSortValue = expectionSortValue;
    }

    public Integer getRestSymbolDays() {
        return restSymbolDays;
    }

    public void setRestSymbolDays(Integer restSymbolDays) {
        this.restSymbolDays = restSymbolDays;
    }

    public BigDecimal getCleanPrice() {
        return cleanPrice;
    }

    public void setCleanPrice(BigDecimal cleanPrice) {
        this.cleanPrice = cleanPrice;
    }

    public BigDecimal getYtm() {
        return ytm;
    }

    public void setYtm(BigDecimal ytm) {
        this.ytm = ytm;
    }

    public Integer getRestSymbolDaysExchange() {
        return restSymbolDaysExchange;
    }

    public void setRestSymbolDaysExchange(Integer restSymbolDaysExchange) {
        this.restSymbolDaysExchange = restSymbolDaysExchange;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
