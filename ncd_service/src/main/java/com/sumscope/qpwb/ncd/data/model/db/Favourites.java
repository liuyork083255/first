package com.sumscope.qpwb.ncd.data.model.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Favourites {
    private long id;
    private String userId;
    private String issuerId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "issuer_id")
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
        Favourites that = (Favourites) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(issuerId, that.issuerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, issuerId);
    }
}
