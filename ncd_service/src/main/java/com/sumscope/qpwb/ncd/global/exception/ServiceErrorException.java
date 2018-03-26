package com.sumscope.qpwb.ncd.global.exception;

/**
 * Created by mengyang.sun on 2018/01/19
 */
public class ServiceErrorException extends NcdExcption {

    public ServiceErrorException(String message) {
        super(message);
    }

    public ServiceErrorException(String message, Throwable t) {
        super(message, t);
    }

}

