package com.sumscope.cdh.realtime.config;

import com.sumscope.cdh.realtime.model.restful.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Configuration
public class RestfulConfig {
    @Autowired
    private Environment configParam;

    @Bean
    public RequestModel getRequestModel(){
        RequestModel requestModel = new RequestModel();
        try
        {
            requestModel.setDataSourceId(Integer.parseInt(configParam.getProperty("restful.request.param.DataSourceId")));
            requestModel.setApiName(configParam.getProperty("restful.request.param.ApiName"));
            requestModel.setUser(configParam.getProperty("restful.request.param.User"));
            requestModel.setPassword(configParam.getProperty("restful.request.param.Password"));
            requestModel.setApiVersion(configParam.getProperty("restful.request.param.ApiVersion"));
            requestModel.setStartPage(Integer.parseInt(configParam.getProperty("restful.request.param.StartPage")));
            requestModel.setPageSize(Integer.parseInt(configParam.getProperty("restful.request.param.PageSize")));
            requestModel.setStartDate(configParam.getProperty("restful.request.param.StartDate"));
            requestModel.setEndDate(configParam.getProperty("restful.request.param.EndDate"));
            requestModel.setCodes(new ArrayList<>());
            requestModel.setConditions(configParam.getProperty("restful.request.param.Conditions"));
            requestModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns").split("\\|")));
        }
        catch (Exception e)
        {
            throw new RuntimeException("init restful param error.exception:"+e.getMessage());
        }
        return requestModel;
    }
}
