package com.sumscope.cdh.realtime.transfer.aspectj;

import com.sumscope.cdh.realtime.transfer.restful.MapDBUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/12/15.
 */
@Aspect
@Component
public class AspectJUtil {
    @Autowired
    private MapDBUtil mapDBUtil;

    @Before("execution(* com.sumscope.cdh.realtime.transfer.restful.MapDBUtil.*J(..))")
    public void openMapDB(){
        mapDBUtil.openDB();
    }

    @After("execution(* com.sumscope.cdh.realtime.transfer.restful.MapDBUtil.*J(..))")
    public void closeMapDB(){
        mapDBUtil.closeDB();
    }
}
