package com.sumscope.cdh.webbond.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/1/22.
 */
public class LogTool {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh");

    private LogTool() {}

    // todo: log exception to Monitor System / Notify System
    public static void logException(Exception e, String message) {
        logger.error(message);
        logger.error(ExceptionUtils.getStackTrace(e));
    }
}
