package com.sumscope.cdh.realtime.transfer.intercept_string_test;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.message.SourceTradeMessageModel;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/30.
 */
public class TradeMessageTest {

    @Test
    public void fun1(){
        String message = "{\"AckMsgHead\":{\"exectime\":0.0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":" +
                "{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[{\"MS_OPERATE\":\"2\",\"MS_GOODS_CODE\":\"041762011\",\"MS_COMPANY_NAME\":\"PATR\",\"MS_BOND_KEY_LISTED_MARKET\":\"W0000332017CORLCP01CIB\",\"MS_TYPE\":\"2\",\"MS_BOND_KEY\":\"W0000332017CORLCP01\",\"MS_BODY\":{\"MS_id\":\"232052\",\"MS_price\":\"4.73\",\"MS_fullPrice\":\"0\",\"MS_goodsShortName\":\"17\\u65e0\\u9521\\u5efa\\u6295CP001\",\"MS_dealStatus\":\"0\",\"MS_netPrice\":\"0\",\"MS_rebate\":\"0\",\"MS_goodsCode\":\"041762011\",\"MS_createTime\":1506499646,\"MS_yield\":\"4.73\"},\"MS_COMPANY_ID\":\"4\",\"MS_CREATE_TIME\":1506499646,\"MS_Version\":13307,\"MS_MODIFY_TIME\":1506499646,\"MS_LISTED_MARKET\":\"CIB\"}," +
                "{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"170210\",\"MS_COMPANY_NAME\":\"PATR\",\"MS_BOND_KEY_LISTED_MARKET\":\"G0001242017FINPBB10CIB\",\"MS_TYPE\":\"2\",\"MS_BOND_KEY\":\"G0001242017FINPBB10\",\"MS_BODY\":{\"MS_id\":\"232059\",\"MS_price\":\"4.3325\",\"MS_rebate\":\"0\",\"MS_goodsShortName\":\"17\\u56fd\\u5f0010\",\"MS_dealStatus\":\"0\",\"MS_goodsCode\":\"170210\",\"MS_createTime\":1506499842,\"MS_yield\":\"4.3325\"},\"MS_COMPANY_ID\":\"4\",\"MS_CREATE_TIME\":1506499842,\"MS_Version\":13308,\"MS_MODIFY_TIME\":1506499842,\"MS_LISTED_MARKET\":\"CIB\"}," +
                "{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"170210\",\"MS_COMPANY_NAME\":\"PATR\",\"MS_BOND_KEY_LISTED_MARKET\":\"G0001242017FINPBB10CIB\",\"MS_TYPE\":\"2\",\"MS_BOND_KEY\":\"G0001242017FINPBB10\",\"MS_BODY\":" + "{\"MS_id\":\"232060\",\"MS_price\":\"4.3325\",\"MS_rebate\":\"0\",\"MS_goodsShortName\":\"17\\u56fd\\u5f0010\",\"MS_dealStatus\":\"0\",\"MS_goodsCode\":\"170210\",\"MS_createTime\":1506499843,\"MS_yield\":\"4.3325\"},\"MS_COMPANY_ID\":\"4\",\"MS_CREATE_TIME\":1506499843,\"MS_Version\":13309,\"MS_MODIFY_TIME\":1506499843,\"MS_LISTED_MARKET\":\"CIB\"}]},\"seq\":146290}";

        int index1 = message.indexOf("[{");
        int index2 = message.indexOf("}]}");
        String intercept = message.substring(index1,index2+2);

        List<SourceTradeMessageModel> sourceTradeMessageModels = JSON.parseArray(intercept, SourceTradeMessageModel.class);

        System.out.println(JSON.toJSONString(sourceTradeMessageModels,true));
    }
}
