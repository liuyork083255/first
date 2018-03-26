package com.sumscope.cdh.realtime.model.restful;

import java.util.List;

/**
 * Created by liu.yang on 2017/10/18.
 */
public class RequestModel {
    private int DataSourceId;
    private String ApiName;
    private String User;
    private String Password;
    private String ApiVersion;
    private int StartPage;
    private int PageSize;
    private String StartDate;
    private String EndDate;
    private List<String> Codes;
    private String Conditions;
    private List<String> Columns;

    public int getDataSourceId() {
        return DataSourceId;
    }

    public void setDataSourceId(int dataSourceId) {
        DataSourceId = dataSourceId;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getApiVersion() {
        return ApiVersion;
    }

    public void setApiVersion(String apiVersion) {
        ApiVersion = apiVersion;
    }

    public int getStartPage() {
        return StartPage;
    }

    public void setStartPage(int startPage) {
        StartPage = startPage;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public List<String> getCodes() {
        return Codes;
    }

    public void setCodes(List<String> codes) {
        Codes = codes;
    }

    public String getConditions() {
        return Conditions;
    }

    public void setConditions(String conditions) {
        Conditions = conditions;
    }

    public List<String> getColumns() {
        return Columns;
    }

    public void setColumns(List<String> columns) {
        Columns = columns;
    }
}
