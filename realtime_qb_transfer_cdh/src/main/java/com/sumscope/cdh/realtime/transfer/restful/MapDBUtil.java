package com.sumscope.cdh.realtime.transfer.restful;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.common.CommonUtil;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by liu.yang on 2017/12/15.
 */
@Component
public class MapDBUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapDBUtil.class);
    private static final String dbName = "restful_cache.db";
    private DB db;

    private static String tradeCacheDB = "trade_cache.db";
    static HTreeMap.KeySet<String> trade_set;
    private static DB tradeid_db;

    public void openDB(){
        try {
            this.db = DBMaker.fileDB(dbName).checksumHeaderBypass().fileMmapEnable().make();
            LOGGER.info("open map db success");
        } catch (Exception e) {
            LOGGER.error("mapDB init error   exception:{}",e.getMessage());
        }
    }

    public void closeDB(){
        if(db != null){
            db.close();
            LOGGER.info("close map db success");
        }
        else LOGGER.error("mapDB is null when close DB");
    }

    public static synchronized void saveTradeIdCache(Set<String> set){
        try {
            tradeid_db = DBMaker.fileDB(tradeCacheDB).checksumHeaderBypass().fileMmapEnable().make();
            trade_set = tradeid_db.hashSet("trade_set").serializer(Serializer.STRING).createOrOpen();
            set.forEach(k -> trade_set.add(k));
            LOGGER.info("save tradeId success.   size={}",trade_set.size());
        } catch (Exception e) {
            try {
                tradeCacheDB = "trade_cache" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".db";
                tradeid_db = DBMaker.fileDB(tradeCacheDB).make();
                trade_set = tradeid_db.hashSet("trade_set").serializer(Serializer.STRING).createOrOpen();
                set.forEach(k -> trade_set.add(k));
                LOGGER.info("save tradeId success.   size={}",trade_set.size());
            } catch (Exception e1) {
                LOGGER.error("save tradeId error.    exception={}",e1.getMessage());
            }
        }finally {
            closeTradeCacheDB();
        }
    }

    public static synchronized void deleteTradeIdCache(){
        try {
            tradeid_db = DBMaker.fileDB(tradeCacheDB).checksumHeaderBypass().fileMmapEnable().make();
            trade_set = tradeid_db.hashSet("trade_set").serializer(Serializer.STRING).createOrOpen();
            trade_set.clear();
        } catch (Exception e) {
            tradeCacheDB = "trade_cache" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".db";
            LOGGER.error("delete all trade_cache error.   exception={}",e.getMessage());
        }finally {
            closeTradeCacheDB();
        }
    }

    public Set<String> getTradeIdCache(){
        Set<String> _set = new HashSet<>();
        try {
            tradeid_db = DBMaker.fileDB(tradeCacheDB).checksumHeaderBypass().fileMmapEnable().make();
            trade_set = tradeid_db.hashSet("trade_set").serializer(Serializer.STRING).createOrOpen();
            trade_set.forEach(k -> _set.add(k));
            LOGGER.info("load tradeId cache size={}",_set.size());
        } catch (Exception e) {
            LOGGER.error("load tradeId cache error.exception={}",e.getMessage());
        }finally {
            closeTradeCacheDB();
        }
        return _set;
    }

    private static void closeTradeCacheDB(){
        if(tradeid_db != null)
            try {
                tradeid_db.commit();
                tradeid_db.close();
                tradeid_db = null;
            } catch (Exception e) {
                LOGGER.error("close trade map DB error.    exception={}",e.getMessage());
            }
    }

    private HTreeMap getMap(RestfulCacheType type){
        if(type == RestfulCacheType.HI){
            return db.hashMap(type.getValue()).keySerializer(Serializer.DATE).valueSerializer(Serializer.INTEGER).createOrOpen();
        }else{
            return db.hashMap(type.getValue()).keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        }
    }

    public int getMapSizeJ(RestfulCacheType type){
        int size = 0;
        try {
            size = getMap(type).size();
            LOGGER.info("{} get size={}",type.getValue(),size);
        } catch (Exception e) {
            LOGGER.error("{} cache get model from DB error    exception:{}",type.getValue(),e.getMessage());
        }
        return size;
    }

    public void setMapJ(RestfulCacheType type, Map map){
        try {
            HTreeMap mapDBCache = getMap(type);
            LOGGER.info("{} cache size={} before resetting cache",type.getValue(),mapDBCache.size());
            mapDBCache.clear();

            if(type == RestfulCacheType.HI) map.forEach((d,i) ->  mapDBCache.put(d,i));
            else map.forEach((s,j) -> mapDBCache.put(s, JSON.toJSONString(j)));

            LOGGER.info("{} cache size={} after resetting cache",type.getValue(),mapDBCache.size());
        } catch (Exception e) {
            LOGGER.error("save {} cache into mapDB error    exception:{}",type.getValue(),e.getMessage());
        }
    }

    public boolean convertToRestfulCacheJ(RestfulCacheType type,Map map){
        try {
            HTreeMap mapDBCache = getMap(type);

            if(type == RestfulCacheType.HI) mapDBCache.forEach((key,value) -> map.put(key,value));
            else{
                HTreeMap<String,String> _mapDBCache = (HTreeMap<String,String>)mapDBCache;
                _mapDBCache.forEach((key,value) -> map.put(key,JSON.parseObject(value)));
            }
            LOGGER.info("{} db cache convert to restful cache,size={}",type.getValue(),mapDBCache.size());
            return true;
        } catch (Exception e) {
            LOGGER.error("{} dbCache convert to restful cache error    exception:{}",type.getValue(),e.getMessage());
            return false;
        }
    }
}



















