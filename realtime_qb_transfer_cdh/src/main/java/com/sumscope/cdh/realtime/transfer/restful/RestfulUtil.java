package com.sumscope.cdh.realtime.transfer.restful;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by liu.yang on 2017/10/31.
 */
@Component
public class RestfulUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulUtil.class);

    @Autowired
    private NumberTool numberTool;

    private TreeMap<Date,Integer> containerCacheHI;
    private HashMap<String,JSONObject> containerCacheWB;
    private HashMap<String,JSONObject> containerCacheWRM;
    private HashMap<String,JSONObject> containerCacheCDC;
    private HashMap<String,JSONObject> containerCacheCSI;
    private HashMap<String,JSONObject> containerCacheINT;

    public void initHI(TreeMap<Date,Integer> containerCacheHI){
        this.containerCacheHI  = containerCacheHI;
    }
    public void initWB(HashMap<String,JSONObject> containerCacheWB){
        this.containerCacheWB  = containerCacheWB;
    }
    public void initWRM(HashMap<String,JSONObject> containerCacheWRM){
        this.containerCacheWRM = containerCacheWRM;
    }
    public void initCDC(HashMap<String,JSONObject> containerCacheCDC){
        this.containerCacheCDC = containerCacheCDC;
    }
    public void initCSI(HashMap<String,JSONObject> containerCacheCSI){
        this.containerCacheCSI = containerCacheCSI;
    }
    public void initINT(HashMap<String,JSONObject> containerCacheINT){
        this.containerCacheINT = containerCacheINT;
    }

    public String getShortName(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null) return "--";
        else{
            return jsonObject.getString("short_name");
        }
    }

    public String getRemaintTime(String key){
        JSONObject jsonObject = containerCacheWRM.get(key);
        if(jsonObject == null) {
            return "--";
        }else{
            return numberTool.convertDaysToYears(jsonObject.getString("residual_maturity"));
        }
    }

    public String getCdcValuation(String key){
        JSONObject jsonObject = containerCacheCDC.get(key);
        if(jsonObject == null){
            return "--";
        }else{
            return jsonObject.getString("Val_Yield");
        }
    }

    public String getCsiValuation(String key){
        JSONObject jsonObject = containerCacheCSI.get(key);
        if(jsonObject == null){
            return "--";
        }else{
            return jsonObject.getString("Yield_To_Maturity");
        }
    }

    public String getIssuerBondRating(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null){
            return "--";
        }else{
            return jsonObject.getString("corpBondRating");
        }
    }

    public String getExpection(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        Outlook outlook = Outlook.fromValue(jsonObject == null ? null : jsonObject.getString("issuer_outlook_current"));
        return outlook.getZh();
    }

    public String getRatingInstitutionName(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null) return "--";
        JSONObject jsonObject1 = containerCacheINT.get(jsonObject.getString("issuer_rating_institution_code"));
        if(jsonObject1 == null) return "--";
        return jsonObject1.getString("Short_Name_C");
    }

    public String getBidSubCdc(String key,String bid,String cdc){
        if(bid == null || "--".equals(cdc)) return "--";
        else return subA_B(key,bid,cdc,"CDC");
    }

    public String getCdcSubOfr(String key,String cdc,String ofr){
        if(ofr == null || "--".equals(cdc)) return "--";
        else return subA_B(key,cdc,ofr,"CDC");
    }

    public String getBidSubCsi(String key,String bid,String csi){
        if(bid == null || "--".equals(csi)) return "--";
        else return subA_B(key,bid,csi,"CSI");
    }

    public String getCsiSubOfr(String key,String csi,String ofr){
        if(ofr == null || "--".equals(csi)) return "--";
        else return subA_B(key,csi,ofr,"CSI");
    }

    public String getDuration(String key){
        JSONObject jsonObject = containerCacheCDC.get(key);
        if(jsonObject == null) return "--";
        else return jsonObject.getString("Val_Modified_Duration");
    }

    public Integer getRemaintTimeValue(String key){
        JSONObject jsonObject = containerCacheWRM.get(key);
        if(jsonObject == null) return -999;
        else {
            String residualMaturityFormat = jsonObject.getString("residual_maturity");
            String residualMaturity = String.valueOf(jsonObject.getInteger("Now_to_OptionDate_or_to_MaturityDate_days"));
            if (residualMaturityFormat.indexOf(residualMaturity) < 0) {
                String _residualMaturity = residualMaturityFormat.substring(0, residualMaturityFormat.indexOf('D'));
                return Integer.parseInt(_residualMaturity);
            } else {
                return Integer.parseInt(residualMaturity);
            }
        }
    }

    public Double getBidVolumeValue(String value){
        if(value == null || "--".equals(value)) return -999D;
        else return getVolumeValue(value);
    }

    public Double getOfrVolumeValue(String value){
        if(value == null || "--".equals(value)) return -999D;
        else return getVolumeValue(value);
    }

    public Double getBidValue(String value){
        if(value == null || "--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getOfrValue(String value){
        if(value == null || "--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getCdcValuationValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getCsiValuationValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getBidSubCdcValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getBidSubCsiValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getCdcSubOfrValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public Double getCsiSubOfrValue(String value){
        if("--".equals(value)) return -999D;
        else return getDoubleValue(value);
    }

    public String getPinyinFull(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null || jsonObject.getString("pinyin_full") == null) return "--";
        else return jsonObject.getString("pinyin_full").split("_")[0];
    }

    public String getRatingInstitutionPinyin(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null) return "--";

        JSONObject jsonObject1 = containerCacheINT.get(jsonObject.getString("issuer_rating_institution_code"));
        if(jsonObject1 == null) return "--";

        return jsonObject1.getString("Pin_Yin_Full").split("_")[0];
    }

    public String getExpectionValue(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        Outlook outlook = Outlook.fromValue(jsonObject == null ? null : jsonObject.getString("issuer_outlook_current"));
        return outlook.getValue();
    }

    public Double getBidVolumeSortValue(String v,Double d){
        return getSortVolumeValue(v,d);
    }

    public Double getOfrVolumeSortValue(String v,Double d){
        return getSortVolumeValue(v,d);
    }

    public Integer getExpectionSortValue(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        Outlook outlook = Outlook.fromValue(jsonObject == null ? null : jsonObject.getString("issuer_outlook_current"));
        return outlook.ordinal();
    }

    public Integer getRestSymbolDays(String key,String key_mklt){
        String bondResidualMaturity = getBondResidualMaturity(key);
        if(bondResidualMaturity != null){
            JSONObject wbObject = containerCacheWB.get(key_mklt);
            if(wbObject == null) return 0;

            String maturity_date = wbObject.getString("maturity_date");
            return getRestSymbolDays(maturity_date,"CIB",containerCacheHI);
        }else{
            return 0;
        }
    }

    public Integer getRestSymbolDaysExchange(String key,String key_mklt){
        String bondResidualMaturity = getBondResidualMaturity(key);
        if(bondResidualMaturity != null){
            JSONObject wbObject = containerCacheWB.get(key_mklt);
            if(wbObject == null) return 0;
            String maturity_date = wbObject.getString("maturity_date");
            return getRestSymbolDays(maturity_date,"SSE",containerCacheHI);
        }else{
            return 0;
        }
    }

    public String getBusinessCode(String key){
        JSONObject jsonObject = containerCacheWB.get(key);
        if(jsonObject == null) return "BOND";
        if("NCD".equals(jsonObject.getString("bond_type")))
            return "NCD";
        return "BOND";
    }

    public double parseDouble(String value){
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return -999D;
        }
    }

    private String subA_B(String key,String a,String b,String type){
        NumberFormat format4 = new DecimalFormat("#0.0000");
        try{
            return format4.format(Double.parseDouble(a)-Double.parseDouble(b));
        }catch (Exception e){
            LOGGER.warn("[subA_B] get warn. bond={}_{}   a={} sub b={}",key,type,a,b);
            return "--";
        }
    }

    private Double getVolumeValue(String value){
        Double _d = new Double(0.0);
        String[] split = value.split("\\+");
        for(String s : split){
            try {
                double v = Double.parseDouble(s);
                _d += v;
            }catch (Exception e){
            }
        }
        return _d;
    }

    private Double getDoubleValue(String value){
        Double _d = new Double(0.0);
        try {
            double v = Double.parseDouble(value);
            _d += v;
        }catch (Exception e){
        }
        return _d;
    }

    private Double getSortVolumeValue(String volumeStr, Double volumeValue) {
        if (StringUtils.contains(volumeStr, "+")) {
            String firstNumber = volumeStr.split("\\+")[0].trim();
            if (NumberUtils.isNumber(firstNumber)) {
                return Double.valueOf(firstNumber);
            } else {
                return 0D;
            }
        } else {
            return volumeValue;
        }
    }

    private String getBondResidualMaturity(String key){
        JSONObject jsonObject = containerCacheWRM.get(key);
        if(jsonObject == null) return null;
        else{
            try {
                String residualMaturityFormat = jsonObject.getString("residual_maturity");
                String residualMaturity = jsonObject.getInteger("Now_to_OptionDate_or_to_MaturityDate_days").toString();
                if (residualMaturityFormat.indexOf(residualMaturity) < 0) {
                    String _residualMaturity = residualMaturityFormat.substring(0, residualMaturityFormat.indexOf('D'));
                    return _residualMaturity;
                } else {
                    return residualMaturity;
                }
            } catch (Exception e) {
                LOGGER.error("get error.   bondKey={}    exception={}",key,e.getMessage());
                return null;
            }
        }
    }

    private int getRestSymbolDays(String dateString, String marketType, TreeMap<Date, Integer> ascHolidays) {
        try {
            Date date = DateUtils.parseDate(dateString, "yyyyMMdd");
            Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            int diff = (int) TimeUnit.DAYS.convert(date.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            if (diff < 365) {
                return getRestSymbolDay(dateString, marketType, ascHolidays);
            }
        } catch (Exception e) {
            LOGGER.error("got exception", e);
        }
        return 0;
    }

    private int getRestSymbolDay(String dateStr, String marketType, TreeMap<Date, Integer> ascHolidays) throws ParseException {
        Date date = DateUtils.parseDate(dateStr, "yyyyMMdd");
        Integer value = ascHolidays.get(date);
        int marketValue = MarketValueEnum.toInt(marketType);
        if (null != value && 0 != (value & marketValue)) {
            int result = 0;
            for (Map.Entry<Date, Integer> holidayEntry : ascHolidays.tailMap(date).entrySet()) {
                if (0 != (marketValue & holidayEntry.getValue())) {
                    int diff;
                    diff = (int) TimeUnit.DAYS.convert((holidayEntry.getKey().getTime() - date.getTime()), TimeUnit.MILLISECONDS);
                    if (result == diff) {
                        result++;
                    } else {
                        break;
                    }
                }
            }
            return result;
        }
        return 0;
    }

    private boolean isBondSubtype(String type){
        if(type == null) return false;
        switch (type){
            case "MCD":return true;
            case "SPD":return true;
            case "SHD":return true;
            case "CCD":return true;
            case "RRD":return true;
            case "RTD":return true;
            case "FRD":return true;
            case "OTD":return true;
            default:return false;
        }
    }
}
