package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.webbond.model.Bond;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BondsJsonTool {
    private static JavaType getBondsListType(ObjectMapper mapper) {
        return JsonTool.getCollectionType(mapper, List.class, Bond.class);
    }
    public static List<Bond> deserializeBondsFromFile(String file, ObjectMapper mapper) throws IOException {
        return mapper.readValue(new File(file), getBondsListType(mapper));
    }
}