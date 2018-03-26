package com.sumscope.cdh.realtime.transfer.model.db;

import java.math.BigDecimal;

/**
 * Created by liu.yang on 2017/9/27.
 */
public class TargetBboDBModel {
    /**
     *  UUID
     *  唯一标识
     */
    private String uuid;
    /**
     *  Mysql 分区字段
     */
    private int bboMonth;//(primary key)

    private String bondKey;
    private String listedMarket;
    private String brokerId;
    private String remaintTime;
    private String bondCode;
    private String shortName;
    private String latestTransaction;
    private String bidVolume;
    private String bid;
    private Integer bidBarginFlag;
    private String ofr;
    private String cdcValuation;
    private String csiValuation;
    private String issuerBondRating;
    private String expection;
    private String ratingInstitutionName;
    private String bidSubCdc;
    private String cdcSubOfr;
    private String bidSubCsi;
    private String csiSubOfr;
    private String brokerName;
    private String updateTime;
    private String duration;
    private Integer ofrBarginFlag;
    private String ofrVolume;
    private String bidPriceComment;
    private String ofrPriceComment;
    private Integer remaintTimeValue;
    private Double latestTransactionValue;
    private Double bidVolumeValue;
    private Double ofrVolumeValue;
    private Double bidValue;
    private Double ofrValue;
    private Double cdcValuationValue;
    private Double csiValuationValue;
    private Double bidSubCdcValue;
    private Double bidSubCsiValue;
    private Double cdcSubOfrValue;
    private Double csiSubOfrValue;
    private String updateDateTime;   // index
    private String createDateTime;   // index
    private String pinyinFull;
    private String ratingInstitutionPinyin;
    private String expectionValue;
    private Double bidVolumeSortValue;
    private Double ofrVolumeSortValue;
    private Integer expectionSortValue;
    private String ofrQuoteIds;
    private String bidQuoteIds;
    private Integer bidRelationFlag;
    private Integer ofrRelationFlag;
    private Integer restSymbolDays;
    private Integer bidIsExercise;
    private Integer ofrIsExercise;
    private BigDecimal bidCleanPrice;
    private BigDecimal ofrCleanPrice;
    private BigDecimal bidYtm;
    private BigDecimal ofrYtm;
    private BigDecimal bidRebate;
    private BigDecimal ofrRebate;
    private Integer restSymbolDaysExchange;
    private String businessCode;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getBboMonth() {
        return bboMonth;
    }

    public void setBboMonth(int bboMonth) {
        this.bboMonth = bboMonth;
    }

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

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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

    public String getLatestTransaction() {
        return latestTransaction;
    }

    public void setLatestTransaction(String latestTransaction) {
        this.latestTransaction = latestTransaction;
    }

    public String getBidVolume() {
        return bidVolume;
    }

