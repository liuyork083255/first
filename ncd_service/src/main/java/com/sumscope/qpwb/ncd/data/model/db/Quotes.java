package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Quotes {
    private long id;
    private String rate;
    private String issuerId;
    private String firstLevelOrder;
    private String secondLevelOrder;
    private Date issuerDate;
    private String issuerName;
    private String brokerId;
    private String brokerName;
    private String fullName;
    private String m1DetailId;
    private BigDecimal m1Price;
    private String m1Volume;
    private BigDecimal m1Change;
    private String m1Mark;
    private Byte m1Available;
    private Time m1QuoteTime;
    private String m3DetailId;
    private BigDecimal m3Price;
    private String m3Volume;
    private BigDecimal m3Change;
    private String m3Mark;
    private Byte m3Available;
    private Time m3QuoteTime;
    private String m6DetailId;
    private BigDecimal m6Price;
    private String m6Volume;
    private BigDecimal m6Change;
    private String m6Mark;
    private Byte m6Available;
    private Time m6QuoteTime;
    private String m9DetailId;
    private BigDecimal m9Price;
    private String m9Volume;
    private BigDecimal m9Change;
    private String m9Mark;
    private Byte m9Available;
    private Time m9QuoteTime;
    private String y1DetailId;
    private BigDecimal y1Price;
    private String y1Volume;
    private BigDecimal y1Change;
    private String y1Mark;
    private Byte y1Available;
    private Time y1QuoteTime;
    private Byte recommend;
    private String issuerCode;
    private Byte outdated = 0;
    private Byte m1Recommend;
    private Byte m3Recommend;
    private Byte m6Recommend;
    private Byte m9Recommend;
    private Byte y1Recommend;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "issuer_id")
    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
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
    @Column(name = "issuer_date")
    public Date getIssuerDate() {
        return issuerDate;
    }

    public void setIssuerDate(Date issuerDate) {
        this.issuerDate = issuerDate;
    }

    @Basic
    @Column(name = "issuer_name")
    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
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
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "m1_detail_id")
    public String getM1DetailId() {
        return m1DetailId;
    }

    public void setM1DetailId(String m1DetailId) {
        this.m1DetailId = m1DetailId;
    }

    @Basic
    @Column(name = "m1_price")
    public BigDecimal getM1Price() {
        return m1Price;
    }

    public void setM1Price(BigDecimal m1Price) {
        this.m1Price = m1Price;
    }

    @Basic
    @Column(name = "m1_volume")
    public String getM1Volume() {
        return m1Volume;
    }

    public void setM1Volume(String m1Volume) {
        this.m1Volume = m1Volume;
    }

    @Basic
    @Column(name = "m1_change")
    public BigDecimal getM1Change() {
        return m1Change;
    }

    public void setM1Change(BigDecimal m1Change) {
        this.m1Change = m1Change;
    }

    @Basic
    @Column(name = "m1_mark")
    public String getM1Mark() {
        return m1Mark;
    }

    public void setM1Mark(String m1Mark) {
        this.m1Mark = m1Mark;
    }

    @Basic
    @Column(name = "m1_available")
    public Byte getM1Available() {
        return m1Available;
    }

    public void setM1Available(Byte m1Available) {
        this.m1Available = m1Available;
    }

    @Basic
    @Column(name = "m1_quote_time")
    public Time getM1QuoteTime() {
        return m1QuoteTime;
    }

    public void setM1QuoteTime(Time m1QuoteTime) {
        this.m1QuoteTime = m1QuoteTime;
    }

    @Basic
    @Column(name = "m3_detail_id")
    public String getM3DetailId() {
        return m3DetailId;
    }

    public void setM3DetailId(String m3DetailId) {
        this.m3DetailId = m3DetailId;
    }

    @Basic
    @Column(name = "m3_price")
    public BigDecimal getM3Price() {
        return m3Price;
    }

    public void setM3Price(BigDecimal m3Price) {
        this.m3Price = m3Price;
    }

    @Basic
    @Column(name = "m3_volume")
    public String getM3Volume() {
        return m3Volume;
    }

    public void setM3Volume(String m3Volume) {
        this.m3Volume = m3Volume;
    }

    @Basic
    @Column(name = "m3_change")
    public BigDecimal getM3Change() {
        return m3Change;
    }

    public void setM3Change(BigDecimal m3Change) {
        this.m3Change = m3Change;
    }

    @Basic
    @Column(name = "m3_mark")
    public String getM3Mark() {
        return m3Mark;
    }

    public void setM3Mark(String m3Mark) {
        this.m3Mark = m3Mark;
    }

    @Basic
    @Column(name = "m3_available")
    public Byte getM3Available() {
        return m3Available;
    }

    public void setM3Available(Byte m3Available) {
        this.m3Available = m3Available;
    }

    @Basic
    @Column(name = "m3_quote_time")
    public Time getM3QuoteTime() {
        return m3QuoteTime;
    }

    public void setM3QuoteTime(Time m3QuoteTime) {
        this.m3QuoteTime = m3QuoteTime;
    }

    @Basic
    @Column(name = "m6_detail_id")
    public String getM6DetailId() {
        return m6DetailId;
    }

    public void setM6DetailId(String m6DetailId) {
        this.m6DetailId = m6DetailId;
    }

    @Basic
    @Column(name = "m6_price")
    public BigDecimal getM6Price() {
        return m6Price;
    }

    public void setM6Price(BigDecimal m6Price) {
        this.m6Price = m6Price;
    }

    @Basic
    @Column(name = "m6_volume")
    public String getM6Volume() {
        return m6Volume;
    }

    public void setM6Volume(String m6Volume) {
        this.m6Volume = m6Volume;
    }

    @Basic
    @Column(name = "m6_change")
    public BigDecimal getM6Change() {
        return m6Change;
    }

    public void setM6Change(BigDecimal m6Change) {
        this.m6Change = m6Change;
    }

    @Basic
    @Column(name = "m6_mark")
    public String getM6Mark() {
        return m6Mark;
    }

    public void setM6Mark(String m6Mark) {
        this.m6Mark = m6Mark;
    }

    @Basic
    @Column(name = "m6_available")
    public Byte getM6Available() {
        return m6Available;
    }

    public void setM6Available(Byte m6Available) {
        this.m6Available = m6Available;
    }

    @Basic
    @Column(name = "m6_quote_time")
    public Time getM6QuoteTime() {
        return m6QuoteTime;
    }

    public void setM6QuoteTime(Time m6QuoteTime) {
        this.m6QuoteTime = m6QuoteTime;
    }

    @Basic
    @Column(name = "m9_detail_id")
    public String getM9DetailId() {
        return m9DetailId;
    }

    public void setM9DetailId(String m9DetailId) {
        this.m9DetailId = m9DetailId;
    }

    @Basic
    @Column(name = "m9_price")
    public BigDecimal getM9Price() {
        return m9Price;
    }

    public void setM9Price(BigDecimal m9Price) {
        this.m9Price = m9Price;
    }

    @Basic
    @Column(name = "m9_volume")
    public String getM9Volume() {
        return m9Volume;
    }

    public void setM9Volume(String m9Volume) {
        this.m9Volume = m9Volume;
    }

    @Basic
    @Column(name = "m9_change")
    public BigDecimal getM9Change() {
        return m9Change;
    }

    public void setM9Change(BigDecimal m9Change) {
        this.m9Change = m9Change;
    }

    @Basic
    @Column(name = "m9_mark")
    public String getM9Mark() {
        return m9Mark;
    }

    public void setM9Mark(String m9Mark) {
        this.m9Mark = m9Mark;
    }

    @Basic
    @Column(name = "m9_available")
    public Byte getM9Available() {
        return m9Available;
    }

    public void setM9Available(Byte m9Available) {
        this.m9Available = m9Available;
    }

    @Basic
    @Column(name = "m9_quote_time")
    public Time getM9QuoteTime() {
        return m9QuoteTime;
    }

    public void setM9QuoteTime(Time m9QuoteTime) {
        this.m9QuoteTime = m9QuoteTime;
    }

    @Basic
    @Column(name = "y1_detail_id")
    public String getY1DetailId() {
        return y1DetailId;
    }

    public void setY1DetailId(String y1DetailId) {
        this.y1DetailId = y1DetailId;
    }

    @Basic
    @Column(name = "y1_price")
    public BigDecimal getY1Price() {
        return y1Price;
    }

    public void setY1Price(BigDecimal y1Price) {
        this.y1Price = y1Price;
    }

    @Basic
    @Column(name = "y1_volume")
    public String getY1Volume() {
        return y1Volume;
    }

    public void setY1Volume(String y1Volume) {
        this.y1Volume = y1Volume;
    }

    @Basic
    @Column(name = "y1_change")
    public BigDecimal getY1Change() {
        return y1Change;
    }

    public void setY1Change(BigDecimal y1Change) {
        this.y1Change = y1Change;
    }

    @Basic
    @Column(name = "y1_mark")
    public String getY1Mark() {
        return y1Mark;
    }

    public void setY1Mark(String y1Mark) {
        this.y1Mark = y1Mark;
    }

    @Basic
    @Column(name = "y1_available")
    public Byte getY1Available() {
        return y1Available;
    }

    public void setY1Available(Byte y1Available) {
        this.y1Available = y1Available;
    }

    @Basic
    @Column(name = "y1_quote_time")
    public Time getY1QuoteTime() {
        return y1QuoteTime;
    }

    public void setY1QuoteTime(Time y1QuoteTime) {
        this.y1QuoteTime = y1QuoteTime;
    }

    @Basic
    @Column(name = "recommend")
    public Byte getRecommend() {
        return recommend;
    }

    public void setRecommend(Byte recommend) {
        this.recommend = recommend;
    }

    @Basic
    @Column(name = "issuer_code")
    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quotes quotes = (Quotes) o;
        return id == quotes.id &&
                Objects.equals(rate, quotes.rate) &&
                Objects.equals(issuerId, quotes.issuerId) &&
                Objects.equals(firstLevelOrder, quotes.firstLevelOrder) &&
                Objects.equals(secondLevelOrder, quotes.secondLevelOrder) &&
                Objects.equals(issuerDate, quotes.issuerDate) &&
                Objects.equals(issuerName, quotes.issuerName) &&
                Objects.equals(brokerId, quotes.brokerId) &&
                Objects.equals(brokerName, quotes.brokerName) &&
                Objects.equals(fullName, quotes.fullName) &&
                Objects.equals(m1DetailId, quotes.m1DetailId) &&
                Objects.equals(m1Price, quotes.m1Price) &&
                Objects.equals(m1Volume, quotes.m1Volume) &&
                Objects.equals(m1Change, quotes.m1Change) &&
                Objects.equals(m1Mark, quotes.m1Mark) &&
                Objects.equals(m1Available, quotes.m1Available) &&
                Objects.equals(m1QuoteTime, quotes.m1QuoteTime) &&
                Objects.equals(m3DetailId, quotes.m3DetailId) &&
                Objects.equals(m3Price, quotes.m3Price) &&
                Objects.equals(m3Volume, quotes.m3Volume) &&
                Objects.equals(m3Change, quotes.m3Change) &&
                Objects.equals(m3Mark, quotes.m3Mark) &&
                Objects.equals(m3Available, quotes.m3Available) &&
                Objects.equals(m3QuoteTime, quotes.m3QuoteTime) &&
                Objects.equals(m6DetailId, quotes.m6DetailId) &&
                Objects.equals(m6Price, quotes.m6Price) &&
                Objects.equals(m6Volume, quotes.m6Volume) &&
                Objects.equals(m6Change, quotes.m6Change) &&
                Objects.equals(m6Mark, quotes.m6Mark) &&
                Objects.equals(m6Available, quotes.m6Available) &&
                Objects.equals(m6QuoteTime, quotes.m6QuoteTime) &&
                Objects.equals(m9DetailId, quotes.m9DetailId) &&
                Objects.equals(m9Price, quotes.m9Price) &&
                Objects.equals(m9Volume, quotes.m9Volume) &&
                Objects.equals(m9Change, quotes.m9Change) &&
                Objects.equals(m9Mark, quotes.m9Mark) &&
                Objects.equals(m9Available, quotes.m9Available) &&
                Objects.equals(m9QuoteTime, quotes.m9QuoteTime) &&
                Objects.equals(y1DetailId, quotes.y1DetailId) &&
                Objects.equals(y1Price, quotes.y1Price) &&
                Objects.equals(y1Volume, quotes.y1Volume) &&
                Objects.equals(y1Change, quotes.y1Change) &&
                Objects.equals(y1Mark, quotes.y1Mark) &&
                Objects.equals(y1Available, quotes.y1Available) &&
                Objects.equals(y1QuoteTime, quotes.y1QuoteTime) &&
                Objects.equals(recommend, quotes.recommend) &&
                Objects.equals(issuerCode, quotes.issuerCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, rate, issuerId, firstLevelOrder, secondLevelOrder, issuerDate, issuerName, brokerId, brokerName, fullName, m1DetailId, m1Price, m1Volume, m1Change, m1Mark, m1Available, m1QuoteTime, m3DetailId, m3Price, m3Volume, m3Change, m3Mark, m3Available, m3QuoteTime, m6DetailId, m6Price, m6Volume, m6Change, m6Mark, m6Available, m6QuoteTime, m9DetailId, m9Price, m9Volume, m9Change, m9Mark, m9Available, m9QuoteTime, y1DetailId, y1Price, y1Volume, y1Change, y1Mark, y1Available, y1QuoteTime, recommend, issuerCode);
    }

    @Basic
    @Column(name = "outdated")
    public Byte getOutdated() {
        return outdated;
    }

    public void setOutdated(Byte outdated) {
        this.outdated = outdated;
    }

    @Basic
    @Column(name = "m1_recommend")
    public Byte getM1Recommend() {
        return m1Recommend;
    }

    public void setM1Recommend(Byte m1Recommend) {
        this.m1Recommend = m1Recommend;
    }

    @Basic
    @Column(name = "m3_recommend")
    public Byte getM3Recommend() {
        return m3Recommend;
    }

    public void setM3Recommend(Byte m3Recommend) {
        this.m3Recommend = m3Recommend;
    }

    @Basic
    @Column(name = "m6_recommend")
    public Byte getM6Recommend() {
        return m6Recommend;
    }

    public void setM6Recommend(Byte m6Recommend) {
        this.m6Recommend = m6Recommend;
    }

    @Basic
    @Column(name = "m9_recommend")
    public Byte getM9Recommend() {
        return m9Recommend;
    }

    public void setM9Recommend(Byte m9Recommend) {
        this.m9Recommend = m9Recommend;
    }

    @Basic
    @Column(name = "y1_recommend")
    public Byte getY1Recommend() {
        return y1Recommend;
    }

    public void setY1Recommend(Byte y1Recommend) {
        this.y1Recommend = y1Recommend;
    }
}
