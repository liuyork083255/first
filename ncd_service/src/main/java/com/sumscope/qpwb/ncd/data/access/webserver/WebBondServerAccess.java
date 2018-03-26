package com.sumscope.qpwb.ncd.data.access.webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.qpwb.ncd.data.model.webserver.WebBondRequest;
import com.sumscope.qpwb.ncd.data.model.webserver.WebBondResponse;
import com.sumscope.qpwb.ncd.utils.JsonTool;
import com.sumscope.qpwb.ncd.utils.JsonUtils;
import com.sumscope.qpwb.ncd.web.condition.NcdSubCondition;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.json.Json;

/**
 * webServerAccess
 * Created by mengyang.sun on 2018/01/19
 */
@Component
public class WebBondServerAccess {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${webbond.server.gateway.url}")
    private String webserverUrl;
    @Value("${webbond.server.subscribe.api.name}")
    private String subNcdApiName;
    @Value("${webbond.server.unsubscribe.api.name}")
    private String unSubNcdApiName;
    private final ObjectMapper mapper = JsonTool.createObjectMapper();


    private String getWBApiUrl(String url) {
        return webserverUrl + url;
    }

    public WebBondResponse subscribe(String institutionCodes, NcdSubCondition condition) {
        String url = getWBApiUrl(subNcdApiName);
        return doWebServer(url, institutionCodes, condition);
    }

    public WebBondResponse unSubscribe(String institutionCodes, NcdSubCondition condition) {
        String url = getWBApiUrl(unSubNcdApiName);
        return doWebServer(url, institutionCodes, condition);
    }

    private WebBondResponse doWebServer(String url, String institutionCodes, NcdSubCondition condition) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            String json = generateRequestJson(institutionCodes, condition);
            StringEntity entity = new StringEntity(json);
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);
            if (response == null) {
                return new WebBondResponse();
            }
            ResponseHandler<String> handler = new BasicResponseHandler();
            String ss = handler.handleResponse(response);
            WebBondResponse webBond = JsonUtils.fromJsonToEntity(ss, WebBondResponse.class);
            return webBond;
        } catch (Exception ex) {
            logger.error("Failed to subscribe ncd, err:{}", ex.getMessage());
            return null;
        }
    }

    public String generateRequestJson(String institutionCodes, NcdSubCondition condition) {
        WebBondRequest request = new WebBondRequest();
        request.setToken(condition.getToken());
        request.setBrokerId(condition.getBrokerId());
        request.setInstitutionCodes(institutionCodes);
        request.setAccessToken(condition.getAccessToken());
        request.setTokenClientId(condition.getTokenClientId());
        request.setTokenUsername(condition.getTokenName());
        request.setSession(condition.getSession());
        String json = JsonUtils.toJson(request);
        return json;
    }
}
