package com.sumscope.cdh.realtime.transfer.intercept_string_test;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.message.SourceBboMessageModel;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/30.
 */
public class BboMessageTest {
    @Test
    public void fun1(){

        String message = "{\"AckMsgHead\":{\"exectime\":0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[{\"MS_lastDealPrice\":\"4.1140\",\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"018002\",\"MS_BOND_KEY_LISTED_MARKET\":\"G0001242013FINPBB02SHSSE\",\"MS_BestVersion\":14886,\"MS_lastDealNetPrice\":\"102.060\",\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"G0001242013FINPBB02SH\",\"MS_BODY\":{\"MS_bidPrice\":\"4.1464\",\"MS_ofrYield\":\"4.1059\",\"MS_ofrOfferId\":\"G0001242013FINPBB02SHSSE-1\",\"MS_bidOfferId\":\"G0001242013FINPBB02SHSSE1\",\"MS_bidYield\":\"4.1464\",\"MS_ofrNetPrice\":\"102.070\",\"MS_ofrPrice\":\"4.1059\",\"MS_ofrVolume\":\"1.5\",\"MS_bidNetPrice\":\"102.020\",\"MS_goodsCode\":\"018002\",\"MS_createTime\":1506653554,\"MS_bidVolume\":\"27.0\"},\"MS_COMPANY_ID\":\"e\",\"MS_CREATE_TIME\":1506653554,\"MS_Version\":33712,\"MS_MODIFY_TIME\":1506653554,\"MS_LISTED_MARKET\":\"SSE\"},{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"113013\",\"MS_BOND_KEY_LISTED_MARKET\":\"G0001312017CORCVB01SSE\",\"MS_BestVersion\":14887,\"MS_lastDealNetPrice\":\"125.400\",\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"G0001312017CORCVB01\",\"MS_BODY\":{\"MS_ofrOfferId\":\"G0001312017CORCVB01SSE-1\",\"MS_bidOfferId\":\"G0001312017CORCVB01SSE1\",\"MS_ofrNetPrice\":\"125.440\",\"MS_ofrVolume\":\"55.5\",\"MS_bidNetPrice\":\"125.400\",\"MS_goodsCode\":\"113013\",\"MS_createTime\":1506653970,\"MS_bidVolume\":\"4.8\"},\"MS_COMPANY_ID\":\"e\",\"MS_CREATE_TIME\":1506653970,\"MS_Version\":33713,\"MS_MODIFY_TIME\":1506653970,\"MS_LISTED_MARKET\":\"SSE\"},{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"124655\",\"MS_BOND_KEY_LISTED_MARKET\":\"N0000752013CORLEB02SSE\",\"MS_BestVersion\":14888,\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"N0000752013CORLEB02\",\"MS_BODY\":{\"MS_bidPrice\":\"12.0400\",\"MS_ofrYield\":\"6.2658\",\"MS_ofrOfferId\":\"N0000752013CORLEB02SSE-1\",\"MS_bidOfferId\":\"N0000752013CORLEB02SSE1\",\"MS_bidYield\":\"12.0400\",\"MS_ofrNetPrice\":\"82.480\",\"MS_ofrPrice\":\"6.2658\",\"MS_ofrVolume\":\"3.0\",\"MS_bidNetPrice\":\"74.500\",\"MS_goodsCode\":\"124655\",\"MS_createTime\":1506653973,\"MS_bidVolume\":\"0.1\"},\"MS_COMPANY_ID\":\"e\",\"MS_CREATE_TIME\":1506653973,\"MS_Version\":33714,\"MS_MODIFY_TIME\":1506653973,\"MS_LISTED_MARKET\":\"SSE\"}]},\"seq\":273016}";

        int index1 = message.indexOf("[{");
        int index2 = message.indexOf("}]}");
        String intercept = message.substring(index1,index2+2);
        List<SourceBboMessageModel> sourceBboMessageModels = JSON.parseArray(intercept, SourceBboMessageModel.class);
        System.out.println(JSON.toJSONString(sourceBboMessageModels,true));
    }
}
