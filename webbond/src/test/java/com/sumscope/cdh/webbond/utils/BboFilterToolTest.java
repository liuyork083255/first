package com.sumscope.cdh.webbond.utils;

import com.sumscope.cdh.webbond.model.Bond;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by chengzhang.wang on 2017/3/31.
 */
public class BboFilterToolTest
{
    BboFilterTool bboFilterTool;
    @Before
    public void setUp() throws Exception
    {
        bboFilterTool = new BboFilterTool();
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void getSortVolumeValueTest(){
        assert bboFilterTool.getSortVolumeValue("2000") == 2000;
        assert bboFilterTool.getSortVolumeValue("2000 + 3000") == 2000;
        assert bboFilterTool.getSortVolumeValue("--+3000") == -999D;
        assert bboFilterTool.getSortVolumeValue(" 2000+3000") == 2000;
        assert bboFilterTool.getSortVolumeValue(" 2000 +3000") == 2000;
    }

    @Test
    public void test(){
        TreeMap<Integer,String> treeMap = new TreeMap();
        treeMap.put(4,"a");
        treeMap.put(3,"b");
        treeMap.put(2,"c");
        treeMap.put(1,"d");
        for (String s : treeMap.values())
        {
            System.out.println(s);
        }
    }

    @Test
    public void testTreeMap(){
        TreeMap treeMap = new TreeMap();
        treeMap.put("","");
    }

}