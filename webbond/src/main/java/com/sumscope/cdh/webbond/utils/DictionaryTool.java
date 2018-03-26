package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Consumer;

public class DictionaryTool {
    private DictionaryTool() {}

    public static String getLevel1FromLevel2(JsonNode staticDictionaryRootNode, String key, String level2value, String defValue) throws IOException {
        ObjectNode msgNode = (ObjectNode) staticDictionaryRootNode.path("msg");
        ArrayNode subTypeNodes = (ArrayNode) msgNode.path(key);
        for(JsonNode levelOneNode : subTypeNodes) {
            for(JsonNode levelTwoNode : levelOneNode.path("childNodes")) {
                if (levelTwoNode.path("code").asText().equals(level2value))
                    return levelOneNode.path("code").asText();
            }
        }
        return defValue;
    }

    static ArrayNode getOrCreateField(JsonNode staticDictionaryRootNode, String fieldKey) {
        ObjectNode msgNode = (ObjectNode) staticDictionaryRootNode.path("msg");
        return msgNode.withArray(fieldKey);
    }

    public static void generateDictionary(ICdhRestfulConfig config, ObjectMapper mapper, ObjectNode dictionaryRootNode) throws IOException {
        CdhRestful.query(config, "application.restful.api.provinces", new DictionarySimpleConsumer(dictionaryRootNode, "province"));
        CdhRestful.query(config, "application.restful.api.institutions", new DictionarySimpleConsumer(dictionaryRootNode, "issuer"));

        CdhRestful.query(config, "application.restful.api.level_1_sw_sectors", new DictionarySimpleConsumer(dictionaryRootNode, "sector"));
        CdhRestful.query(config, "application.restful.api.level_2_sw_sectors", new SwSectorLevelTwoConsumer(
                getOrCreateField(dictionaryRootNode, "sector")));

        int thisYear = LocalDate.now().getYear();
        for (int year=2001; year<=thisYear; year++) {
            ArrayNode nodes = getOrCreateField(dictionaryRootNode, "issueYear");
            JsonNode yearNode = mapper.readTree(String.format("{\"code\": \"%s\",\"name\": \"%s\"}", year, year));
            nodes.add(yearNode);
        }
    }
}

final class SwSectorLevelTwoConsumer implements Consumer<ArrayNode> {
    private final ArrayNode levelOneNodes;

    public SwSectorLevelTwoConsumer(ArrayNode levelOneNodes) {
        this.levelOneNodes = levelOneNodes;
    }

    private JsonNode getFather(String code) throws Exception {
        for (JsonNode nd: levelOneNodes) {
            if (nd.path("code").asText().equals(code))
                return nd;
        }
        throw new Exception(String.format("parent sector code %s not found", code));
    }

    @Override
    public void accept(ArrayNode nodes) {
        try {
            for (JsonNode nd : nodes) {
                String fatherCode = nd.path("fatherCode").asText();
                JsonNode father = getFather(fatherCode);
                ((ArrayNode)father.withArray("childNodes")).add(nd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

final class DictionarySimpleConsumer implements Consumer<ArrayNode> {
    private final JsonNode dictionaryRootNode;
    private final String fieldKey;
    public DictionarySimpleConsumer(JsonNode dictionaryRootNode, String fieldKey) {
        this.dictionaryRootNode = dictionaryRootNode;
        this.fieldKey = fieldKey;
    }
    @Override
    public void accept(ArrayNode message) {
        try {
            ArrayNode nodes = DictionaryTool.getOrCreateField(dictionaryRootNode, fieldKey);
            nodes.addAll(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
