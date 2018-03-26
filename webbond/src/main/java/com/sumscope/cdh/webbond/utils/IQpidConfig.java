package com.sumscope.cdh.webbond.utils;

public interface IQpidConfig {
    String getQpidFactory();
    String getQpidAddress();
    int getQpidReconnectCount();
    int getQpidReconnectInterval();
}