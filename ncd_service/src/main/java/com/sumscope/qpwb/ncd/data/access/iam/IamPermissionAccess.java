package com.sumscope.qpwb.ncd.data.access.iam;

import com.alibaba.fastjson.JSONObject;
import com.sumscope.qpwb.ncd.data.model.iam.IamUserAuthz;
import com.sumscope.qpwb.ncd.data.model.qpwb.WebbondResult;
import com.sumscope.qpwb.ncd.global.constants.RedisConstants;
import com.sumscope.qpwb.ncd.global.exception.NcdExceptionModel;
import com.sumscope.qpwb.ncd.global.exception.NcdExcption;
import com.sumscope.qpwb.ncd.global.exception.ServiceErrorException;
import com.sumscope.qpwb.ncd.global.exception.UserNotFoundException;
import com.sumscope.qpwb.ncd.utils.CommonUtil;
import com.sumscope.qpwb.ncd.utils.JsonUtils;
import com.sumscope.service.webbond.common.http.HttpClientUtils;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import com.sumscope.service.webbond.common.iam.IamHttpClient;
import com.sumscope.service.webbond.common.iam.response.IamListResult;
import com.sumscope.service.webbond.common.iam.response.IamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static com.sumscope.qpwb.ncd.global.constants.NcdConstants.IAM_ERRNUM_USER_NOT_FOUND;

@Component
public class IamPermissionAccess {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final static int IAM_SUCCESS = 0;
    private final static String BamApiGetUserAuthz = "/bam/api/v1/bizcompanies/businesses/userauthz";
    private final static String EmApiGetUserPerm = "/em/api/v1/functions/users/%s/perm";
    @Value("${iam.bam.server}")
    private String bamBaseUrl;
    @Value("${application.em.restful.url}")
    private String emBaseUrl;
    @Value("${iam.bam.server.user.auth}")
    private String bamUserAuthApiName;
    @Value("${application.bam.biz.type.code}")
    private String loginBizTypeCode;
    @Value("${application.em.web.biz.type.code}")
    private String webBizTypeCode;
    @Autowired
    CommonUtil commonUtil;

    @Value("${spring.redis.timeout}")
    private long timeout;
    public static Map<String, Collection<String>> cachedAuthBrokerMap = new HashMap<>();

    public String getLoginBizTypeCode() {
        return loginBizTypeCode;
    }

    public void setLoginBizTypeCode(String loginBizTypeCode) {
        this.loginBizTypeCode = loginBizTypeCode;
    }

    private String getBamApiUrl(String url) {
        return bamBaseUrl + url;
    }

    private String getEmApiUrl(String url) {
        return emBaseUrl + url;
    }

    public Set<String> getBrokersByUserAuth(String userId) {
        Set<String> brokers = new HashSet<>();
        try {
            String url = getBamApiUrl(BamApiGetUserAuthz);
            if (cachedAuthBrokerMap.containsKey(userId)){
                return (Set<String>) cachedAuthBrokerMap.get(userId);
            }
            List<IamUserAuthz> authz = getUserBusinessAuthz(userId, url, loginBizTypeCode);

            brokers = authz.stream().filter(
                    x -> x.getAuthzValue() == 1 && commonUtil.getBrokerIds().contains(x.getBizCompanyId())).map(
                    IamUserAuthz::getBizCompanyId).collect(Collectors.toSet());
            cachedAuthBrokerMap.put(userId, brokers);
        } catch (Exception ex) {
            logger.error("Failed to get brokers by user");
        }
        return brokers;
    }

    public boolean isAuthByUserId(String userId)  {
        Assert.hasText(userId, "userId argument can not be null or empty");
        List<String> brokers = new ArrayList<>();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("functionCode", webBizTypeCode);
            String url = getEmApiUrl(String.format(EmApiGetUserPerm, userId));
            String response = HttpClientUtils.get(url, params);
            JSONObject obj = JSONObject.parseObject(response);
            JSONObject meta = obj.getObject("meta", JSONObject.class);
            if (WebbondResult.SUCCESS_ERR_NUM != meta.getInteger("errNum")) {
                logger.warn("Unsuccessful WebbondResult for {} {}: errnum={}, errmsg={}", meta.get("requestMethod"), meta.get("requestURI"), meta.get("errNum").toString(), meta.get("errMsg"));
                return false;
            }
            return "1".equals(obj.getObject("data", JSONObject.class).get("permValue"));
        } catch (Exception ex) {
            logger.error("Failed to get brokers by user, err:{}", ex.getMessage());
            throw NcdExceptionModel.ApiNoAvailableVersion;
        }
    }

    /**
     * 获取用户业务的授权
     *
     * @param userId
     * @return
     */
    public List<IamUserAuthz> getUserBusinessAuthz(String userId, String apiUrl, String bizTypeCode) throws NcdExcption {
        Assert.hasText(userId, "userId argument can not be null or empty");

        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bizTypeCode", bizTypeCode);

        IamListResult<IamUserAuthz> result = null;
        try {
            result = IamHttpClient.getListResult(apiUrl, params, IamUserAuthz.class);
        } catch (HttpException ex) {
            throw new ServiceErrorException("Error happened when calling " + apiUrl + ": " + ex.getMessage());
        }

        checkIamResult(result, userId, apiUrl);
        return result.getData();
    }

    private void checkIamResult(IamResult result, String userId, String apiUrl) throws NcdExcption {
        if (result == null) {
            throw new ServiceErrorException("Null returned when calling " + apiUrl);
        } else if (result.getMeta().getErrNum() != IAM_SUCCESS) {
            if (result.getMeta().getErrNum() == IAM_ERRNUM_USER_NOT_FOUND) {
                throw new UserNotFoundException(String.format("User(id=%s) not found when calling " + apiUrl, userId));
            } else {
                throw new ServiceErrorException("Error returned when calling " + apiUrl + ": " + result.getMeta().getErrMsg());
            }
        }
    }
}
