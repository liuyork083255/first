package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Long quoteDetailId;
    private String userId;
    private String brokerId;
    private String brokerName;
    private String issuerName;
    private Timestamp createTime;
    private String issuerId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quote_detail_id", nullable = true)
    public Long getQuoteDetailId() {
        return quoteDetailId;
    }

    public void setQuoteDetailId(Long quoteDetailId) {
        this.quoteDetailId = quoteDetailId;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "broker_id", nullable = true, length = 32)
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "broker_name", nullable = true, length = 32)
    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    @Basic
    @Column(name = "issuer_name", nullable = true, length = 60)
    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "issuer_id", nullable = true, length = 60)
    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id &&
                Objects.equals(quoteDetailId, orders.quoteDetailId) &&
                Objects.equals(userId, orders.userId) &&
                Objects.equals(brokerId, orders.brokerId) &&
                Objects.equals(brokerName, orders.brokerName) &&
                Objects.equals(issuerName, orders.issuerName) &&
                Objects.equals(createTime, orders.createTime) &&
                Objects.equals(issuerId, orders.issuerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, quoteDetailId, userId, brokerId, brokerName, issuerName, createTime, issuerId);
    }
}
