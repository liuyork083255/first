package com.sumscope.qpwb.ncd.data.access.qpwb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.sumscope.qpwb.ncd.data.model.db.Issuers;
import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import com.sumscope.qpwb.ncd.data.model.iam.IssuerInfo;
import com.sumscope.qpwb.ncd.data.model.qpwb.NcdIssuer;
import com.sumscope.qpwb.ncd.data.model.qpwb.QpwbListResult;
import com.sumscope.qpwb.ncd.data.model.qpwb.WebbondResult;
import com.sumscope.qpwb.ncd.data.model.repository.UserContactRepo;
import com.sumscope.service.webbond.common.http.HttpClientUtils;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import com.sumscope.service.webbond.common.web.response.MetaData;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sumscope.service.webbond.common.iam.IamHttpClient.jsonMapper;

/**
 * Created by mengyang.sun on 2018/01/19
 */
@Component
public class QpwbNcdIssuersAccess extends QpwbBaseAccess {
    private String getWBApiUrl(String url) {
        return wbBaseUrl + url;
    }

    @Value("${application.webbond.restful.url}")
    private String wbBaseUrl;
    @Value("${application.webbond.ncdissuers.api.name}")
    private String ncdIssuersApiName;
    @Value("${application.webbond.latest.issuers.api.name}")
    private String latestIssuerApiName;
    @Value("${application.edm.restful.url}")
    private String edmRestfulUrl;
    @Value("${application.edm.issuer.users.api.name}")
    private String usersApiName;
    @Autowired
    private UserContactRepo userContactRepo;
    @Autowired
    public static Map<String, UserContact> cachedUserContactByUserId = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            List<UserContact> userContacts = userContactRepo.findAll();
            cachedUserContactByUserId = userContacts.stream().collect(Collectors.toMap(UserContact::getUserId, Function.identity()));
        } catch (Exception ex) {
            logger.error("Failed to get all user contact, ex:{}", ex.getMessage());
        }
    }

    public List<NcdIssuer> getNcdIssuers(String userId) {
        Assert.hasText(userId, "userId argument can not be null or empty");

        String apiUrl = getWBApiUrl(ncdIssuersApiName);
        Map<String, String> params = new HashedMap();
        params.put("userId", userId);
        try {
            String response = HttpClientUtils.get(apiUrl, params);
            QpwbListResult<NcdIssuer> result = convertToNcdIssuersResult(response, NcdIssuer.class);
            if (result == null) {
                logger.warn("get the ncd issuers is null");
                return new ArrayList<>();
            } else {
                List<NcdIssuer> ncdIssuers = result.getData();
                return CollectionUtils.isEmpty(ncdIssuers) ? new ArrayList<>() : ncdIssuers;
            }

        } catch (HttpException ex) {
            logger.error("Error happened to call api:{}", apiUrl);
            return new ArrayList<>();
        }

    }

    public JSONObject getLatestIssuer(String userId, String issuerCode) {
        Assert.hasText(userId, "userId argument can not be null or empty");

        String apiUrl = getWBApiUrl(String.format(latestIssuerApiName, issuerCode));
        Map<String, String> params = new HashedMap();
        params.put("userId", userId);
        try {
            String response = HttpClientUtils.get(apiUrl, params);
            JSONObject obj = JSONObject.parseObject(response);
            JSONObject meta = obj.getObject("meta", JSONObject.class);
            if (WebbondResult.SUCCESS_ERR_NUM != meta.getInteger("errNum")) {
                logger.warn("Unsuccessful WebbondResult for {} {}: errnum={}, errmsg={}", meta.get("requestMethod"), meta.get("requestURI"), meta.get("errNum").toString(), meta.get("errMsg"));
                return null;
            }
            return obj.getObject("data", JSONObject.class).getObject("businessInfo", JSONObject.class);
        } catch (HttpException ex) {
            logger.error("Error happened to call api:{},err:{}", apiUrl, ex.getMessage());
        }
        return null;
    }

    public IssuerInfo getIssuerInfoByUserId(String userId) {
        IssuerInfo issuerInfo = new IssuerInfo();
        if (cachedUserContactByUserId.containsKey(userId)) {
            UserContact userContact = cachedUserContactByUserId.get(userId);
            issuerInfo.setQq(userContact.getQq());
            issuerInfo.setMobile(userContact.getMobile());
            issuerInfo.setTelephone(userContact.getTelephone());
            issuerInfo.setCompanyCode(userContact.getCompanyCode());
            issuerInfo.setCustomerOrgName(userContact.getCustomerOrgName());
            issuerInfo.setUsername(userContact.getUserName());
        } else {
            issuerInfo = getIssuerInfoByEdm(userId);
        }
        return issuerInfo;
    }

    public IssuerInfo getIssuerInfoByEdm(String userId) {
        IssuerInfo issuerInfo = new IssuerInfo();
        String response = null;
        try {
            response = HttpClientUtils.get(edmRestfulUrl + usersApiName + userId + "/", null);
            logger.info("get issuer info by userId={}, response body:{}", userId, response);
            JSONObject issuerInfoByUserId = JSON.parseObject(response).getJSONObject("data");
            if (CollectionUtils.isEmpty(issuerInfoByUserId)) {
                logger.error("get issuer info is null By userId {}", userId);
                return new IssuerInfo();
            }
            issuerInfo.setCustomerOrgName(issuerInfoByUserId.getString("companyName"));
            issuerInfo.setUsername(issuerInfoByUserId.getString("name"));
            issuerInfo.setCompanyCode(issuerInfoByUserId.getString("companyId"));
            return addAttributeInfo(issuerInfo, userId);
        } catch (HttpException e) {
            logger.error("get issuer info by userId={} error.  response   exception:{}", userId, e.getMessage());
        } catch (Exception e) {
            logger.error("get issuer info by userId={} unknown error.  response body:{}   exception:{}", userId, response, e.getMessage());
        }
        return issuerInfo;
    }

    /**
     * 添加用户 联系方式
     *
     * @param issuerInfo
     * @param userId
     * @return
     */
    private IssuerInfo addAttributeInfo(IssuerInfo issuerInfo, String userId) {
        // 没有则请求restful
        String response = null;
        try {
            response = HttpClientUtils.get(edmRestfulUrl + usersApiName + userId + "/contactinfo/", null);
            JSONObject issuerInfoByUserId = JSON.parseObject(response).getJSONObject("data");
            if (CollectionUtils.isEmpty(issuerInfoByUserId)) {
                logger.error("get issuer info is null By userId {}", userId);
                return issuerInfo;
            }
            issuerInfo.setQq(issuerInfoByUserId.getString("qq"));
            issuerInfo.setMobile(issuerInfoByUserId.getString("mobile"));
            issuerInfo.setTelephone(issuerInfoByUserId.getString("telephone"));
        } catch (Exception e) {
            logger.error("get issuer info by userId={} unknown error.  response body:{}   exception:{}", userId, response, e.getMessage());
        }
        return issuerInfo;
    }

    private <T> QpwbListResult<T> convertToNcdIssuersResult(String response, Class<T> clazz) {
        if (isSuccessResult(response)) {
            JavaType javaType = jsonMapper.createCollectionType(QpwbListResult.class, clazz);
            QpwbListResult<T> result = jsonMapper.fromJson(response, javaType);
            return result;
        } else {
            return null;
        }
    }

    private boolean isSuccessResult(String response) {
        if (!StringUtils.hasText(response)) {
            logger.warn("the response is null or empty");
            return false;
        } else {
            WebbondResult result = jsonMapper.fromJson(response, WebbondResult.class);
            if (result != null && result.getMeta() != null) {
                if (WebbondResult.SUCCESS_ERR_NUM == result.getMeta().getErrNum()) {
                    return true;
                } else {
                    MetaData meta = result.getMeta();
                    logger.warn(String.format("Unsuccessful WebbondResult for %s %s: errnum=%d, errmsg=%s", meta.getRequestMethod(), meta.getRequestURI(), meta.getErrNum(), meta.getErrMsg()));
                    return false;
                }
            } else {
                logger.warn("Invalid WebbondResult");
                return false;
            }
        }
    }

}
