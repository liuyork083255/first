package com.sumscope.qpwb.ncd.data.access.cdh;

import com.alibaba.fastjson.JSONObject;
import com.sumscope.qpwb.ncd.domain.HolidayDTO;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CdhHolidayAccess extends CdhBaseAccess {

    private static final Logger logger = LoggerFactory.getLogger(CdhHolidayAccess.class);
    @Autowired
    private CdhRestWrapper cdhRestWrapper;

    @Value("${cdh.restful.url}")
    private String restfulUrl;
    @Value("${cdh.restful.ncd.holiday.api.name}")
    private String holidayApiName;
    @Value("${cdh.restful.username}")
    private String username;
    @Value("${cdh.restful.password}")
    private String password;

    private String currentDate;
    private List<String> cacheListCIB = new ArrayList<>();
    private List<String> cacheListSSE = new ArrayList<>();
    private List<String> cacheListSZE = new ArrayList<>();
    private Map<String,HolidayDTO> dueDayMap = new HashMap<>();


    @PostConstruct
    public void init(){
        currentDate = LocalDate.now().toString();
        getCIBHolidayList();
        getSSEHolidayList();
        getSZEHolidayList();
        initDueDay();
    }

    public String getIssueDate(){
        return getWorkdayByInt(LocalDate.now().toString(),1,NcdConstants.MARKET_CIB);
    }

    public String getWorkdayByInt(String currentDate, int num, String type){
        if(num < 1) return currentDate;
        switch (type){
            case NcdConstants.MARKET_CIB:return workdayByInt(currentDate,num,this.cacheListCIB);
            case NcdConstants.MARKET_SSE:return workdayByInt(currentDate,num,this.cacheListSSE);
            case NcdConstants.MARKET_SZE:return workdayByInt(currentDate,num,this.cacheListSZE);
            default:
                return currentDate;
        }
    }

    public void initDueDay(){
        this.dueDayMap = new HashMap<>();
        // 用 CIB 节假日计算并获得后两个工作日
        String nextTwoWorkdayStr = getWorkdayByInt(LocalDate.now().toString(), 2, NcdConstants.MARKET_CIB);
        logger.info("getDueDay : get next two workDay is [{}]", nextTwoWorkdayStr);
        LocalDate nextTwoWorkday = DateUtils.parseLocalDate(nextTwoWorkdayStr, NcdConstants.DATE_YYYY_MM_DD);
        // + 1M
        LocalDate next1MDate = nextTwoWorkday.plusMonths(1);
        this.dueDayMap.put(NcdConstants.LIMIT_1M,cacheIsContentDay(next1MDate.toString(),next1MDate.getDayOfWeek().getValue()));
        // + 3M
        LocalDate next3MDate = nextTwoWorkday.plusMonths(3);
        this.dueDayMap.put(NcdConstants.LIMIT_3M, cacheIsContentDay(next3MDate.toString(),next3MDate.getDayOfWeek().getValue()));
        // + 6M
        LocalDate next6MDate = nextTwoWorkday.plusMonths(6);
        this.dueDayMap.put(NcdConstants.LIMIT_6M, cacheIsContentDay(next6MDate.toString(),next6MDate.getDayOfWeek().getValue()));
        // + 9M
        LocalDate next9MDate = nextTwoWorkday.plusMonths(9);
        this.dueDayMap.put(NcdConstants.LIMIT_9M, cacheIsContentDay(next9MDate.toString(),next9MDate.getDayOfWeek().getValue()));
        // + 1Y
        LocalDate next1YDate = nextTwoWorkday.plusYears(1);
        this.dueDayMap.put(NcdConstants.LIMIT_1Y, cacheIsContentDay(next1YDate.toString(),next1YDate.getDayOfWeek().getValue()));
    }

    /**
     * 判断当前日期 <yyyy-MM-dd> 是否为节假日，只要在 CIB 中则返回 true
     * @param date
     * @return
     */
    public HolidayDTO cacheIsContentDay(String date,int week){
        logger.info("judge date = {} is holiday.",date);
        HolidayDTO hd = new HolidayDTO();
        int gap = getGap(date);
        if(gap != 0){
            hd.setDate(date + " " + getWeekOfChinaFmt(week));
            hd.setGap(gap);
            return hd;
        }
        return hd;
    }

    /**
     * 计算银行间下一个工作日的间隔天数
     * @param date
     * @return
     */
    private int getGap(String date){
        int count = 0;
        LocalDate parse = LocalDate.parse(date);

        while (this.cacheListCIB.contains(parse.toString())){
            count++;
            parse = parse.plusDays(1);
        }
        return count;
    }

    private String getWeekOfChinaFmt(int week){
        switch (week){
            case 1 : return "周一";
            case 2 : return "周二";
            case 3 : return "周三";
            case 4 : return "周四";
            case 5 : return "周五";
            case 6 : return "周六";
            case 7 : return "周日";
            default :
                logger.error("the week={} out of range 1-7",week);
                return "";
        }
    }

    private String workdayByInt(String currentDate, int num, List<String> list){
        LocalDate localDate = DateUtils.parseLocalDate(currentDate, NcdConstants.DATE_YYYY_MM_DD);
        LocalDate targetDate = localDate.plusDays(num);
        while (list.contains(targetDate.toString())){
            targetDate = targetDate.plusDays(1);
        }
        return targetDate.toString();
    }

    private void getCIBHolidayList(){
        List<JSONObject> cibList = getList(this.currentDate,NcdConstants.MARKET_CIB);
        if(CollectionUtils.isEmpty(cibList)) cibList = getList(this.currentDate,NcdConstants.MARKET_CIB);
        if(CollectionUtils.isEmpty(cibList)){
            logger.error("get {} holiday size is 0.",NcdConstants.MARKET_CIB);
        }else{
            synchronized (this) {
                this.cacheListCIB.clear();
                cibList.forEach(o -> this.cacheListCIB.add(o.getString(NcdConstants.API_HOLIDAY_KEY)));
            }
            logger.info("load {} holiday size is {}.",NcdConstants.MARKET_CIB,cibList.size());
        }
    }

    private void getSSEHolidayList(){
        List<JSONObject> shList = getList(this.currentDate,NcdConstants.MARKET_SSE);
        if(CollectionUtils.isEmpty(shList)) shList = getList(this.currentDate,NcdConstants.MARKET_SSE);
        if(CollectionUtils.isEmpty(shList)){
            logger.error("get {} holiday size is 0.",NcdConstants.MARKET_SSE);
        }else{
            synchronized (this) {
                this.cacheListSSE.clear();
                shList.forEach(o -> this.cacheListSSE.add(o.getString(NcdConstants.API_HOLIDAY_KEY)));
            }
            logger.info("load {} holiday size is {}.",NcdConstants.MARKET_SSE,shList.size());
        }
    }

    private void getSZEHolidayList(){
        List<JSONObject> szList = getList(this.currentDate,NcdConstants.MARKET_SZE);
        if(CollectionUtils.isEmpty(szList)) szList = getList(this.currentDate,NcdConstants.MARKET_SZE);
        if(CollectionUtils.isEmpty(szList)){
            logger.error("get {} holiday size is 0.",NcdConstants.MARKET_SZE);
        }else{
            synchronized (this) {
                this.cacheListSZE.clear();
                szList.forEach(o -> this.cacheListSZE.add(o.getString(NcdConstants.API_HOLIDAY_KEY)));
            }
            logger.info("load {} holiday size is {}.",NcdConstants.MARKET_SZE,szList.size());
        }
    }

    private List<JSONObject> getList(String date, String type){
        List<JSONObject> list = new ArrayList<>();
        try {
            list = cdhRestWrapper.getAllRowsSimple(
                    restfulUrl,
                    username,
                    password,
                    NcdConstants.RESTFUL_PAGE_SIZE,
                    NcdConstants.RESTFUL_DATASOURCE_ID,
                    holidayApiName,
                    String.format("holiday_date >= '%s' and market_type = '%s' order by holiday_date asc",date,type),
                    new ArrayList<>(),
                    JSONObject.class);
        } catch (Exception e) {
            logger.error("get holiday from restful error.   exception={}",e.getMessage());
        }
        return list;
    }

    public Map<String, HolidayDTO> getDueDayMap() {
        return dueDayMap;
    }
}
