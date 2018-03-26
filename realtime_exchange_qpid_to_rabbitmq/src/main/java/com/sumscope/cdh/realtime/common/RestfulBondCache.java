package com.sumscope.cdh.realtime.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class RestfulBondCache {

    private static HashMap<String,HashMap<String,String>> bondCache;

    public static HashMap<String, HashMap<String, String>> getBondCache() {
        return bondCache;
    }

    public static void setBondCache(HashMap<String, HashMap<String, String>> bondCache) {
        RestfulBondCache.bondCache = bondCache;
    }

    public static synchronized void put(String key, HashMap<String,String> value){
        bondCache.put(key,value);
    }

    public static synchronized void remove(String key){
        bondCache.remove(key);
    }

    public boolean containsKey(String key){
        return bondCache.containsKey(key);
    }

    public HashMap<String,String> get(String key){
        return bondCache.get(key);
    }

    public int size(){
        return bondCache.size();
    }

}
