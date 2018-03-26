package com.sumscope.cdh.webbond.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeMeasure implements AutoCloseable {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh");

    final long _time;
    final String _title;
    public TimeMeasure(String title) {
        _time = System.currentTimeMillis();
        _title = title;
    }
    @Override
    public void close() throws Exception {
        logger.info(_title + ": " + (System.currentTimeMillis() - _time) + " ms");
    }
}