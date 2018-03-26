package com.sumscope.qpwb.ncd.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumscope.qpwb.ncd.data.access.iam.IamPermissionAccess;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BamAuthChangeHandler {
    private final static Logger logger = LoggerFactory.getLogger(BamAuthChangeHandler.class);
    @Autowired
    IamPermissionAccess iamPermissionAccess;

    public void handleBamAuthChange(String msg) {
        try {
            JSONObject obj = JSONObject.parseObject(msg);
            String changeType = (String) obj.get("Type");
            if (NcdConstants.BAM_BIZ_AUTH_CHANGE.equals(changeType)) {
                JSONArray authzs = obj.getJSONObject("BAMBizAuthzChanges").getJSONArray("UserBizAuthzs");
                for (Object objAuth : authzs) {
                    JSONObject auth = (JSONObject) objAuth;
                    if (!iamPermissionAccess.getLoginBizTypeCode().equals(auth.getString("BizTypeCode"))){
                        continue;
                    }
                    String userId = (String) auth.get("UserId");
                    int authz = (Integer) auth.get("Authz");
                    String brokerId = (String) auth.get("BizCompanyId");
                    Set<String> brokers = iamPermissionAccess.getBrokersByUserAuth(userId);
                    if (authz != 1) {
                        brokers.remove(brokerId);
                    } else {
                        if (!brokers.contains(brokerId)) {
                            brokers.add(brokerId);
                        }
                    }
                    IamPermissionAccess.cachedAuthBrokerMap.put(userId, brokers);
                }

            } else {
                logger.error("the user auth change type {} not {}", changeType, NcdConstants.BAM_BIZ_AUTH_CHANGE);
            }
        } catch (Exception ex) {
            logger.error("Failed to handle the bam auth change from mq msg, err:{}", ex.getMessage());
        }
    }
}
