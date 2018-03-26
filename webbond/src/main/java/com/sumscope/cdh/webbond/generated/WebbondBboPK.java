package com.sumscope.cdh.webbond.generated;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by chengzhang.wang on 2017/7/26.
 */
public class WebbondBboPK implements Serializable {
    private String bondKey;
    private String listedMarket;
    private String brokerId;
    private String updateDateTime;

    @Column(name = "bondKey", nullable = false, length = 100)
    @Id
    public String getBondKey() {
        return bondKey;
    }

    public void setBondKey(String bondKey) {
        this.bondKey = bondKey;
    }

    @Column(name = "listedMarket", nullable = false, length = 10)
    @Id
    public String getListedMarket() {
        return listedMarket;
    }

    public void setListedMarket(String listedMarket) {
        this.listedMarket = listedMarket;
    }

    @Column(name = "brokerId", nullable = false, length = 10)
    @Id
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    @Column(name = "updateDateTime", nullable = false, length = 100)
    @Id
    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebbondBboPK that = (WebbondBboPK) o;

        if (bondKey != null ? !bondKey.equals(that.bondKey) : that.bondKey != null) return false;
        if (listedMarket != null ? !listedMarket.equals(that.listedMarket) : that.listedMarket != null) return false;
        if (brokerId != null ? !brokerId.equals(that.brokerId) : that.brokerId != null) return false;
        if (updateDateTime != null ? !updateDateTime.equals(that.updateDateTime) : that.updateDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bondKey != null ? bondKey.hashCode() : 0;
        result = 31 * result + (listedMarket != null ? listedMarket.hashCode() : 0);
        result = 31 * result + (brokerId != null ? brokerId.hashCode() : 0);
        result = 31 * result + (updateDateTime != null ? updateDateTime.hashCode() : 0);
        return result;
    }
}
