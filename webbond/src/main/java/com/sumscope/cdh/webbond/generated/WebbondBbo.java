package com.sumscope.cdh.webbond.generated;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by chengzhang.wang on 2017/7/26.
 */
@Entity
@Table(name = "webbond_bbo", catalog = "")
@IdClass(WebbondBboPK.class)
public class WebbondBbo implements Serializable
{
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
    private String updateDateTime;
    private String createDateTime;
    private String pinyinFull;
    private String ratingInstitutionPinyin;
    private String expectionValue;
    private Double bidVolumeSortValue;
    private Double ofrVolumeSortValue;
    private Integer expectionSortValue;
    private String ofrQuoteIds;
    private String bidQuoteIds;
    private Byte bidRelationFlag;
    private Byte ofrRelationFlag;
    private Integer restSymbolDays;
    private Byte bidIsExercise;
    private Byte ofrIsExercise;
    private BigDecimal bidCleanPrice;
    private BigDecimal ofrCleanPrice;
    private BigDecimal bidYtm;
    private BigDecimal ofrYtm;
    private BigDecimal bidRebate;
    private BigDecimal ofrRebate;
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
    @Column(name = "brokerId", nullable = false, length = 10)
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
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
    @Column(name = "bondCode", nullable = true, length = 100)
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
    @Column(name = "latestTransaction", nullable = true, length = 100)
    public String getLatestTransaction() {
        return latestTransaction;
    }

    public void setLatestTransaction(String latestTransaction) {
        this.latestTransaction = latestTransaction;
    }

    @Basic
    @Column(name = "bidVolume", nullable = true, length = 200)
    public String getBidVolume() {
        return bidVolume;
    }

    public void setBidVolume(String bidVolume) {
        this.bidVolume = bidVolume;
    }

    @Basic
    @Column(name = "bid", nullable = true, length = 100)
    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Basic
    @Column(name = "bidBarginFlag", nullable = true)
    public Integer getBidBarginFlag() {
        return bidBarginFlag;
    }

    public void setBidBarginFlag(Integer bidBarginFlag) {
        this.bidBarginFlag = bidBarginFlag;
    }

    @Basic
    @Column(name = "ofr", nullable = true, length = 100)
    public String getOfr() {
        return ofr;
    }

    public void setOfr(String ofr) {
        this.ofr = ofr;
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
    @Column(name = "bidSubCdc", nullable = true, length = 100)
    public String getBidSubCdc() {
        return bidSubCdc;
    }

    public void setBidSubCdc(String bidSubCdc) {
        this.bidSubCdc = bidSubCdc;
    }

    @Basic
    @Column(name = "cdcSubOfr", nullable = true, length = 100)
    public String getCdcSubOfr() {
        return cdcSubOfr;
    }

    public void setCdcSubOfr(String cdcSubOfr) {
        this.cdcSubOfr = cdcSubOfr;
    }

    @Basic
    @Column(name = "bidSubCsi", nullable = true, length = 100)
    public String getBidSubCsi() {
        return bidSubCsi;
    }

    public void setBidSubCsi(String bidSubCsi) {
        this.bidSubCsi = bidSubCsi;
    }

    @Basic
    @Column(name = "csiSubOfr", nullable = true, length = 100)
    public String getCsiSubOfr() {
        return csiSubOfr;
    }

    public void setCsiSubOfr(String csiSubOfr) {
        this.csiSubOfr = csiSubOfr;
    }

    @Basic
    @Column(name = "brokerName", nullable = true, length = 100)
    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
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
    @Column(name = "duration", nullable = true, length = 100)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "ofrBarginFlag", nullable = true)
    public Integer getOfrBarginFlag() {
        return ofrBarginFlag;
    }

    public void setOfrBarginFlag(Integer ofrBarginFlag) {
        this.ofrBarginFlag = ofrBarginFlag;
    }

    @Basic
    @Column(name = "ofrVolume", nullable = true, length = 200)
    public String getOfrVolume() {
        return ofrVolume;
    }

    public void setOfrVolume(String ofrVolume) {
        this.ofrVolume = ofrVolume;
    }

    @Basic
    @Column(name = "bidPriceComment", nullable = true, length = 200)
    public String getBidPriceComment() {
        return bidPriceComment;
    }

