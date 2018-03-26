package com.sumscope.qpwb.ncd.global.constants;

import java.math.BigDecimal;
import java.util.List;

public class NcdConstants {
    private NcdConstants() {}
    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_YYYY_MM_DD_POINT = "yyyy.MM.dd";
    public static final String DATE_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HMS = "HH:mm:ss";
    public static final String DATE_YMD_HMS_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_MYD_HM = "MM d yyyy h:mma";
    public static final int RESTFUL_PAGE_SIZE = 5000;
    public static final int RESTFUL_DATASOURCE_ID = 100;
    public static final String MARKET_CIB = "CIB";
    public static final String MARKET_SSE = "SSE";
    public static final String MARKET_SZE = "SZE";
    public static final String API_HOLIDAY_KEY = "holiday_date";
    public static final String LIMIT_1M = "1M";
    public static final String LIMIT_3M = "3M";
    public static final String LIMIT_6M = "6M";
    public static final String LIMIT_9M = "9M";
    public static final String LIMIT_1Y = "1Y";
    public static final String LIMIT_1M_VALUE = "1";
    public static final String LIMIT_3M_VALUE = "2";
    public static final String LIMIT_6M_VALUE = "3";
    public static final String LIMIT_9M_VALUE = "4";
    public static final String LIMIT_1Y_VALUE = "5";

    public static final String NON_BANK_TEXT= "非银";
    public static final String NON_BANK_VALUE = "1";
    public static final String AVAILABLE_TEXT = "询满";
    public static final String FLOAT_RATE_TEXT = "浮息";
    public static final String FLOAT_RATE_OTHER = "其它";
    public static final String FIX_RATE_VALUE = "0"; //固息
    public static final String RECOMMEND = "1";
    public static final String AVAILABLE = "1"; //可用：不是询满
    public static final int ONE_HANDRED_MILLION_DIGIT = 0; //亿的单位
    public static final String STATE_OWN_RATE = "国有/股份制";
    public static final String ALL = "ALL";
    public static final String OTHER = "OTD";
    public static final String UNAVAILABLE = "0"; //不可用

    public static final String MATRIX_CON_CATEGORY_FILTER = "filter";
    public static final String MATRIX_CON_CATEGORY_FAVOURITE = "favourite";
    public static final String MATRIX_CON_CATEGORY_ALL = "all";
    public static final String TOTAL_ASSETS = "总资产";
    public static final String CLEAN_ASSETS = "净资产";
    public static final String INCOME = "营收";
    public static final String NET_MARGIN = "净利润";
    public static final String LOAN_DEPOSIT_RATIO = "存贷比";
    public static final String CCAR = "核心资本充足率";
    public static final String REJECT_RATIO = "不良率";
    public static final String INSTITUTION = "机构类型";
    public static final String RATE = "评级";
    public static final String PLAN_AMOUNT_TEXT = "同业存单计划发行额度";
    public static final String INDEX_DATE = "Index_Date";

    public static final String BLANK= "";
    public static final String STR_ZEOR = "0.0";


    // 外部API的错误类型
    public final static int IAM_ERRNUM_USER_NOT_FOUND = 110001;
    public final static String BAM_BIZ_AUTH_CHANGE = "64001";

    public static final String NCD_TYPE_ALL = "all";
    public static final String NCD_TYPE_FAVOURITE = "favourite";
    public static final String NCD_TYPE_FILTER = "filter";
    public static final String ANY= "*";


}
