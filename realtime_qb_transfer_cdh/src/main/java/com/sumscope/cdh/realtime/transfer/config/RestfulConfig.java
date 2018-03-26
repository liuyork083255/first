package com.sumscope.cdh.realtime.transfer.config;

import com.sumscope.cdh.realtime.transfer.model.restful.RestfulModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by liu.yang on 2017/10/30.
 */
@Configuration
public class RestfulConfig {
    @Autowired
    private Environment configParam;

    private RestfulModel getRestfulModel(){
        RestfulModel restfulModel = new RestfulModel();
        restfulModel.setDataSourceId(100);
        restfulModel.setUser("CDH");
        restfulModel.setPassword("000000");
        restfulModel.setApiVersion("N");
        restfulModel.setStartPage(1);
        restfulModel.setPageSize(5000);
        restfulModel.setStartDate("");
        restfulModel.setEndDate("");
        restfulModel.setCodes(new ArrayList<>());
        return restfulModel;
    }

    @Bean(name="webbond_residual_maturity")
    public RestfulModel getWebbondResidualMaturity(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.WRM"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.WRM"));
        restfulModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns.WRM").split("\\|")));
        return restfulModel;
    }

    @Bean(name="cdc_bond_valuation")
    public RestfulModel getCdcBondValuation(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.CDC"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.CDC"));
        restfulModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns.CDC").split("\\|")));
        return restfulModel;
    }

    @Bean(name="csi_bond_valuation")
    public RestfulModel getCsiBondValuation(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.CSI"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.CSI"));
        restfulModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns.CSI").split("\\|")));
        return restfulModel;
    }

    @Bean(name="institution")
    public RestfulModel getInstitution(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.IST"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.IST"));
        restfulModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns.IST").split("\\|")));
        return restfulModel;
    }

    @Bean(name="holiday_info")
    public RestfulModel getHolidayInfo(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.HI"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.HI"));
        restfulModel.setColumns(Arrays.asList(configParam.getProperty("restful.request.param.Columns.HI").split("\\|")));
        return restfulModel;
    }

    @Bean(name="webbond_bond")
    public RestfulModel getWebbondBond(){
        RestfulModel restfulModel = getRestfulModel();
        restfulModel.setApiName(configParam.getProperty("restful.request.param.ApiName.WB"));
        restfulModel.setConditions(configParam.getProperty("restful.request.param.Conditions.WB"));
        restfulModel.setColumns(new ArrayList<>());
        return restfulModel;
    }

}