    public void setBidPriceComment(String bidPriceComment) {
        this.bidPriceComment = bidPriceComment;
    }

    @Basic
    @Column(name = "ofrPriceComment", nullable = true, length = 200)
    public String getOfrPriceComment() {
        return ofrPriceComment;
    }

    public void setOfrPriceComment(String ofrPriceComment) {
        this.ofrPriceComment = ofrPriceComment;
    }

    @Basic
    @Column(name = "RemaintTimeValue", nullable = true)
    public Integer getRemaintTimeValue() {
        return remaintTimeValue;
    }

    public void setRemaintTimeValue(Integer remaintTimeValue) {
        this.remaintTimeValue = remaintTimeValue;
    }

    @Basic
    @Column(name = "latestTransactionValue", nullable = true, precision = 6)
    public Double getLatestTransactionValue() {
        return latestTransactionValue;
    }

    public void setLatestTransactionValue(Double latestTransactionValue) {
        this.latestTransactionValue = latestTransactionValue;
    }

    @Basic
    @Column(name = "bidVolumeValue", nullable = true, precision = 6)
    public Double getBidVolumeValue() {
        return bidVolumeValue;
    }

    public void setBidVolumeValue(Double bidVolumeValue) {
        this.bidVolumeValue = bidVolumeValue;
    }

    @Basic
    @Column(name = "ofrVolumeValue", nullable = true, precision = 6)
    public Double getOfrVolumeValue() {
        return ofrVolumeValue;
    }

    public void setOfrVolumeValue(Double ofrVolumeValue) {
        this.ofrVolumeValue = ofrVolumeValue;
    }

    @Basic
    @Column(name = "bidValue", nullable = true, precision = 6)
    public Double getBidValue() {
        return bidValue;
    }

    public void setBidValue(Double bidValue) {
        this.bidValue = bidValue;
    }

    @Basic
    @Column(name = "ofrValue", nullable = true, precision = 6)
    public Double getOfrValue() {
        return ofrValue;
    }

