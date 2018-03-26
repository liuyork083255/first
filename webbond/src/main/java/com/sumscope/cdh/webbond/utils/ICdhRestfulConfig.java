package com.sumscope.cdh.webbond.utils;

public interface ICdhRestfulConfig {
    String getRestfulHost();
    int getRestfulPort();
    String getRestfulUser();
    String getRestfulPassword();
    int getRestfulPageSize();
    String getRestfulApiJson(String propertyKey);
}
