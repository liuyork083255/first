package com.sumscope.qpwb.ncd.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class JsonTool {
    private JsonTool() {}
    private static final Logger logger = LoggerFactory.getLogger(JsonTool.class);

    public static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper createObjectMapper() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        mapper.setDateFormat(dateFormat);
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

    public static byte[] writeValueAsBytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public static String writeValueAsString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(jsonStr, valueTypeRef);
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
    }
}