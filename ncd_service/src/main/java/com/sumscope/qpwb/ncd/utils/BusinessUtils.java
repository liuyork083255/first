package com.sumscope.qpwb.ncd.utils;

import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.RateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessUtils {
    private static final Logger logger = LoggerFactory.getLogger(BusinessUtils.class);

    public static List<BigDecimal> convertToDecimalList(String value) {
        List<BigDecimal> results = new ArrayList();
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Arrays.asList(value.split(":")).forEach(val -> {
            results.add(new BigDecimal(val));
        });
        if (results.size() < 2) {
            logger.error("the param value {} is not match the A:B", value);
            return null;
        }
        return results;
    }

    public static List<String> convertToStringList(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Arrays.asList(value.split(":"));
    }

    public static boolean inRange(List<BigDecimal> ranges, String strValue) {
        // 如果ncd没有录入这个信息就返回true
        if (StringUtils.isEmpty(strValue)) {
            return true;
        }
        if (ranges.size() < 2) {
            logger.error("the param ranges's size lt 2");
            return false;
        }
        BigDecimal value;
        try {
            value = new BigDecimal(strValue);
        } catch (Exception ex) {
            value = BigDecimal.ZERO;
            //ncd没有录入这个信息
            logger.warn("Failed to convert {} to BigDecimal while the ncd issuer not input the value, err: {}", strValue, ex.getMessage());
        }
        BigDecimal startRange = ranges.get(0);
        BigDecimal endRange = ranges.get(1);
        return value.compareTo(startRange) >= 0 && (BigDecimalUtils.isZero(endRange) || value.compareTo(endRange) <= 0);
    }

    //是否询满 false:询满
    public static boolean isAvailable(Byte value) {
        return value != null && Byte.valueOf(NcdConstants.AVAILABLE).compareTo(value) == 0;
    }

    public static boolean matchRate(List<String> rates, String rate) {
        if (rate == null) {
            return false;
        }
        //当选择的评级是AAA时AAA+的也查询出来
        if (NcdConstants.STATE_OWN_RATE.equals(rate)) {
            rate = "AAA";
        }
        //浮息只有【不限】才能查询到
        if (NcdConstants.FLOAT_RATE_TEXT.equals(rate)) {
            if (rates.contains(NcdConstants.ALL)) {
                return true;
            }
            return false;
        }
        if ((rates.contains(NcdConstants.ALL) || rates.contains(rate)) || (rates.contains(NcdConstants.OTHER) && !RateEnum.getFilterList().contains(rate))) {
            return true;
        }
        return false;
    }

    public static Long convertToLong(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return new Long(value);
    }


}
