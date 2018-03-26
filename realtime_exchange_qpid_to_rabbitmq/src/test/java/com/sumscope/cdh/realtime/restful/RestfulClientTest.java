package com.sumscope.cdh.realtime.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.cdh.realtime.model.restful.RequestModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by liu.yang on 2017/10/18.
 */
public class RestfulClientTest {

    private String url = "http://qbweb.idbhost.com/cdh/restfulapi/api/runapi";
    private Client client;
    private RequestModel requestModel;

    @Before
    public void getRequestModel(){
        requestModel = new RequestModel();
        requestModel.setDataSourceId(100);
        requestModel.setApiName("p_bond_list_info");
        requestModel.setUser("CDH");
        requestModel.setPassword("000000");
        requestModel.setApiVersion("N");
        requestModel.setStartPage(1);
        requestModel.setPageSize(1);
        requestModel.setStartDate("");
        requestModel.setEndDate("");
        requestModel.setCodes(new ArrayList<>());
        requestModel.setConditions("Bond_ID like '%SH' or Bond_ID like '%SZ'");
        requestModel.setColumns(Arrays.asList("Bond_ID|Bond_Key|Listed_Market".split("\\|")));
    }

    @Test
    public void fun1(){
        client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        HashMap<String,Object> result = target.request().post(Entity.entity(JSON.toJSONString(requestModel), MediaType.APPLICATION_JSON),HashMap.class);
        System.out.println(result.get("resultTable").getClass().getName());
        JSONArray list = (JSONArray) result.get("resultTable");

        list.forEach((model) -> {
            JSONObject jsonObject = (JSONObject)model;
            jsonObject.forEach((key,value) -> {
                System.out.println(key + "-" + value);
            });
        });
    }

    @After
    public void close(){
        if(client != null)
            client.close();
    }
}
