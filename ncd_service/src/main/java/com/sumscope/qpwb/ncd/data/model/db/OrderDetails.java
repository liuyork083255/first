package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class OrderDetails {
    private long id;
    private String issuerName;
    private String userId;
    private String terminal;
    private String issuePrice;
    private Timestamp createTime;
    private boolean priceType;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    public String getIssuePrice() {
        return issuePrice;
    }

    public void setIssuePrice(String issuePrice) {
        this.issuePrice = issuePrice;
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
    @Column(name = "price_type")
    public Boolean getPriceType() {
        return priceType;
    }

    public void setPriceType(Boolean priceType) {
        this.priceType = priceType;
    }
}
