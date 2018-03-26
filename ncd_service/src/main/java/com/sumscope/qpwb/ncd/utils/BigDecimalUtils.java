package com.sumscope.qpwb.ncd.utils;

import com.sumscope.service.webbond.common.utils.DecimalPrecision;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
    private BigDecimalUtils(){}

    public static BigDecimal toDecimal(BigDecimal decimal, int digit){
        if(decimal == null){
            return null;
        }
        else{
            return decimal.setScale(digit, RoundingMode.HALF_UP);
        }
    }

    public static BigDecimal toDecimal(String strDecimal, int digit){
        if (StringUtils.isEmpty(strDecimal)){
            return  null;
        }else {
            return new BigDecimal(strDecimal).setScale(digit, RoundingMode.HALF_UP);
        }
    }

    public static Double toDouble(BigDecimal decimal){
        if(decimal == null){
            return 0.0;
        }
        else{
            return decimal.doubleValue();
        }
    }

    public static String toString(BigDecimal decimal){
        if (decimal == null){
            return null;
        }else {
            return  decimal.toString();
        }
    }

    public static String toString(BigDecimal decimal, int digit) {
        if (decimal == null){
            return null;
        }else {
            return  DecimalPrecision.formatter(decimal.toString(), digit, null);
        }
    }

    public static boolean isZero(BigDecimal decimal){
        return decimal == null || decimal.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isZeroStr(String decimal){
        if(StringUtils.isEmpty(decimal) ||
                org.apache.commons.lang3.StringUtils.equals(decimal,"0.0000") ||
                org.apache.commons.lang3.StringUtils.equals(decimal,"0"))
            return true;
        return false;
    }

    //是否大于等于
    public static boolean isGE(BigDecimal value1, BigDecimal value2){
        if (value1 == null || value2 == null){
            return false;
        }
        return value1.compareTo(value2) >=0;
    }
}