    public void setOfrValue(Double ofrValue) {
        this.ofrValue = ofrValue;
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
    @Column(name = "bidSubCdcValue", nullable = true, precision = 6)
    public Double getBidSubCdcValue() {
        return bidSubCdcValue;
    }

    public void setBidSubCdcValue(Double bidSubCdcValue) {
        this.bidSubCdcValue = bidSubCdcValue;
    }

    @Basic
    @Column(name = "bidSubCsiValue", nullable = true, precision = 6)
    public Double getBidSubCsiValue() {
        return bidSubCsiValue;
    }

    public void setBidSubCsiValue(Double bidSubCsiValue) {
        this.bidSubCsiValue = bidSubCsiValue;
    }

    @Basic
    @Column(name = "cdcSubOfrValue", nullable = true, precision = 6)
    public Double getCdcSubOfrValue() {
        return cdcSubOfrValue;
    }

    public void setCdcSubOfrValue(Double cdcSubOfrValue) {
        this.cdcSubOfrValue = cdcSubOfrValue;
    }

    @Basic
    @Column(name = "csiSubOfrValue", nullable = true, precision = 6)
    public Double getCsiSubOfrValue() {
        return csiSubOfrValue;
    }

    public void setCsiSubOfrValue(Double csiSubOfrValue) {
        this.csiSubOfrValue = csiSubOfrValue;
    }

    @Id
    @Column(name = "updateDateTime", nullable = false, length = 100)
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
    @Column(name = "bidVolumeSortValue", nullable = true, precision = 6)
    public Double getBidVolumeSortValue() {
        return bidVolumeSortValue;
    }

    public void setBidVolumeSortValue(Double bidVolumeSortValue) {
        this.bidVolumeSortValue = bidVolumeSortValue;
    }

    @Basic
    @Column(name = "ofrVolumeSortValue", nullable = true, precision = 6)
    public Double getOfrVolumeSortValue() {
        return ofrVolumeSortValue;
    }

    public void setOfrVolumeSortValue(Double ofrVolumeSortValue) {
        this.ofrVolumeSortValue = ofrVolumeSortValue;
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
    @Column(name = "ofrQuoteIds", nullable = true, length = 4096)
    public String getOfrQuoteIds() {
        return ofrQuoteIds;
    }

    public void setOfrQuoteIds(String ofrQuoteIds) {
        this.ofrQuoteIds = ofrQuoteIds;
    }

    @Basic
    @Column(name = "bidQuoteIds", nullable = true, length = 4096)
    public String getBidQuoteIds() {
        return bidQuoteIds;
    }

    public void setBidQuoteIds(String bidQuoteIds) {
        this.bidQuoteIds = bidQuoteIds;
    }

    @Basic
    @Column(name = "bidRelationFlag", nullable = true)
    public Byte getBidRelationFlag() {
        return bidRelationFlag;
    }

    public void setBidRelationFlag(Byte bidRelationFlag) {
        this.bidRelationFlag = bidRelationFlag;
    }

    @Basic
    @Column(name = "ofrRelationFlag", nullable = true)
    public Byte getOfrRelationFlag() {
        return ofrRelationFlag;
    }

    public void setOfrRelationFlag(Byte ofrRelationFlag) {
        this.ofrRelationFlag = ofrRelationFlag;
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
    @Column(name = "bidIsExercise", nullable = true)
    public Byte getBidIsExercise() {
        return bidIsExercise;
    }

    public void setBidIsExercise(Byte bidIsExercise) {
        this.bidIsExercise = bidIsExercise;
    }

    @Basic
    @Column(name = "ofrIsExercise", nullable = true)
    public Byte getOfrIsExercise() {
        return ofrIsExercise;
    }

    public void setOfrIsExercise(Byte ofrIsExercise) {
        this.ofrIsExercise = ofrIsExercise;
    }

    @Basic
    @Column(name = "bidCleanPrice", nullable = true, precision = 4)
    public BigDecimal getBidCleanPrice() {
        return bidCleanPrice;
    }

    public void setBidCleanPrice(BigDecimal bidCleanPrice) {
        this.bidCleanPrice = bidCleanPrice;
    }

    @Basic
    @Column(name = "ofrCleanPrice", nullable = true, precision = 4)
    public BigDecimal getOfrCleanPrice() {
        return ofrCleanPrice;
    }

    public void setOfrCleanPrice(BigDecimal ofrCleanPrice) {
        this.ofrCleanPrice = ofrCleanPrice;
    }

    @Basic
    @Column(name = "bidYtm", nullable = true, precision = 4)
    public BigDecimal getBidYtm() {
        return bidYtm;
    }

    public void setBidYtm(BigDecimal bidYtm) {
        this.bidYtm = bidYtm;
    }

    @Basic
    @Column(name = "ofrYtm", nullable = true, precision = 4)
    public BigDecimal getOfrYtm() {
        return ofrYtm;
    }

    public void setOfrYtm(BigDecimal ofrYtm) {
        this.ofrYtm = ofrYtm;
    }

    @Basic
    @Column(name = "bidRebate", nullable = true, precision = 4)
    public BigDecimal getBidRebate() {
        return bidRebate;
    }

    public void setBidRebate(BigDecimal bidRebate) {
        this.bidRebate = bidRebate;
    }

    @Basic
    @Column(name = "ofrRebate", nullable = true, precision = 4)
    public BigDecimal getOfrRebate() {
        return ofrRebate;
    }

    public void setOfrRebate(BigDecimal ofrRebate) {
        this.ofrRebate = ofrRebate;
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

        WebbondBbo that = (WebbondBbo) o;

        if (bondKey != null ? !bondKey.equals(that.bondKey) : that.bondKey != null) return false;
        if (listedMarket != null ? !listedMarket.equals(that.listedMarket) : that.listedMarket != null) return false;
        if (brokerId != null ? !brokerId.equals(that.brokerId) : that.brokerId != null) return false;
        if (remaintTime != null ? !remaintTime.equals(that.remaintTime) : that.remaintTime != null) return false;
        if (bondCode != null ? !bondCode.equals(that.bondCode) : that.bondCode != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (latestTransaction != null ? !latestTransaction.equals(that.latestTransaction) : that.latestTransaction != null)
            return false;
        if (bidVolume != null ? !bidVolume.equals(that.bidVolume) : that.bidVolume != null) return false;
        if (bid != null ? !bid.equals(that.bid) : that.bid != null) return false;
        if (bidBarginFlag != null ? !bidBarginFlag.equals(that.bidBarginFlag) : that.bidBarginFlag != null)
            return false;
        if (ofr != null ? !ofr.equals(that.ofr) : that.ofr != null) return false;
        if (cdcValuation != null ? !cdcValuation.equals(that.cdcValuation) : that.cdcValuation != null) return false;
        if (csiValuation != null ? !csiValuation.equals(that.csiValuation) : that.csiValuation != null) return false;
        if (issuerBondRating != null ? !issuerBondRating.equals(that.issuerBondRating) : that.issuerBondRating != null)
            return false;
        if (expection != null ? !expection.equals(that.expection) : that.expection != null) return false;
        if (ratingInstitutionName != null ? !ratingInstitutionName.equals(that.ratingInstitutionName) : that.ratingInstitutionName != null)
            return false;
        if (bidSubCdc != null ? !bidSubCdc.equals(that.bidSubCdc) : that.bidSubCdc != null) return false;
        if (cdcSubOfr != null ? !cdcSubOfr.equals(that.cdcSubOfr) : that.cdcSubOfr != null) return false;
        if (bidSubCsi != null ? !bidSubCsi.equals(that.bidSubCsi) : that.bidSubCsi != null) return false;
        if (csiSubOfr != null ? !csiSubOfr.equals(that.csiSubOfr) : that.csiSubOfr != null) return false;
        if (brokerName != null ? !brokerName.equals(that.brokerName) : that.brokerName != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (ofrBarginFlag != null ? !ofrBarginFlag.equals(that.ofrBarginFlag) : that.ofrBarginFlag != null)
            return false;
        if (ofrVolume != null ? !ofrVolume.equals(that.ofrVolume) : that.ofrVolume != null) return false;
        if (bidPriceComment != null ? !bidPriceComment.equals(that.bidPriceComment) : that.bidPriceComment != null)
            return false;
        if (ofrPriceComment != null ? !ofrPriceComment.equals(that.ofrPriceComment) : that.ofrPriceComment != null)
            return false;
        if (remaintTimeValue != null ? !remaintTimeValue.equals(that.remaintTimeValue) : that.remaintTimeValue != null)
            return false;
        if (latestTransactionValue != null ? !latestTransactionValue.equals(that.latestTransactionValue) : that.latestTransactionValue != null)
            return false;
        if (bidVolumeValue != null ? !bidVolumeValue.equals(that.bidVolumeValue) : that.bidVolumeValue != null)
            return false;
        if (ofrVolumeValue != null ? !ofrVolumeValue.equals(that.ofrVolumeValue) : that.ofrVolumeValue != null)
            return false;
        if (bidValue != null ? !bidValue.equals(that.bidValue) : that.bidValue != null) return false;
        if (ofrValue != null ? !ofrValue.equals(that.ofrValue) : that.ofrValue != null) return false;
        if (cdcValuationValue != null ? !cdcValuationValue.equals(that.cdcValuationValue) : that.cdcValuationValue != null)
            return false;
        if (csiValuationValue != null ? !csiValuationValue.equals(that.csiValuationValue) : that.csiValuationValue != null)
            return false;
        if (bidSubCdcValue != null ? !bidSubCdcValue.equals(that.bidSubCdcValue) : that.bidSubCdcValue != null)
            return false;
        if (bidSubCsiValue != null ? !bidSubCsiValue.equals(that.bidSubCsiValue) : that.bidSubCsiValue != null)
            return false;
        if (cdcSubOfrValue != null ? !cdcSubOfrValue.equals(that.cdcSubOfrValue) : that.cdcSubOfrValue != null)
            return false;
        if (csiSubOfrValue != null ? !csiSubOfrValue.equals(that.csiSubOfrValue) : that.csiSubOfrValue != null)
            return false;
        if (updateDateTime != null ? !updateDateTime.equals(that.updateDateTime) : that.updateDateTime != null)
            return false;
        if (createDateTime != null ? !createDateTime.equals(that.createDateTime) : that.createDateTime != null)
            return false;
        if (pinyinFull != null ? !pinyinFull.equals(that.pinyinFull) : that.pinyinFull != null) return false;
        if (ratingInstitutionPinyin != null ? !ratingInstitutionPinyin.equals(that.ratingInstitutionPinyin) : that.ratingInstitutionPinyin != null)
            return false;
        if (expectionValue != null ? !expectionValue.equals(that.expectionValue) : that.expectionValue != null)
            return false;
        if (bidVolumeSortValue != null ? !bidVolumeSortValue.equals(that.bidVolumeSortValue) : that.bidVolumeSortValue != null)
            return false;
        if (ofrVolumeSortValue != null ? !ofrVolumeSortValue.equals(that.ofrVolumeSortValue) : that.ofrVolumeSortValue != null)
            return false;
        if (expectionSortValue != null ? !expectionSortValue.equals(that.expectionSortValue) : that.expectionSortValue != null)
            return false;
        if (ofrQuoteIds != null ? !ofrQuoteIds.equals(that.ofrQuoteIds) : that.ofrQuoteIds != null) return false;
        if (bidQuoteIds != null ? !bidQuoteIds.equals(that.bidQuoteIds) : that.bidQuoteIds != null) return false;
        if (bidRelationFlag != null ? !bidRelationFlag.equals(that.bidRelationFlag) : that.bidRelationFlag != null)
            return false;
        if (ofrRelationFlag != null ? !ofrRelationFlag.equals(that.ofrRelationFlag) : that.ofrRelationFlag != null)
            return false;
        if (restSymbolDays != null ? !restSymbolDays.equals(that.restSymbolDays) : that.restSymbolDays != null)
            return false;
        if (bidIsExercise != null ? !bidIsExercise.equals(that.bidIsExercise) : that.bidIsExercise != null)
            return false;
        if (ofrIsExercise != null ? !ofrIsExercise.equals(that.ofrIsExercise) : that.ofrIsExercise != null)
            return false;
        if (bidCleanPrice != null ? !bidCleanPrice.equals(that.bidCleanPrice) : that.bidCleanPrice != null)
            return false;
        if (ofrCleanPrice != null ? !ofrCleanPrice.equals(that.ofrCleanPrice) : that.ofrCleanPrice != null)
            return false;
        if (bidYtm != null ? !bidYtm.equals(that.bidYtm) : that.bidYtm != null) return false;
        if (ofrYtm != null ? !ofrYtm.equals(that.ofrYtm) : that.ofrYtm != null) return false;
        if (bidRebate != null ? !bidRebate.equals(that.bidRebate) : that.bidRebate != null) return false;
        if (ofrRebate != null ? !ofrRebate.equals(that.ofrRebate) : that.ofrRebate != null) return false;
        if (restSymbolDaysExchange != null ? !restSymbolDaysExchange.equals(that.restSymbolDaysExchange) : that.restSymbolDaysExchange != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bondKey != null ? bondKey.hashCode() : 0;
        result = 31 * result + (listedMarket != null ? listedMarket.hashCode() : 0);
        result = 31 * result + (brokerId != null ? brokerId.hashCode() : 0);
        result = 31 * result + (remaintTime != null ? remaintTime.hashCode() : 0);
        result = 31 * result + (bondCode != null ? bondCode.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (latestTransaction != null ? latestTransaction.hashCode() : 0);
        result = 31 * result + (bidVolume != null ? bidVolume.hashCode() : 0);
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (bidBarginFlag != null ? bidBarginFlag.hashCode() : 0);
        result = 31 * result + (ofr != null ? ofr.hashCode() : 0);
        result = 31 * result + (cdcValuation != null ? cdcValuation.hashCode() : 0);
        result = 31 * result + (csiValuation != null ? csiValuation.hashCode() : 0);
        result = 31 * result + (issuerBondRating != null ? issuerBondRating.hashCode() : 0);
        result = 31 * result + (expection != null ? expection.hashCode() : 0);
        result = 31 * result + (ratingInstitutionName != null ? ratingInstitutionName.hashCode() : 0);
        result = 31 * result + (bidSubCdc != null ? bidSubCdc.hashCode() : 0);
        result = 31 * result + (cdcSubOfr != null ? cdcSubOfr.hashCode() : 0);
        result = 31 * result + (bidSubCsi != null ? bidSubCsi.hashCode() : 0);
        result = 31 * result + (csiSubOfr != null ? csiSubOfr.hashCode() : 0);
        result = 31 * result + (brokerName != null ? brokerName.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (ofrBarginFlag != null ? ofrBarginFlag.hashCode() : 0);
        result = 31 * result + (ofrVolume != null ? ofrVolume.hashCode() : 0);
        result = 31 * result + (bidPriceComment != null ? bidPriceComment.hashCode() : 0);
        result = 31 * result + (ofrPriceComment != null ? ofrPriceComment.hashCode() : 0);
        result = 31 * result + (remaintTimeValue != null ? remaintTimeValue.hashCode() : 0);
        result = 31 * result + (latestTransactionValue != null ? latestTransactionValue.hashCode() : 0);
        result = 31 * result + (bidVolumeValue != null ? bidVolumeValue.hashCode() : 0);
        result = 31 * result + (ofrVolumeValue != null ? ofrVolumeValue.hashCode() : 0);
        result = 31 * result + (bidValue != null ? bidValue.hashCode() : 0);
        result = 31 * result + (ofrValue != null ? ofrValue.hashCode() : 0);
        result = 31 * result + (cdcValuationValue != null ? cdcValuationValue.hashCode() : 0);
        result = 31 * result + (csiValuationValue != null ? csiValuationValue.hashCode() : 0);
        result = 31 * result + (bidSubCdcValue != null ? bidSubCdcValue.hashCode() : 0);
        result = 31 * result + (bidSubCsiValue != null ? bidSubCsiValue.hashCode() : 0);
        result = 31 * result + (cdcSubOfrValue != null ? cdcSubOfrValue.hashCode() : 0);
        result = 31 * result + (csiSubOfrValue != null ? csiSubOfrValue.hashCode() : 0);
        result = 31 * result + (updateDateTime != null ? updateDateTime.hashCode() : 0);
        result = 31 * result + (createDateTime != null ? createDateTime.hashCode() : 0);
        result = 31 * result + (pinyinFull != null ? pinyinFull.hashCode() : 0);
        result = 31 * result + (ratingInstitutionPinyin != null ? ratingInstitutionPinyin.hashCode() : 0);
        result = 31 * result + (expectionValue != null ? expectionValue.hashCode() : 0);
        result = 31 * result + (bidVolumeSortValue != null ? bidVolumeSortValue.hashCode() : 0);
        result = 31 * result + (ofrVolumeSortValue != null ? ofrVolumeSortValue.hashCode() : 0);
        result = 31 * result + (expectionSortValue != null ? expectionSortValue.hashCode() : 0);
        result = 31 * result + (ofrQuoteIds != null ? ofrQuoteIds.hashCode() : 0);
        result = 31 * result + (bidQuoteIds != null ? bidQuoteIds.hashCode() : 0);
        result = 31 * result + (bidRelationFlag != null ? bidRelationFlag.hashCode() : 0);
        result = 31 * result + (ofrRelationFlag != null ? ofrRelationFlag.hashCode() : 0);
        result = 31 * result + (restSymbolDays != null ? restSymbolDays.hashCode() : 0);
        result = 31 * result + (bidIsExercise != null ? bidIsExercise.hashCode() : 0);
        result = 31 * result + (ofrIsExercise != null ? ofrIsExercise.hashCode() : 0);
        result = 31 * result + (bidCleanPrice != null ? bidCleanPrice.hashCode() : 0);
        result = 31 * result + (ofrCleanPrice != null ? ofrCleanPrice.hashCode() : 0);
        result = 31 * result + (bidYtm != null ? bidYtm.hashCode() : 0);
        result = 31 * result + (ofrYtm != null ? ofrYtm.hashCode() : 0);
        result = 31 * result + (bidRebate != null ? bidRebate.hashCode() : 0);
        result = 31 * result + (ofrRebate != null ? ofrRebate.hashCode() : 0);
        result = 31 * result + (restSymbolDaysExchange != null ? restSymbolDaysExchange.hashCode() : 0);
        return result;
    }
}
