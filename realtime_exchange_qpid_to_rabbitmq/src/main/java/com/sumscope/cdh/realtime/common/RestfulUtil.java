package com.sumscope.cdh.realtime.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.cdh.realtime.model.restful.RequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class RestfulUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulUtil.class);

    private Client client;
    @Autowired
    private RequestModel requestModel;
    @Autowired
    private Environment configParam;
    @Autowired
    private RestfulBondCache restfulBondCache;

    @PostConstruct
    public void initBondCache(){
        client = ClientBuilder.newClient();
        WebTarget target = client.target(configParam.getProperty("restful.request.url"));

        LOGGER.info("start request restful api ...");
        HashMap<String,HashMap<String,String>> _temp = new HashMap<>();
        requestModel.setStartPage(1);
        try {
            do
            {
                HashMap<String,Object> result = target.request().post(
                        Entity.entity(JSON.toJSONString(requestModel), MediaType.APPLICATION_JSON),HashMap.class);
                JSONArray list = (JSONArray) result.get("resultTable");
                LOGGER.info("load page=>{} , size=>{}",requestModel.getStartPage(),list.size());
                list.forEach((bond) ->
                {
                    JSONObject jsonObject = (JSONObject)bond;
                    HashMap<String,String> value = new HashMap<>();
                    value.put("BondKey",jsonObject.getString("Bond_Key"));
                    value.put("ListedMarket",jsonObject.getString("Listed_Market"));
                    _temp.put(jsonObject.getString("Bond_ID"),value);
                });

                if(list.size() < Integer.parseInt(configParam.getProperty("restful.request.param.PageSize")))
                {
                    break;
                }
                else
                {
                    requestModel.setStartPage(requestModel.getStartPage() + 1);
                }
            }while (true);

            LOGGER.info("load total size =>{}",_temp.size());
            if(_temp.isEmpty()) return;
            restfulBondCache.setBondCache(_temp);

            close();
        } catch (Exception e) {
            LOGGER.error("request restful api error.exception:" + e.getMessage());
        }
    }

    public boolean containsKey(String key){
        return restfulBondCache.containsKey(key);
    }

    public HashMap<String,String> getValue(String key){
        return restfulBondCache.get(key);
    }

    @Scheduled(cron = "${restful.schedule.load.time}")
    public void startSchedule(){
        LOGGER.info("schedule start...");
        initBondCache();
    }

    public void close(){
        if(client != null)
        {
            client.close();
            client = null;
            LOGGER.info("request restful api closed ...");
        }
    }
}
