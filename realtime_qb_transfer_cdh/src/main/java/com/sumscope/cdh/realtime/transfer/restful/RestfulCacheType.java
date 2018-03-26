package com.sumscope.cdh.realtime.transfer.restful;

/**
 * Created by liu.yang on 2017/12/15.
 */
public enum RestfulCacheType {

    WRM("WRM"),CDC("CDC"),CSI("CSI"),INT("INT"),HI("HI"),WB("WE");
    private final String value;

    RestfulCacheType(String type){
        this.value = type;
    }
    public String getValue(){
        return this.value;
    }
}
