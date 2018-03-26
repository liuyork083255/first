package com.sumscope.cdh.realtime.transfer;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by liu.yang on 2017/10/11.
 */
public class FunctionPointTest {

    @Test
    public void fun1(){
//        long time = 1507714591;
        long time = 1507714591*1000l;
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        System.out.println(format);

        Long timestamp = Long.parseLong("1507714591")*1000;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        System.out.println(date);
    }

    @Test
    public void fun2(){
        String bondCode = "4156143IB";
        if(bondCode.indexOf(".")>0){
            System.out.println(bondCode.substring(0,bondCode.indexOf(".")));
        }else{
            System.out.println(bondCode);
        }
    }

    @Test
    public void fun3(){
        List<String> list = new ArrayList<>();
        System.out.println(JSON.toJSONString(list));
    }
}
