package com.sumscope.cdh.realtime.restful;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liu.yang on 2017/10/18.
 */
public class FunctionPointTest {

    @Test
    public void fun1(){
        StringBuffer sb = new StringBuffer("12345689");
        System.out.println(sb.toString());
        sb.delete(0,sb.length());
        System.out.println(sb.toString());

    }

    @Test
    public void fun2(){
        HashMap<String,String> map = new HashMap<>();
        map.put("hehe","");
        System.out.println(String.valueOf(map.get("hehddde")).equals("null"));
        System.out.println(map.get("hehe") == null);
    }

    @Test
    public void fun3() throws InterruptedException {
        new Thread(() ->{print("thread 1");}).start();
        new Thread(() ->{print("thread 2");}).start();
        Thread.sleep(1000000000);
    }

    private void print(String type){
        while (true) {
            System.out.println(type);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void fun4(){
        AtomicInteger count = new AtomicInteger();
        count.addAndGet(100);
        System.out.println(count);
    }

    @Test
    public void fun(){

    }
}
