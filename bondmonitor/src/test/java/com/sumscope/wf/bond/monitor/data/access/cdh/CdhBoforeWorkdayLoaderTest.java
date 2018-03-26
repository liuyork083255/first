package com.sumscope.wf.bond.monitor.data.access.cdh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.service.webbond.common.http.HttpClientUtils;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdhBoforeWorkdayLoaderTest {
    private ParamModel paramModel = new ParamModel();
    private CdhBeforeWorkdayLoader cdhBeforeWorkdayLoader = new CdhBeforeWorkdayLoader();
    private static final String URL = "http://restfulapi-cdh.dev.sumscope.com:8080/api/runapi";
    @Before
    public void init() {
        paramModel.setDataSourceId(100);
        paramModel.setApiName("qbpro_recent_Nth_workdays_CIB");
        paramModel.setUser("CDH");
        paramModel.setPassword("000000");
        paramModel.setApiVersion("N");
        paramModel.setStartPage(1);
        paramModel.setStartDate("");
        paramModel.setEndDate("");
        paramModel.setPageSize(5000);
        paramModel.setColumns(Arrays.asList());
        paramModel.setConditions("");
    }

    /**
     * 获取前第 n 个工作
     * @throws HttpException
     */
    @Test
    public void getPreWorkdayOne() throws HttpException {
        String result = HttpClientUtils.post(URL, paramModel);
        JSONObject data = JSON.parseObject(result);
        List<JSONObject> list = (List<JSONObject> )data.get("resultTable");

        String preWorkday1 = cdhBeforeWorkdayLoader.getPreWorkday0(list, 1);
        Assert.isTrue(StringUtils.equals("20180309",preWorkday1),"compare error by 1");

        String preWorkday2 = cdhBeforeWorkdayLoader.getPreWorkday0(list, 2);
        Assert.isTrue(StringUtils.equals("20180308",preWorkday2),"compare error by 2");

        String preWorkday3 = cdhBeforeWorkdayLoader.getPreWorkday0(list, 3);
        Assert.isTrue(StringUtils.equals("20180307",preWorkday3),"compare error by 3");
    }

    /**
     * 获取前 n 个工作日列表
     * @throws HttpException
     */
    @Test
    public void getPreWorkdayList() throws HttpException{
        String result = HttpClientUtils.post(URL, paramModel);
        JSONObject data = JSON.parseObject(result);
        List<JSONObject> list = (List<JSONObject> )data.get("resultTable");

        List<String> preWorkdayList = cdhBeforeWorkdayLoader.getPreWorkdayList0(list, 5);
        System.out.println(preWorkdayList);
    }

}

class ParamModel{
    private int dataSourceId;
    private String apiName;
    private String user;
    private String password;
    private String apiVersion;
    private int startPage;
    private String startDate;
    private String endDate;
    private int pageSize;
    private List<String> columns;
    private String conditions;

    public int getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
