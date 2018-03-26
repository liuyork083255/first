package com.sumscope.cdh.webbond.generated;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by chengzhang.wang on 2017/4/10.
 */
public class WebbondTradePK implements Serializable
{
    private String bondKey;
    private String listedMarket;
    private String tradeId;

    @Column(name = "bondKey", nullable = false, length = 100)
    @Id
    public String getBondKey()
    {
        return bondKey;
    }

    public void setBondKey(String bondKey)
    {
        this.bondKey = bondKey;
    }

    @Column(name = "listedMarket", nullable = false, length = 10)
    @Id
    public String getListedMarket()
    {
        return listedMarket;
    }

    public void setListedMarket(String listedMarket)
    {
        this.listedMarket = listedMarket;
    }

    @Column(name = "tradeId", nullable = false, length = 200)
    @Id
    public String getTradeId()
    {
        return tradeId;
    }

    public void setTradeId(String tradeId)
    {
        this.tradeId = tradeId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebbondTradePK that = (WebbondTradePK) o;

        if (bondKey != null ? !bondKey.equals(that.bondKey) : that.bondKey != null) return false;
        if (listedMarket != null ? !listedMarket.equals(that.listedMarket) : that.listedMarket != null) return false;
        if (tradeId != null ? !tradeId.equals(that.tradeId) : that.tradeId != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = bondKey != null ? bondKey.hashCode() : 0;
        result = 31 * result + (listedMarket != null ? listedMarket.hashCode() : 0);
        result = 31 * result + (tradeId != null ? tradeId.hashCode() : 0);
        return result;
    }
}