    public void setBidVolume(String bidVolume) {
        this.bidVolume = bidVolume;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Integer getBidBarginFlag() {
        return bidBarginFlag;
    }

    public void setBidBarginFlag(Integer bidBarginFlag) {
        this.bidBarginFlag = bidBarginFlag;
    }

    public String getOfr() {
        return ofr;
    }

    public void setOfr(String ofr) {
        this.ofr = ofr;
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

    public String getBidSubCdc() {
        return bidSubCdc;
    }

    public void setBidSubCdc(String bidSubCdc) {
        this.bidSubCdc = bidSubCdc;
    }

    public String getCdcSubOfr() {
        return cdcSubOfr;
    }

    public void setCdcSubOfr(String cdcSubOfr) {
        this.cdcSubOfr = cdcSubOfr;
    }

    public String getBidSubCsi() {
        return bidSubCsi;
    }

    public void setBidSubCsi(String bidSubCsi) {
        this.bidSubCsi = bidSubCsi;
    }

    public String getCsiSubOfr() {
        return csiSubOfr;
    }

    public void setCsiSubOfr(String csiSubOfr) {
        this.csiSubOfr = csiSubOfr;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getOfrBarginFlag() {
        return ofrBarginFlag;
    }

    public void setOfrBarginFlag(Integer ofrBarginFlag) {
        this.ofrBarginFlag = ofrBarginFlag;
    }

    public String getOfrVolume() {
        return ofrVolume;
    }

    public void setOfrVolume(String ofrVolume) {
        this.ofrVolume = ofrVolume;
    }

    public String getBidPriceComment() {
        return bidPriceComment;
    }

    public void setBidPriceComment(String bidPriceComment) {
        this.bidPriceComment = bidPriceComment;
    }

    public String getOfrPriceComment() {
        return ofrPriceComment;
    }

    public void setOfrPriceComment(String ofrPriceComment) {
        this.ofrPriceComment = ofrPriceComment;
    }

    public Integer getRemaintTimeValue() {
        return remaintTimeValue;
    }

    public void setRemaintTimeValue(Integer remaintTimeValue) {
        this.remaintTimeValue = remaintTimeValue;
    }

    public Double getLatestTransactionValue() {
        return latestTransactionValue;
    }

    public void setLatestTransactionValue(Double latestTransactionValue) {
        this.latestTransactionValue = latestTransactionValue;
    }

    public Double getBidVolumeValue() {
        return bidVolumeValue;
    }

    public void setBidVolumeValue(Double bidVolumeValue) {
        this.bidVolumeValue = bidVolumeValue;
    }

    public Double getOfrVolumeValue() {
        return ofrVolumeValue;
    }

    public void setOfrVolumeValue(Double ofrVolumeValue) {
        this.ofrVolumeValue = ofrVolumeValue;
    }

    public Double getBidValue() {
        return bidValue;
    }

    public void setBidValue(Double bidValue) {
        this.bidValue = bidValue;
    }

    public Double getOfrValue() {
        return ofrValue;
    }

    public void setOfrValue(Double ofrValue) {
        this.ofrValue = ofrValue;
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

    public Double getBidSubCdcValue() {
        return bidSubCdcValue;
    }

    public void setBidSubCdcValue(Double bidSubCdcValue) {
        this.bidSubCdcValue = bidSubCdcValue;
    }

    public Double getBidSubCsiValue() {
        return bidSubCsiValue;
    }

    public void setBidSubCsiValue(Double bidSubCsiValue) {
        this.bidSubCsiValue = bidSubCsiValue;
    }

    public Double getCdcSubOfrValue() {
        return cdcSubOfrValue;
    }

    public void setCdcSubOfrValue(Double cdcSubOfrValue) {
        this.cdcSubOfrValue = cdcSubOfrValue;
    }

    public Double getCsiSubOfrValue() {
        return csiSubOfrValue;
    }

    public void setCsiSubOfrValue(Double csiSubOfrValue) {
        this.csiSubOfrValue = csiSubOfrValue;
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

    public Double getBidVolumeSortValue() {
        return bidVolumeSortValue;
    }

    public void setBidVolumeSortValue(Double bidVolumeSortValue) {
        this.bidVolumeSortValue = bidVolumeSortValue;
    }

    public Double getOfrVolumeSortValue() {
        return ofrVolumeSortValue;
    }

    public void setOfrVolumeSortValue(Double ofrVolumeSortValue) {
        this.ofrVolumeSortValue = ofrVolumeSortValue;
    }

    public Integer getExpectionSortValue() {
        return expectionSortValue;
    }

    public void setExpectionSortValue(Integer expectionSortValue) {
        this.expectionSortValue = expectionSortValue;
    }

    public String getOfrQuoteIds() {
        return ofrQuoteIds;
    }

    public void setOfrQuoteIds(String ofrQuoteIds) {
        this.ofrQuoteIds = ofrQuoteIds;
    }

    public String getBidQuoteIds() {
        return bidQuoteIds;
    }

    public void setBidQuoteIds(String bidQuoteIds) {
        this.bidQuoteIds = bidQuoteIds;
    }

    public Integer getBidRelationFlag() {
        return bidRelationFlag;
    }

    public void setBidRelationFlag(Integer bidRelationFlag) {
        this.bidRelationFlag = bidRelationFlag;
    }

    public Integer getOfrRelationFlag() {
        return ofrRelationFlag;
    }

    public void setOfrRelationFlag(Integer ofrRelationFlag) {
        this.ofrRelationFlag = ofrRelationFlag;
    }

    public Integer getRestSymbolDays() {
        return restSymbolDays;
    }

    public void setRestSymbolDays(Integer restSymbolDays) {
        this.restSymbolDays = restSymbolDays;
    }

    public Integer getBidIsExercise() {
        return bidIsExercise;
    }

    public void setBidIsExercise(Integer bidIsExercise) {
        this.bidIsExercise = bidIsExercise;
    }

    public Integer getOfrIsExercise() {
        return ofrIsExercise;
    }

    public void setOfrIsExercise(Integer ofrIsExercise) {
        this.ofrIsExercise = ofrIsExercise;
    }

    public BigDecimal getBidCleanPrice() {
        return bidCleanPrice;
    }

    public void setBidCleanPrice(BigDecimal bidCleanPrice) {
        this.bidCleanPrice = bidCleanPrice;
    }

    public BigDecimal getOfrCleanPrice() {
        return ofrCleanPrice;
    }

    public void setOfrCleanPrice(BigDecimal ofrCleanPrice) {
        this.ofrCleanPrice = ofrCleanPrice;
    }

    public BigDecimal getBidYtm() {
        return bidYtm;
    }

    public void setBidYtm(BigDecimal bidYtm) {
        this.bidYtm = bidYtm;
    }

    public BigDecimal getOfrYtm() {
        return ofrYtm;
    }

    public void setOfrYtm(BigDecimal ofrYtm) {
        this.ofrYtm = ofrYtm;
    }

    public BigDecimal getBidRebate() {
        return bidRebate;
    }

    public void setBidRebate(BigDecimal bidRebate) {
        this.bidRebate = bidRebate;
    }

    public BigDecimal getOfrRebate() {
        return ofrRebate;
    }

    public void setOfrRebate(BigDecimal ofrRebate) {
        this.ofrRebate = ofrRebate;
    }

    public Integer getRestSymbolDaysExchange() {
        return restSymbolDaysExchange;
    }

    public void setRestSymbolDaysExchange(Integer restSymbolDaysExchange) {
        this.restSymbolDaysExchange = restSymbolDaysExchange;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
