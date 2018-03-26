package com.sumscope.cdh.webbond.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sumscope.cdh.webbond.model.UserAccount;

public class UserAccountsTool {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    public static Map<String, UserAccount> generateUserAccounts(ICdhRestfulConfig restfulConfig) throws IOException {

        logger.info("Loading broker1 authorized users...");
        Map<String, List<String>> broker1 = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.broker1_auth", "account_id", Arrays.asList("business_allow_id"));

        logger.info("Loading broker2 authorized users...");
        Map<String, List<String>> broker2 = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.broker2_auth", "account_id", Arrays.asList("business_allow_id"));

        logger.info("Loading broker3 authorized users...");
        Map<String, List<String>> broker3 = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.broker3_auth", "account_id", Arrays.asList("business_allow_id"));

        logger.info("Loading broker4 authorized users...");
        Map<String, List<String>> broker4 = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.broker4_auth", "account_id", Arrays.asList("business_allow_id"));

        logger.info("Loading broker5 authorized users...");
        Map<String, List<String>> broker5 = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.broker5_auth", "account_id", Arrays.asList("business_allow_id"));

        Set<String> brokerBlacklist = new HashSet<>();
        logger.info("Loading company business blacklist...");
        CdhRestful.query(restfulConfig, "application.restful.api.company_business", (ArrayNode jsonNodes) -> {
            for (JsonNode nd : jsonNodes) {
                brokerBlacklist.add(nd.path("company_id").asText() + "_" + nd.path("business_allow_id").asText());
            }
        });

        logger.info("Loading cdc authorized users...");
        Map<String, List<String>> cdc = CdhRestful.loadRestfulObjects(restfulConfig, "application.restful.api.cdc_auth",
                "account_id", Arrays.asList("account_id"));

        logger.info("Loading account ids...");
        Map<String, List<String>> accountIds = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.user_account", "username", Arrays.asList("id", "display_name", "company_id"));

        int ACCOUNT_ID_INDEX = 0;
        int ACCOUNT_DISPLAY_NAME_INDEX = 1;
        int ACCOUNT_COMPANY_ID_INDEX = 2;
        int BROKER_BUSINESS_ID_INDEX = 0;

        return accountIds.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        kv -> kv.getKey(),
                        kv -> {
                            UserAccount userAccount = new UserAccount();
                            userAccount.userAccountId = kv.getValue().get(ACCOUNT_ID_INDEX);
                            userAccount.userAccountName = kv.getKey();
                            userAccount.authedBrokers = new ArrayList<String>();
                            userAccount.displayName = kv.getValue().get(ACCOUNT_DISPLAY_NAME_INDEX);
                            String companyId = kv.getValue().get(ACCOUNT_COMPANY_ID_INDEX);

                            List<String> brokerProperties = null;
                            brokerProperties = broker1.get(userAccount.userAccountId);
                            if (brokerProperties != null) {
                                String blacklistKey = companyId + "_" + brokerProperties.get(BROKER_BUSINESS_ID_INDEX);
                                if (!brokerBlacklist.contains(blacklistKey)) {
                                    userAccount.authedBrokers.add("1");
                                }
                            }
                            brokerProperties = broker2.get(userAccount.userAccountId);
                            if (brokerProperties != null) {
                                String blacklistKey = companyId + "_" + brokerProperties.get(BROKER_BUSINESS_ID_INDEX);
                                if (!brokerBlacklist.contains(blacklistKey)) {
                                    userAccount.authedBrokers.add("2");
                                }
                            }
                            brokerProperties = broker3.get(userAccount.userAccountId);
                            if (brokerProperties != null) {
                                String blacklistKey = companyId + "_" + brokerProperties.get(BROKER_BUSINESS_ID_INDEX);
                                if (!brokerBlacklist.contains(blacklistKey)) {
                                    userAccount.authedBrokers.add("3");
                                }
                            }
                            brokerProperties = broker4.get(userAccount.userAccountId);
                            if (brokerProperties != null) {
                                String blacklistKey = companyId + "_" + brokerProperties.get(BROKER_BUSINESS_ID_INDEX);
                                if (!brokerBlacklist.contains(blacklistKey)) {
                                    userAccount.authedBrokers.add("4");
                                }
                            }
                            brokerProperties = broker5.get(userAccount.userAccountId);
                            if (brokerProperties != null) {
                                String blacklistKey = companyId + "_" + brokerProperties.get(BROKER_BUSINESS_ID_INDEX);
                                if (!brokerBlacklist.contains(blacklistKey)) {
                                    userAccount.authedBrokers.add("5");
                                }
                            }
                            userAccount.cdcAuthed = cdc.containsKey(kv.getValue().get(ACCOUNT_ID_INDEX));
                            return userAccount;
                        }
                ));
    }

}
