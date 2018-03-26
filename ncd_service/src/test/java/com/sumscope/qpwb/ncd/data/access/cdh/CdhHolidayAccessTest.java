package com.sumscope.qpwb.ncd.data.access.cdh;

import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.domain.HolidayDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class CdhHolidayAccessTest extends BaseApplicationTest {
    @Autowired
    private CdhHolidayAccess cdhHolidayAccess;

    /**
     * 工作日，结果为空
     * {
     *     date : "",
     *     gap : 0
     * }
     */
    @Test
    public void getGapTest1(){
        String date = "2018-05-23";
        int week = 3;
        HolidayDTO holidayDTO = cdhHolidayAccess.cacheIsContentDay(date, week);

        Assert.isTrue(StringUtils.compareIgnoreCase(holidayDTO.getDate(),"") == 0,"get holiday error.");
        Assert.isTrue(holidayDTO.getGap() == 0,"get holiday error.");
    }

    /**
     * 休息日，结果为
     * {
     *     date : "yyyy-MM-dd 周x",
     *     gap : n
     * }
     */
    @Test
    public void getGapTest2(){

        String date = "2018-07-29";
        int week = 7;
        HolidayDTO holidayDTO = cdhHolidayAccess.cacheIsContentDay(date, week);

        Assert.isTrue(StringUtils.compareIgnoreCase(holidayDTO.getDate(),"2018-07-29 周日") == 0,"get holiday error.");
        Assert.isTrue(holidayDTO.getGap() == 1,"get holiday error.");
    }

    /**
     * 休息日，结果为
     * {
     *     date : "yyyy-MM-dd 周x",
     *     gap : n
     * }
     */
    @Test
    public void getGapTest3(){
        String date = "2018-07-28";
        int week = 6;
        HolidayDTO holidayDTO = cdhHolidayAccess.cacheIsContentDay(date, week);

        Assert.isTrue(StringUtils.compareIgnoreCase(holidayDTO.getDate(),"2018-07-28 周六") == 0,"get holiday error.");
        Assert.isTrue(holidayDTO.getGap() == 2,"get holiday error.");

    }

}
