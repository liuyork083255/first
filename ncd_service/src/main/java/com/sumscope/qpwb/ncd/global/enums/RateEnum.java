package com.sumscope.qpwb.ncd.global.enums;

import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum RateEnum {
    AAAP_RATE(NcdConstants.STATE_OWN_RATE, "001"),
    AAA_RATE("AAA", "002"),
    AAP_RATE("AA+", "003"),
    AA_RATE("AA", "004"),
    AAS_RATE("AA-", "005"),
    AP_RATE("A+", "006"),
    A_RATE("A", "007"),
    AS_RATE("A-", "008"),
    FLOAT_RATE(NcdConstants.FLOAT_RATE_TEXT, "009"),
    NULL_RATE(NcdConstants.FLOAT_RATE_OTHER, "010"),;

    RateEnum(String rate, String sortLevel) {
        this.sortLevel = sortLevel;
        this.rate = rate;
    }

    private String sortLevel;
    private String rate;

    public String getSortLevel() {
        return sortLevel;
    }

    public void setSortLevel(String sortLevel) {
        this.sortLevel = sortLevel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public static RateEnum rateEnum(String rating) {
        if (!StringUtils.hasText(rating)) {
            return RateEnum.NULL_RATE;
        }

        RateEnum[] ratingEnums = RateEnum.values();
        if (ArrayUtils.isEmpty(ratingEnums)) {
            return RateEnum.NULL_RATE;
        }

        for (RateEnum ratingEnum : ratingEnums) {
            if (rating.equals(ratingEnum.getRate())) {
                return ratingEnum;
            }
        }
        return RateEnum.NULL_RATE;
    }

    public static List<String> toList() {
        List<String> list = new ArrayList<>();
        list.add(RateEnum.AAAP_RATE.rate);
        list.add(RateEnum.AAA_RATE.rate);
        list.add(RateEnum.AAP_RATE.rate);
        list.add(RateEnum.AA_RATE.rate);
        list.add(RateEnum.AAS_RATE.rate);
        list.add(RateEnum.AP_RATE.rate);
        list.add(RateEnum.A_RATE.rate);
        list.add(RateEnum.AS_RATE.rate);
        list.add(RateEnum.FLOAT_RATE.rate);
        list.add(RateEnum.NULL_RATE.rate);
        return list;
    }

    public static List<String> getFilterList() {
        List<String> list = new ArrayList<>();
        list.add(RateEnum.AAA_RATE.rate);
        list.add(RateEnum.AAP_RATE.rate);
        list.add(RateEnum.AA_RATE.rate);
        list.add(RateEnum.AAS_RATE.rate);
        list.add(RateEnum.AP_RATE.rate);
        return list;
    }
}


