package com.sumscope.wf.bond.monitor.data.access.cdh.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.List;

public class QuoteHistoryStatsUtilTest {
    private QuoteHistoryStatsUtil quoteHistoryStatsUtil = new QuoteHistoryStatsUtil();
    
    @Test
    public void getIntervalList(){

        LocalTime start = LocalTime.parse("08:00:00");
        LocalTime end = LocalTime.parse("18:00:00");
        int intervalMin = 5;

        List<String> splitList = quoteHistoryStatsUtil.getSplitList(start, end, intervalMin);

        System.out.println(splitList);

    }
    
    /**
     * split by 2
     */
    @Test
    public void testInterval2(){
        int interval = 2;
        LocalTime parse0 = LocalTime.parse("08:00:08");
        LocalTime parse1 = LocalTime.parse("13:38:01");
        LocalTime parse2 = LocalTime.parse("17:09:50");

        String t0 = quoteHistoryStatsUtil.getHourMinuteKey(parse0.getHour(), parse0.getMinute(), 0, interval);
        String t1 = quoteHistoryStatsUtil.getHourMinuteKey(parse1.getHour(), parse1.getMinute(), 1, interval);
        String t2 = quoteHistoryStatsUtil.getHourMinuteKey(parse2.getHour(), parse2.getMinute(), 2, interval);

        Assert.isTrue(StringUtils.equals(t0,"08:00_0"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t1,"13:38_1"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t2,"17:08_2"),"split error by "+interval+".");
    }

    /**
     * split by 5
     */
    @Test
    public void testInterval5(){
        int interval = 5;
        LocalTime parse0 = LocalTime.parse("09:00:08");
        LocalTime parse1 = LocalTime.parse("13:07:01");
        LocalTime parse2 = LocalTime.parse("17:59:50");

        String t0 = quoteHistoryStatsUtil.getHourMinuteKey(parse0.getHour(), parse0.getMinute(), 0, interval);
        String t1 = quoteHistoryStatsUtil.getHourMinuteKey(parse1.getHour(), parse1.getMinute(), 1, interval);
        String t2 = quoteHistoryStatsUtil.getHourMinuteKey(parse2.getHour(), parse2.getMinute(), 2, interval);

        Assert.isTrue(StringUtils.equals(t0,"09:00_0"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t1,"13:05_1"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t2,"17:55_2"),"split error by "+interval+".");
    }

    /**
     * split by 10
     */
    @Test
    public void testInterval10(){
        int interval = 10;
        LocalTime parse0 = LocalTime.parse("10:49:08");
        LocalTime parse1 = LocalTime.parse("15:07:01");
        LocalTime parse2 = LocalTime.parse("17:55:50");

        String t0 = quoteHistoryStatsUtil.getHourMinuteKey(parse0.getHour(), parse0.getMinute(), 0, interval);
        String t1 = quoteHistoryStatsUtil.getHourMinuteKey(parse1.getHour(), parse1.getMinute(), 1, interval);
        String t2 = quoteHistoryStatsUtil.getHourMinuteKey(parse2.getHour(), parse2.getMinute(), 2, interval);

        Assert.isTrue(StringUtils.equals(t0,"10:40_0"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t1,"15:00_1"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t2,"17:50_2"),"split error by "+interval+".");
    }

    /**
     * split by 20
     */
    @Test
    public void testInterval20(){
        int interval = 20;
        LocalTime parse0 = LocalTime.parse("09:31:08");
        LocalTime parse1 = LocalTime.parse("12:50:01");
        LocalTime parse2 = LocalTime.parse("17:09:50");

        String t0 = quoteHistoryStatsUtil.getHourMinuteKey(parse0.getHour(), parse0.getMinute(), 0, interval);
        String t1 = quoteHistoryStatsUtil.getHourMinuteKey(parse1.getHour(), parse1.getMinute(), 1, interval);
        String t2 = quoteHistoryStatsUtil.getHourMinuteKey(parse2.getHour(), parse2.getMinute(), 2, interval);

        Assert.isTrue(StringUtils.equals(t0,"09:20_0"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t1,"12:40_1"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t2,"17:00_2"),"split error by "+interval+".");
    }

    /**
     * split by 30
     */
    @Test
    public void testInterval30(){
        int interval = 30;
        LocalTime parse0 = LocalTime.parse("08:11:08");
        LocalTime parse1 = LocalTime.parse("11:25:01");
        LocalTime parse2 = LocalTime.parse("17:44:50");

        String t0 = quoteHistoryStatsUtil.getHourMinuteKey(parse0.getHour(), parse0.getMinute(), 0, interval);
        String t1 = quoteHistoryStatsUtil.getHourMinuteKey(parse1.getHour(), parse1.getMinute(), 1, interval);
        String t2 = quoteHistoryStatsUtil.getHourMinuteKey(parse2.getHour(), parse2.getMinute(), 2, interval);

        Assert.isTrue(StringUtils.equals(t0,"08:00_0"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t1,"11:00_1"),"split error by "+interval+".");
        Assert.isTrue(StringUtils.equals(t2,"17:30_2"),"split error by "+interval+".");
    }

}
