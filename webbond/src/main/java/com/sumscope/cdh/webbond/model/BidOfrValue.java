package com.sumscope.cdh.webbond.model;

/**
 * Created by chengzhang.wang on 2017/3/17.
 */
public class BidOfrValue
{
    private PriceRange bidRange;
    private PriceRange ofrRange;
    private PriceRange bidMinusCDC;
    private PriceRange ofrMinusCDC;

    public PriceRange getBidRange()
    {
        return bidRange;
    }

    public void setBidRange(PriceRange bidRange)
    {
        this.bidRange = bidRange;
    }

    public PriceRange getOfrRange()
    {
        return ofrRange;
    }

    public void setOfrRange(PriceRange ofrRange)
    {
        this.ofrRange = ofrRange;
    }

    public PriceRange getBidMinusCDC()
    {
        return bidMinusCDC;
    }

    public void setBidMinusCDC(PriceRange bidMinusCDC)
    {
        this.bidMinusCDC = bidMinusCDC;
    }

    public PriceRange getOfrMinusCDC()
    {
        return ofrMinusCDC;
    }

    public void setOfrMinusCDC(PriceRange ofrMinusCDC)
    {
        this.ofrMinusCDC = ofrMinusCDC;
    }
}
