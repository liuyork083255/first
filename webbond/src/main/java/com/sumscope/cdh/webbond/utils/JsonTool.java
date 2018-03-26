package com.sumscope.cdh.webbond.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonTool {
    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }
    public static String nodeToString(ObjectMapper mapper, TreeNode nd) throws JsonProcessingException {
        return mapper.writeValueAsString(mapper.treeToValue(nd, Object.class));
    }
    public static void nodeToFile(ObjectMapper mapper, TreeNode nd, String file) throws IOException {
        mapper.writeValue(new File(file), mapper.treeToValue(nd, Object.class));
    }
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}