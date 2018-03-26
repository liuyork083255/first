package com.sumscope.cdh.webbond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCLogger {

	private static Logger logger = LoggerFactory.getLogger(MDCLogger.class);

	public static final String LOGTYPE = "LOGTYPE";
	public static final String USERNAME = "USERNAME";
	public static final String APINAME = "APINAME";
	public static final String MSG_DETAIL = "MSG_DETAIL";
	public static final String SQL_ROWCOUNT = "SQL_ROWCOUNT";
	public static final String SQL_COLCOUNT = "SQL_COLCOUNT";
	public static final String SQL_TAKETIME = "SQL_TAKETIME";
	public static final String DATA_SOURCE_ID = "DATA_SOURCE_ID";

	public static Logger getLogger() {
		return logger;
	}

	public static void clear() {
		MDC.clear();
		MDC.put(SQL_ROWCOUNT, "0");
		MDC.put(SQL_COLCOUNT, "0");
		MDC.put(SQL_TAKETIME, "0");
	}

	public static void set(String key, String value) {
		MDC.put(key, value);
	}

	public static void main(String[] args) {
		MDCLogger.clear();
		MDCLogger.set(LOGTYPE, "logtype");
		MDCLogger.set(USERNAME, "username");
		MDCLogger.getLogger().warn("testmsg...");
	}

}
