package com.sumscope.wf.bond.monitor.data.access.cdh;

import com.alibaba.fastjson.JSONObject;
import com.sumscope.wf.bond.monitor.global.ConfigParams;
import com.sumscope.wf.bond.monitor.global.constants.BondMonitorConstants;
import com.sumscope.wf.bond.monitor.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CdhBeforeWorkdayLoader {
    private static final Logger logger = LoggerFactory.getLogger(CdhBeforeWorkdayLoader.class);
    @Autowired
    private ConfigParams configParams;
    @Autowired
    private CdhRestWrapper cdhRestWrapper;

    public List<JSONObject> loadCdhBeforeWorkday(){
        List<JSONObject> jsonObjectList = cdhRestWrapper.getJSONObjectList(
                configParams.getRestfulUrl(),
                configParams.getUsername(),
                configParams.getPassword(),
                BondMonitorConstants.RESTFUL_PAGE_SIZE,
                BondMonitorConstants.RESTFUL_DATASOURCE_ID_100,
                configParams.getQbproRecentNthWorkdaysCibName(),
                "",
                new ArrayList<>());
        logger.info("get qbpro_recent_Nth_workdays_CIB restful size={}", jsonObjectList.size());
        return jsonObjectList;
    }

    /**
     * @param n 获取前第 n 个工作日
     * @return
     */
    public String getPreWorkday(int n){
        List<JSONObject> list = loadCdhBeforeWorkday();
        return getPreWorkday0(list,n);
    }

    public String getPreWorkday0(List<JSONObject> list,int n){
        String localDateString = DateUtils.getLocalDateString(BondMonitorConstants.DATE_YYYYMMDD);

        if(!CollectionUtils.isEmpty(list)){
            if(n > 0){
                if(!isWorkday(localDateString,list)) n--;
                return list.get(n).getString(BondMonitorConstants.REST_NTH_WORKDAY_API_NAME);
            }else
                logger.error("get pre workday param is not support. day={}",n);
        }else
            logger.error("get PreWorkDayCIB list size is 0.");
        return localDateString;
    }

    /**
     * 获取前 inc 个工作日
     * @param inc
     * @return
     */
    public List<String> getPreWorkdayList(int inc){
        logger.info("get getPreWorkdayList by {}", inc);
        List<JSONObject> list = loadCdhBeforeWorkday();
        return getPreWorkdayList0(list,inc);
    }

    public List<String> getPreWorkdayList0(List<JSONObject> list,int inc){
        ArrayList<String> workdays = new ArrayList<>();
        if(inc < 1 || CollectionUtils.isEmpty(list)) return workdays;
        do{
            workdays.add(list.get(inc - 1).getString(BondMonitorConstants.REST_NTH_WORKDAY_API_NAME));
            inc--;
        }while (inc > 0);
        logger.info("get getPreWorkdayList is= {}",workdays.toString());
        return workdays;
    }

    private boolean isWorkday(String day, List<JSONObject> list){
        return StringUtils.equalsIgnoreCase(
                list.get(0).getString(BondMonitorConstants.REST_NTH_WORKDAY_API_NAME),day);
    }
}
