package com.sumscope.cdh.realtime.transfer.intercept_string_test;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.db.SourceSingleBboDBModel;
import org.junit.Test;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/30.
 */
public class SingleBboMessageTest {

    @Test
    public void fun1(){
        String message = "{\"AckMsgHead\":{\"exectime\":0.0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[" +
                "{\"ps\":\"1\",\"vol\":\"4000\",\"fbar\":\"0\",\"cn\":\"PATR\",\"cid\":\"4\",\"bklm\":\"Y0000272017CORLCP02CIB\",\"qt\":\"0\",\"lm\":\"CIB\",\"reb\":\"0\",\"bk\":\"Y0000272017CORLCP02\",\"mt\":1506499708,\"gc\":\"041764002\",\"sym\":\"-1\",\"sts\":\"1\",\"id\":\"3759358\",\"ver\":10130,\"gsn\":\"17\\u9633\\u7164CP002\",\"ds\":\"0\",\"ct\":1506499708}," +
                "{\"fbar\":\"0\",\"vol\":\"3000\",\"pri\":\"3.6225\",\"ldp\":\"3.6225\",\"gc\":\"170018\",\"id\":\"3759500\",\"cn\":\"PATR\",\"ps\":\"1\",\"ver\":10131,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1506499708,\"bklm\":\"Z0003002017GOVBGB18CIB\",\"yd\":\"3.6225\",\"sym\":\"-1\",\"bk\":\"Z0003002017GOVBGB18\",\"ds\":\"0\",\"ldnp\":\"0\",\"qt\":\"1\",\"cid\":\"4\",\"mt\":1506499708,\"sts\":\"1\",\"gsn\":\"17\\u9644\\u606f\\u56fd\\u503a18\"}," +
                "{\"fbar\":\"1\",\"vol\":\"3000\",\"pri\":\"4.55\",\"ldp\":\"4.7\",\"gc\":\"041762011\",\"id\":\"3754971\",\"cn\":\"PATR\",\"ps\":\"1\",\"ver\":10128,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1506499708,\"bklm\":\"W0000332017CORLCP01CIB\",\"yd\":\"4.55\",\"sym\":\"-1\",\"bk\":\"W0000332017CORLCP01\",\"ds\":\"0\",\"qt\":\"1\",\"cid\":\"4\",\"pdesc\":\"3000(*)\",\"mt\":1506499708,\"sts\":\"1\",\"gsn\":\"17\\u65e0\\u9521\\u5efa\\u6295CP001\"}," +
                "{\"fbar\":\"1\",\"vol\":\"3000\",\"pri\":\"4.55\",\"ldp\":\"4.7\",\"gc\":\"041762011\",\"id\":\"3754971\",\"cn\":\"PATR\",\"ps\":\"1\",\"ver\":10132,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1506499708,\"bklm\":\"W0000332017CORLCP01CIB\",\"yd\":\"4.55\",\"sym\":\"-1\",\"bk\":\"W0000332017CORLCP01\",\"ds\":\"0\",\"qt\":\"1\",\"cid\":\"4\",\"pdesc\":\"3000(*)\",\"mt\":1506499708,\"sts\":\"1\",\"gsn\":\"17\\u65e0\\u9521\\u5efa\\u6295CP001\"}]},\"seq\":146148}";

        int index1 = message.indexOf("[{");
        int index2 = message.indexOf("}]}");
        String intercept = message.substring(index1,index2+2);
        List<SourceSingleBboDBModel> sourceSingleBboDBModels = JSON.parseArray(intercept, SourceSingleBboDBModel.class);
        System.out.println(JSON.toJSONString(sourceSingleBboDBModels,true));

    }
}
