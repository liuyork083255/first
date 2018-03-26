package com.sumscope.qpwb.ncd.utils;

import com.alibaba.fastjson.JSON;
import com.sumscope.qpwb.ncd.BaseApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsTest extends BaseApplicationTest {
    @Test
    public void matchRate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM  d yyyy  h:mma");
        System.out.println(sdf.format(new Date()));

        Timestamp ss = DateUtils.convertToTimestampByMq("11  3 2015  3:22PM");
        System.out.println(ss);
    }
}
