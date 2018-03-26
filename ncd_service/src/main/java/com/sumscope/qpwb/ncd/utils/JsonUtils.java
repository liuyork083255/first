package com.sumscope.qpwb.ncd.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.sumscope.service.webbond.common.utils.JsonMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static String toJson(Object object) {
        String jsonString = JsonMapper.alwaysMapper().toJson(object);
        return jsonString;
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        JavaType javaType = JsonMapper.alwaysMapper().createCollectionType(ArrayList.class, clazz);
        List<T> result = JsonMapper.alwaysMapper().fromJson(json, javaType);
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public static <T> T fromJsonToEntity(String json, Class<T> clazz) {
        T result = JsonMapper.alwaysMapper().fromJson(json, clazz);
        return result;
    }
}
