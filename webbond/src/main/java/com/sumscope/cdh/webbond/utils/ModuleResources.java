package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;

public class ModuleResources {
    public static ObjectNode getStaticDictionary(ObjectMapper mapper) throws IOException {
        InputStream input = ModuleResources.class.getClassLoader().getResourceAsStream("dictionary_static.json");
        return (ObjectNode) mapper.readTree(input);
    }
}
