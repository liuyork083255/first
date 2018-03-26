package com.sumscope.cdh.realtime.transfer.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.cdh.realtime.transfer.model.restful.RestfulModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by liu.yang on 2017/10/30.
 */
@Component
public class RestfulLoad {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulLoad.class);

    @Value("${restful.request.url}")
    private String restfulURL;
    @Value("${restful.request.param.PageSize}")
    private String restfulPageSizeString;
    private int restfulPageSizeInt;

    @Autowired
    private RestfulUtil restfulUtil;
    @Autowired
    private MapDBUtil mapDBUtil;

    @Autowired
    @Qualifier("webbond_residual_maturity")
    private RestfulModel restfulLoadWRM;
    @Autowired
    @Qualifier("cdc_bond_valuation")
    private RestfulModel restfulLoadCDC;
    @Autowired
    @Qualifier("csi_bond_valuation")
    private RestfulModel restfulLoadCSI;
    @Autowired
    @Qualifier("institution")
    private RestfulModel restfulLoadINT;
    @Autowired
    @Qualifier("holiday_info")
    private RestfulModel restfulLoadHI;
    @Autowired
    @Qualifier("webbond_bond")
    private RestfulModel restfulLoadWB;

    private static TreeMap<Date,Integer> containerCacheHI = new TreeMap<>();
    private static HashMap<String,JSONObject> containerCacheWB = new HashMap<>();
    private static HashMap<String,JSONObject> containerCacheWRM = new HashMap<>();
    private static HashMap<String,JSONObject> containerCacheCDC = new HashMap<>();
    private static HashMap<String,JSONObject> containerCacheCSI = new HashMap<>();
    private static HashMap<String,JSONObject> containerCacheINT = new HashMap<>();

    @PostConstruct
    public void init(){
        restfulPageSizeInt = Integer.parseInt(restfulPageSizeString);
        loadRestfulData();
    }

    public void loadRestfulData(){
        if(mapDBUtil.getMapSizeJ(RestfulCacheType.WRM) == 0) loadCacheWRM();
        else {
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.WRM,containerCacheWRM)) restfulUtil.initWRM(containerCacheWRM);
            else loadCacheWRM();
        }

        if(mapDBUtil.getMapSizeJ(RestfulCacheType.CDC) == 0) loadCacheCDC();
        else{
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.CDC,containerCacheCDC)) restfulUtil.initCDC(containerCacheCDC);
            else loadCacheCDC();
        }

        if(mapDBUtil.getMapSizeJ(RestfulCacheType.CSI) == 0) loadCacheCSI();
        else{
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.CSI,containerCacheCSI)) restfulUtil.initCSI(containerCacheCSI);
            else loadCacheCSI();
        }

        if(mapDBUtil.getMapSizeJ(RestfulCacheType.INT) == 0) loadCacheINT();
        else{
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.INT,containerCacheINT)) restfulUtil.initINT(containerCacheINT);
            else loadCacheINT();
        }

        if(mapDBUtil.getMapSizeJ(RestfulCacheType.HI) == 0) loadCacheHI();
        else{
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.HI,containerCacheHI)) restfulUtil.initHI(containerCacheHI);
            else loadCacheHI();
        }

        if(mapDBUtil.getMapSizeJ(RestfulCacheType.WB) == 0) loadCacheWB();
        else{
            if(mapDBUtil.convertToRestfulCacheJ(RestfulCacheType.WB,containerCacheWB)) restfulUtil.initWB(containerCacheWB);
            else loadCacheWB();
        }
    }

    @Scheduled(cron = "${restful.schedule.load.WRM}")
    public void loadCacheWRM(){
        LOGGER.info("start loadCacheWRM api ...");
        JSONArray list = load(restfulLoadWRM);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheWRM size=0");
            return;
        }

        HashMap<String,JSONObject> _containerCacheWRM = new HashMap<>();
        list.forEach(json ->
        {
            try {
                JSONObject _jsonObject = (JSONObject)json;
                _containerCacheWRM.put(_jsonObject.getString("bond_key"),_jsonObject);
            } catch (Exception e) {
                LOGGER.error("convert loadCacheWRM to cache error.   exception:{}",e.getMessage());
            }
        });
        LOGGER.info("loadCacheWRM cache size={}",_containerCacheWRM.size());
        setContainerCacheWRM(_containerCacheWRM);
        restfulUtil.initWRM(_containerCacheWRM);
        mapDBUtil.setMapJ(RestfulCacheType.WRM,_containerCacheWRM);
    }

    @Scheduled(cron = "${restful.schedule.load.CDC}")
    public void loadCacheCDC(){
        LOGGER.info("start loadCacheCDC api ...");
        JSONArray list = load(restfulLoadCDC);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheCDC size=0");
            return;
        }

        HashMap<String,JSONObject> _containerCacheCDC = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        list.forEach(json ->
        {
            // 加载中债估值：由于一张债券bond_key+listedMarket 可能会有两个记录，那么就取Credibility=推荐
            try {
                JSONObject _jsonObject = (JSONObject)json;
                sb.append(_jsonObject.getString("Bond_Key")).append("_").append(_jsonObject.getString("Listed_Market"));

                if(_containerCacheCDC.containsKey(sb.toString())){
                    if("推荐".equals(_jsonObject.getString("Credibility"))){
                        _containerCacheCDC.put(sb.toString(),_jsonObject);
                    }
                }else{
                    _containerCacheCDC.put(sb.toString(),_jsonObject);
                }
            } catch (Exception e) {
                LOGGER.error("convert loadCacheCDC to cache error.   exception:{}",e.getMessage());
            }finally {
                sb.delete(0,sb.length());
            }
        });
        LOGGER.info("loadCacheCDC cache size={}",_containerCacheCDC.size());
        setContainerCacheCDC(_containerCacheCDC);
        restfulUtil.initCDC(_containerCacheCDC);
        mapDBUtil.setMapJ(RestfulCacheType.CDC,_containerCacheCDC);
    }

    @Scheduled(cron = "${restful.schedule.load.CSI}")
    public void loadCacheCSI(){
        LOGGER.info("start loadCacheCSI api ...");
        JSONArray list = load(restfulLoadCSI);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheCSI size=0");
            return;
        }

        HashMap<String,JSONObject> _containerCacheCSI = new HashMap<>();
        list.forEach(json ->
        {
            JSONObject _jsonObject = (JSONObject)json;
            _containerCacheCSI.put(_jsonObject.getString("Bond_Key"),_jsonObject);
        });
        LOGGER.info("loadCacheCSI cache size={}",_containerCacheCSI.size());
        setContainerCacheCSI(_containerCacheCSI);
        restfulUtil.initCSI(_containerCacheCSI);
        mapDBUtil.setMapJ(RestfulCacheType.CSI,_containerCacheCSI);
    }

    @Scheduled(cron = "${restful.schedule.load.IST}")
    public void loadCacheINT(){
        LOGGER.info("start loadCacheINT api ...");
        JSONArray list = load(restfulLoadINT);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheINT size=0");
            return;
        }

        HashMap<String,JSONObject> _containerCacheINT = new HashMap<>();
        list.forEach(json ->
        {
            JSONObject _jsonObject = (JSONObject)json;
            _containerCacheINT.put(_jsonObject.getString("institution_code"),_jsonObject);
        });
        LOGGER.info("loadCacheINT cache size={}",_containerCacheINT.size());
        setContainerCacheINT(_containerCacheINT);
        restfulUtil.initINT(_containerCacheINT);
        mapDBUtil.setMapJ(RestfulCacheType.INT,_containerCacheINT);
    }

    @Scheduled(cron = "${restful.schedule.load.HI}")
    public void loadCacheHI(){
        LOGGER.info("start loadCacheHI api ...");
        JSONArray list = load(restfulLoadHI);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheHI size=0");
            return;
        }

        TreeMap<Date,Integer> _containerCacheHI = new TreeMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        list.forEach(json ->
        {
            try {
                JSONObject _jsonObject = (JSONObject)json;
                Date holiday_date = sdf.parse(_jsonObject.getString("holiday_date"));
                MarketValueEnum market_type = MarketValueEnum.fromString(_jsonObject.getString("market_type"));

                Integer originValue = _containerCacheHI.get(holiday_date);
                int targetValue = null == originValue ? market_type.getValue() : originValue | market_type.getValue();
                _containerCacheHI.put(holiday_date, targetValue);
            } catch (ParseException e) {
                LOGGER.error("loadCacheHI convert cache error.   exception:{}",e.getMessage());
            }
        });
        LOGGER.info("loadCacheHI cache size={}",_containerCacheHI.size());
        setContainerCacheHI(_containerCacheHI);
        restfulUtil.initHI(_containerCacheHI);
        mapDBUtil.setMapJ(RestfulCacheType.HI,_containerCacheHI);
    }

    @Scheduled(cron = "${restful.schedule.load.WB}")
    public void loadCacheWB(){
        LOGGER.info("start loadCacheWB api ...");
        JSONArray list = load(restfulLoadWB);
        if(list.size() == 0){
            LOGGER.error("restful loadCacheWB size=0");
            return;
        }

        HashMap<String,JSONObject> _containerCacheWB = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        list.forEach(json ->
        {
            try {
                JSONObject _jsonObject = (JSONObject)json;

                _jsonObject.remove("auction_date_start");
                _jsonObject.remove("bond_id");
                _jsonObject.remove("bond_subtype");
                _jsonObject.remove("compound_frequency");
                _jsonObject.remove("coupon_frequency");
                _jsonObject.remove("coupon_rate_current");
                _jsonObject.remove("coupon_rate_spread");
                _jsonObject.remove("coupon_type");
                _jsonObject.remove("delisted_date");
                _jsonObject.remove("ent_cor");
                _jsonObject.remove("fixing_ma_days");
                _jsonObject.remove("frn_index_id");
                _jsonObject.remove("full_name");
                _jsonObject.remove("interest_start_date");
                _jsonObject.remove("is_municipal");
                _jsonObject.remove("issue_amount");
                _jsonObject.remove("issue_price");
                _jsonObject.remove("issue_rate");
                _jsonObject.remove("issue_start_date");
                _jsonObject.remove("issue_year");
                _jsonObject.remove("issuer_code");
                _jsonObject.remove("issuer_rating_current");
                _jsonObject.remove("issuer_rating_current_npy");
                _jsonObject.remove("issuer_rating_date");
                _jsonObject.remove("listed_date");
                _jsonObject.remove("maturity_term");
                _jsonObject.remove("next_coupon_date");
                _jsonObject.remove("option_date");
                _jsonObject.remove("option_exercise_date");
                _jsonObject.remove("option_style");
                _jsonObject.remove("option_type");
                _jsonObject.remove("p_issuer_outlook_current");
                _jsonObject.remove("p_issuer_rating_current");
                _jsonObject.remove("p_rating_current");
                _jsonObject.remove("payment_date");
                _jsonObject.remove("planned_issue_amount");
                _jsonObject.remove("rating_augment");
                _jsonObject.remove("rating_current");
                _jsonObject.remove("rating_current_npy");
                _jsonObject.remove("rating_date");
                _jsonObject.remove("rating_institution_code");
                _jsonObject.remove("redemption_str");
                _jsonObject.remove("term_structure");
                _jsonObject.remove("term_unit");
                _jsonObject.remove("pinyin");
                _jsonObject.remove("cashflow_paymentdate");
                _jsonObject.remove("optionType");
                _jsonObject.remove("corporateBondType");
                _jsonObject.remove("qbBondType");
                _jsonObject.remove("couponType");
                _jsonObject.remove("newListed");
                _jsonObject.remove("perpetualType");
                _jsonObject.remove("bondRating");
                _jsonObject.remove("financialBondType");
                _jsonObject.remove("bondCode");
                _jsonObject.remove("interBank");
                _jsonObject.remove("exchange");
                _jsonObject.remove("pledge");

                sb.append(_jsonObject.getString("bond_key")).append("_").append(_jsonObject.getString("listed_market"));
                _containerCacheWB.put(sb.toString(),_jsonObject);
            } finally {
                sb.delete(0,sb.length());
            }
        });
        LOGGER.info("loadCacheWB cache size={}",_containerCacheWB.size());
        setContainerCacheWB(_containerCacheWB);
        restfulUtil.initWB(_containerCacheWB);
        mapDBUtil.setMapJ(RestfulCacheType.WB,_containerCacheWB);
    }

    private JSONArray load(RestfulModel restfulModel){
        restfulModel.setStartPage(1);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(restfulURL);
        JSONArray list = new JSONArray();
        try{
            do{
                HashMap<String,Object> result = target.request().post(
                        Entity.entity(JSON.toJSONString(restfulModel), MediaType.APPLICATION_JSON),HashMap.class);
                JSONArray _list = (JSONArray) result.get("resultTable");
                LOGGER.info("load page=>{} , size=>{}",restfulModel.getStartPage(),_list.size());
                list.addAll(_list);

                if(_list.size() < restfulPageSizeInt) {
                    break;
                }
                else {
                    restfulModel.setStartPage(restfulModel.getStartPage() + 1);
                }
            }while (true);
            client.close();
        }catch (Exception e){
            LOGGER.error("restful load data error.apiName={}    exception={}",restfulModel.getApiName(),e.getMessage());
            list.clear();
        }
        return list;
    }

    public HashMap<String, JSONObject> getContainerCacheWRM() {
        return containerCacheWRM;
    }

    private void setContainerCacheWRM(HashMap<String, JSONObject> containerCacheWRM) {
        this.containerCacheWRM = containerCacheWRM;
    }

    public HashMap<String, JSONObject> getContainerCacheCDC() {
        return this.containerCacheCDC;
    }

    private void setContainerCacheCDC(HashMap<String, JSONObject> containerCacheCDC) {
        this.containerCacheCDC = containerCacheCDC;
    }

    public HashMap<String, JSONObject> getContainerCacheCSI() {
        return this.containerCacheCSI;
    }

    private void setContainerCacheCSI(HashMap<String, JSONObject> containerCacheCSI) {
        this.containerCacheCSI = containerCacheCSI;
    }

    public HashMap<String, JSONObject> getContainerCacheINT() {
        return this.containerCacheINT;
    }

    private void setContainerCacheINT(HashMap<String, JSONObject> containerCacheINT) {
        this.containerCacheINT = containerCacheINT;
    }

    public TreeMap<Date,Integer> getContainerCacheHI() {
        return this.containerCacheHI;
    }

    private void setContainerCacheHI(TreeMap<Date,Integer> containerCacheHI) {
        this.containerCacheHI = containerCacheHI;
    }

    public HashMap<String, JSONObject> getContainerCacheWB() {
        return this.containerCacheWB;
    }

    private void setContainerCacheWB(HashMap<String, JSONObject> containerCacheWB) {
        this.containerCacheWB = containerCacheWB;
    }
}
