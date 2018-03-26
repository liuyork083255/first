package com.sumscope.qpwb.ncd.utils;

import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.RateEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BussinessUtilsTest extends BaseApplicationTest {

    @Test
    public void matchRate() {
        List<String> rates = new ArrayList<>();
        rates.add(NcdConstants.ALL);
        Assert.assertTrue(BusinessUtils.matchRate(rates, RateEnum.AA_RATE.getRate()));
        Assert.assertTrue(BusinessUtils.matchRate(rates, "BBB"));

        rates.clear();
        rates.add(RateEnum.AA_RATE.getRate());
        rates.add(RateEnum.AAA_RATE.getRate());
        Assert.assertTrue(BusinessUtils.matchRate(rates, RateEnum.AA_RATE.getRate()));
        Assert.assertTrue(BusinessUtils.matchRate(rates, RateEnum.AAA_RATE.getRate()));
        Assert.assertFalse(BusinessUtils.matchRate(rates, "BBB"));

        rates.add(NcdConstants.OTHER);
        Assert.assertTrue(BusinessUtils.matchRate(rates, "BBB"));

        rates.clear();
        rates.add(NcdConstants.OTHER);
        Assert.assertTrue(BusinessUtils.matchRate(rates, "BBB"));
        Assert.assertFalse(BusinessUtils.matchRate(rates, RateEnum.AAA_RATE.getRate()));


    }
}
