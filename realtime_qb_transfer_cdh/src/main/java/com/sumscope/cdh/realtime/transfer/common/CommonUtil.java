package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.cdh.realtime.transfer.restful.MapDBUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liu.yang on 2017/9/30.
 */
@Component
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
    private Set<String> tradeIdSet = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    private MapDBUtil mapDBUtil;

    @Scheduled(cron = "${cache.schedule.save.trade.id}")
    public void saveTradeIdSetToDB(){
        mapDBUtil.saveTradeIdCache(tradeIdSet);
    }

    @Scheduled(cron = "${cache.schedule.delete.trade.all}")
    public void deleteTradeIdSetToDB(){
        tradeIdSet.clear();
        mapDBUtil.deleteTradeIdCache();
        LOGGER.info("delete all tradeId. the tradeIdSet size={} now.",tradeIdSet.size());
    }

    @PostConstruct
    public void init(){
        mapDBUtil.getTradeIdCache();
    }

    public boolean containTradeId(String tradeId){
        if(tradeId == null || tradeIdSet.contains(tradeId)){
            return true;
        }
        tradeIdSet.add(tradeId);
        return false;
    }

    public String getMessage(String message){
        return message.substring(message.indexOf("[{"),message.indexOf("}]}")+2);
    }

    public String getBusinessCode(String msg){
        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            JSONObject ackMsgBody = (JSONObject)jsonObject.get("AckMsgBody");
            return ackMsgBody.getString("BusinessCode");
        } catch (Exception e) {
            LOGGER.error("get BusinessCode error.  message={}   exception={}",msg,e.getMessage());
            return null;
        }
    }

    public String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public String convertLongToStringDatetime(long t){
        if(t == 0)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        else
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
    }

    public int getDBPartition(){
        return Integer.parseInt(new SimpleDateFormat("yyyyMM").format(new Date()));
    }

    public String getTime(long t){
        return new SimpleDateFormat("HH:mm:ss").format(t);
    }

    public String convertOperate(String type){
        switch (type){
            case "0":return "TKN";
            case "1":return "GVN";
            case "2":return "TRD";
            default:throw new RuntimeException("convertOperate no match.MS_OPERATE=" + type);
        }
    }

    public String setListedMarket(String bondCode,String type){
        if(bondCode.indexOf(".") > 0){
            return bondCode;
        }else{
            return bondCode + convertListedMarket(type);
        }
    }

    private String convertListedMarket(String type){
        switch (type){
            case "CIB":return ".IB";
            case "SSE":return ".SH";
            case "SZE":return ".SZ";
            default:throw new RuntimeException("convertListedMarket no match.MS_LISTED_MARKET=" + type);
        }
    }

    public String convertBrokerName(String type){
        switch (type){
            case "1":return "国利";
            case "2":return "国际";
            case "3":return "中诚";
            case "4":return "平安";
            case "5":return "信唐";
            default:throw new RuntimeException("convertBrokerName no match.MS_COMPANY_ID=" + type);
        }
    }

    public boolean isBroker(String type){
        if(type == null || (!type.equals("1") && !type.equals("2") && !type.equals("3") && !type.equals("4") && !type.equals("5")))
            return false;
        else
            return true;
    }

    public String formatVolume(String volumeStr) {
        if (StringUtils.isEmpty(volumeStr) || StringUtils.equals(volumeStr, "0"))
            return "--";
        else {
            if (volumeStr.contains("+")) {
                String[] strs = volumeStr.split("\\+");
                List<String> sb = new ArrayList<>();
                for (String str : strs) {
                    if (StringUtils.equals(str, "0")) {
                        str = "--";
                    }
                    sb.add(str);
                }
                return StringUtils.join(sb, "+");
            }
            return volumeStr;
        }
    }

    public String getBid(String bidPrice,String bidOfferId){
        if(bidOfferId == null) return "--";
        else if(bidPrice == null) return "Bid";
        else return bidPrice;
    }

    public String getOfr(String ofrPrice,String ofrOfferId){
        if(ofrOfferId == null) return "--";
        else if(ofrPrice == null) return "Ofr";
        else return ofrPrice;
    }

    /**
     * 以下所有方法都是供 source singleBbo 转 target singleBbo 调用
     */

    public String getBidOrAsk(String sym){
        if(StringUtils.equals(sym,"1")){
            return "bid";
        }else if(StringUtils.equals(sym,"-1")){
            return "ask";
        }else{
            return null;
        }
    }

    public Long getLocMsgCrtAt(){return System.currentTimeMillis();}

    public String getUniDataID(String brokerId,String market,String bongKey,String quoteId,String sym){
        try{
            String symbol = Integer.parseInt(sym) > 0 ? "B" : "O";
            return brokerId + "," + market + "," + bongKey + "," + symbol + "," + quoteId;
        }catch (Exception e){
            LOGGER.error("source single model field sym parse error.  e={}   sym={}",e.getMessage(),sym);
            return brokerId + "," + market + "," + bongKey + "," + sym + "," + quoteId;
        }
    }

    public byte getSide(String sym){
        try {
            return (byte)Integer.parseInt(sym);
        } catch (NumberFormatException e) {
            throw new RuntimeException("sym convert byte error when source bbo_1 transfer target bbo_1");
        }
    }

    public Integer getStatus(String sts){
        try {
            return Integer.parseInt(sts);
        } catch (NumberFormatException e) {
            throw new RuntimeException("sts convert Integer error when source bbo_1 transfer target bbo_1");
        }
    }

    public Integer getDealStatus(String ds){
        try {
            return Integer.parseInt(ds);
        } catch (NumberFormatException e) {
            throw new RuntimeException("ds convert Integer error when source bbo_1 transfer target bbo_1");
        }
    }

    public Byte getBargainFlag(String fbar){
        try {
            return (byte)Integer.parseInt(fbar);
        } catch (NumberFormatException e) {
            throw new RuntimeException("fbar convert byte error when source bbo_1 transfer target bbo_1");
        }
    }

    public Byte getRelationFlag(String fr){
        try {
            return (byte)Integer.parseInt(fr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("fr convert byte error when source bbo_1 transfer target bbo_1");
        }
    }

    public Double getRebate(String reb){
        try {
            return Double.parseDouble(reb);
        } catch (NumberFormatException e) {
            throw new RuntimeException("reb convert Double error when source bbo_1 transfer target bbo_1");
        }
    }

    public Double getYtm(String yd){
        try {
            return Double.parseDouble(yd);
        } catch (NumberFormatException e) {
            throw new RuntimeException("yd convert Double error when source bbo_1 transfer target bbo_1");
        }
    }

    public Double getPrice(String pri){
        try {
            return Double.parseDouble(pri);
        } catch (NumberFormatException e) {
            throw new RuntimeException("pri convert Double error when source bbo_1 transfer target bbo_1");
        }
    }

    public Double getCleanPrice(String np){
        try {
            return Double.parseDouble(np);
        } catch (NumberFormatException e) {
            throw new RuntimeException("np convert Double error when source bbo_1 transfer target bbo_1");
        }
    }

    public String getUpdateDateTime(long mt,long ct){
        String result;
        try{
            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mt);
            return result;
        }catch (Exception e){
            try{
                result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ct);
                return result;
            }catch (Exception ee){
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
            }
        }
    }

    public Set<String> getTradeIdSet() {
        return tradeIdSet;
    }

    public void setTradeIdSet(Set<String> tradeIdSet) {
        this.tradeIdSet = tradeIdSet;
    }
}
