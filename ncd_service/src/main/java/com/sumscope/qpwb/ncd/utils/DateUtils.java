package com.sumscope.qpwb.ncd.utils;

import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import org.apache.commons.net.ntp.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Created by mengyang.sun on 2018/1/16.
 */
public class DateUtils {
    private DateUtils() {
    }

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static SimpleDateFormat sdfZone = new SimpleDateFormat(NcdConstants.DATE_YMD_HMS_ZONE);
    private static SimpleDateFormat sdfMYD = new SimpleDateFormat(NcdConstants.DATE_MYD_HM, Locale.ENGLISH);
    private static SimpleDateFormat hms = new SimpleDateFormat(NcdConstants.DATE_HMS);

    /**
     * 返回当天的日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当天的日期，返回yyyyMMdd格式
     *
     * @return
     */
    public static String getLocalDateString(String pattern) {
        return formatter(getLocalDate(), pattern);
    }

    /**
     * 格式化LocalDate
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String formatter(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDate天数计算
     *
     * @param days
     * @return
     */
    public static LocalDate plusDays(int days) {
        return getLocalDate().plusDays(days);
    }

    public static LocalDate plusDays(LocalDate localDate, int days) {
        return localDate.plusDays(days);
    }

    public static LocalDate minusYears(LocalDate localDate, int years) {
        return localDate.minusYears(years);
    }

    /**
     * 把字符串转为LocalDate对象
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static LocalDate parseLocalDate(String date, String datePattern) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
        } catch (DateTimeParseException e) {
            logger.warn(String.format("%s cannot converter to localDate of pattern[%s]", date, datePattern));
        }
        return localDate;
    }

    public static final Date getCurrentDate() {
        return new Date(new java.util.Date().getTime());
    }

    public static Time convertToSqlTime(String strDate, String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Time time = null;
        try {
            time = new Time(sdf.parse(strDate).getTime());
        } catch (ParseException e) {
            logger.warn(String.format("%s cannot converter to localDate ", strDate));
        }
        return time;
    }

    public static Date convertToSqlDate(String strDate, String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date localDate = null;
        try {
            localDate = new Date(sdf.parse(strDate).getTime());
        } catch (ParseException e) {
            logger.warn(String.format("%s cannot converter to localDate ", strDate));
        }
        return localDate;
    }

    public static final String convertToDatetimeByDate(Date date) {
        String format = null;
        try {
            format = new SimpleDateFormat(NcdConstants.DATE_YMD_HMS).format(date);
        } catch (Exception e) {
            logger.error("sql Date convert to [yyyy-MM-dd HH:mm:ss] error.   exception={}", e.getMessage());
            return format;
        }
        return format;
    }

    public static String convertSqlDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(NcdConstants.DATE_YYYY_MM_DD);
        String strDate = null;
        try {
            strDate = sdf.format(date);
        } catch (Exception e) {
            logger.warn(String.format("%s cannot converter to String ", strDate));
        }
        return strDate;
    }

    public static String convertTimeToString(Time time) {
        if (time == null) {
            return "";
        }
        return time.toString();
    }

    public static Timestamp convertToTimestampByMq(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            return new Timestamp(sdfMYD.parse(date.trim().replaceAll(" +", " ")).getTime());
        } catch (Exception ex) {
            logger.error("Failed to convert {} to Timestamp, err:{}", date, ex.getMessage());
            return null;
        }
    }

    public static Time convertToTimeByMq(String date) {
        Timestamp ts = convertToTimestampByMq(date);
        if (ts == null) {
            return null;
        }
        return new Time(ts.getTime());
    }

    public static Timestamp convertToTimestamp(String date) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            return new Timestamp(sdfZone.parse(date).getTime());
        } catch (Exception e) {
            logger.error("Failed to convert {} to Timestamp,err:{}", date, e.getMessage());
            return null;
        }
    }

    public static String getTimeByTimestampStr(String strDate) {
        Timestamp date = convertToTimestampByMq(strDate);
        if (date != null) {
            return hms.format(date);
        }
        return null;
    }
}

