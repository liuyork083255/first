package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CdhRestful {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh");

    private CdhRestful() {}

    private static final int MAX_CODES_COUNT = 1000;

    private static String generateRestfulRequest(ICdhRestfulConfig config, ObjectMapper mapper, String key, int startPage, List<String> codes) throws IOException {
        ObjectNode nd = (ObjectNode) mapper.readTree(config.getRestfulApiJson(key));
        if (!nd.has("User"))
            nd.put("User", config.getRestfulUser());
        if (!nd.has("Password"))
            nd.put("Password", config.getRestfulPassword());
        if (!nd.has("StartPage"))
            nd.put("StartPage", startPage);
        if (!nd.has("PageSize"))
            nd.put("PageSize", config.getRestfulPageSize());
        if (!nd.has("ApiVersion"))
            nd.put("ApiVersion", "N");
        if (!StringTool.isEmptyList(codes) && codes.size() < MAX_CODES_COUNT) // warning: if too many codes, just drop it
            for (String code : codes)
                nd.withArray("codes").add(code);
        return nd.toString();
    }

    public static void query(ICdhRestfulConfig config, String requestKey, Consumer<ArrayNode> consumer) throws IOException {
        queryWithCodes(config, requestKey, null, consumer);
    }

    public static void queryWithCodes(ICdhRestfulConfig config, String requestKey, List<String> codes, Consumer<ArrayNode> consumer) throws IOException {
        RestfulClient restfulClient = new RestfulClient(config.getRestfulHost(), config.getRestfulPort());

        int startPage = 1;
        int pageSize = config.getRestfulPageSize();
        ObjectMapper mapper = JsonTool.createObjectMapper();

        while (true) {
            logger.info(String.format("Loading %s, start page: %s", requestKey, startPage));
            String requestBody = generateRestfulRequest(config, mapper, requestKey, startPage, codes);
            JsonNode resultTable = restfulClient.runApi(requestBody);
            ArrayNode resultTableArray = (ArrayNode) resultTable;
            if (resultTableArray.size() == 0)
                return;
            consumer.accept(resultTableArray); // process result here
            if (resultTableArray.size() < pageSize)
                return;
            startPage++;
        }
    }

    public static Map<String, List<String>> loadRestfulObjects(
            ICdhRestfulConfig config, String restfulApiKey,
            String objectKey, List<String> objectPropertyKeys) throws IOException {

        Map<String, List<String>> objects = new HashMap<>();
        CdhRestful.query(config, restfulApiKey, (ArrayNode jsonNodes) -> {
            for (JsonNode nd : jsonNodes) {
                objects.put(nd.path(objectKey).asText(),
                        objectPropertyKeys.stream().map(objPropKey -> nd.path(objPropKey).asText()).collect(Collectors.toList()));
            }
        });
        return objects;
    }
}