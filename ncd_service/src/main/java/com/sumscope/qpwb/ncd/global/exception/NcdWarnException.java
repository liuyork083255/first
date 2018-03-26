package com.sumscope.qpwb.ncd.global.exception;

public class NcdWarnException extends RuntimeException {
    private NcdWarnException(String msg){
        super(msg);
    }

    public static NcdWarnException warn(String msg){
        return new NcdWarnException(msg);
    }
}
