package com.sumscope.qpwb.ncd.data.qpwb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.service.webbond.common.http.HttpClientUtils;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import org.junit.Test;

public class QpwbNcdIssuersAccessTest {

    private String url = "http://edm-iam.dev.sumscope.com/edm/api/v1/users/";
    private String userId = "2351436e2f4d11e5867e000c29a13c19/";

    @Test
    public void getIssuerNameByUserId() throws HttpException {
        String response = HttpClientUtils.get(url + userId, null);
        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("data");
        System.out.println(JSON.toJSONString(jsonObject,true));
    }
}
