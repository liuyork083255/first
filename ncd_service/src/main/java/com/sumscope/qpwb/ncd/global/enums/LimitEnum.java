package com.sumscope.qpwb.ncd.global.enums;

import com.sumscope.qpwb.ncd.global.constants.NcdConstants;

import java.util.ArrayList;
import java.util.List;

public enum  LimitEnum {
    M1(0, NcdConstants.LIMIT_1M),
    M3(1, NcdConstants.LIMIT_3M),
    M6(2, NcdConstants.LIMIT_6M),
    M9(3, NcdConstants.LIMIT_9M),
    Y1(4, NcdConstants.LIMIT_1Y),;

    private LimitEnum(int limitIndex, String limitDesc){
        this.limitIndex = limitIndex;
        this.limitDesc = limitDesc;
    }

    private int limitIndex;
    private String limitDesc;
    public int getLimitIndex() {
        return limitIndex;
    }

    public void setLimitIndex(int limitIndex) {
        this.limitIndex = limitIndex;
    }

    public String getLimitDesc() {
        return limitDesc;
    }

    public void setLimitDesc(String limitDesc) {
        this.limitDesc = limitDesc;
    }

    public static List<String> getLimitDescs() {
        List<String> result = new ArrayList<>();
        LimitEnum[] values = LimitEnum.values();
        for (LimitEnum value : values) {
            result.add(value.getLimitDesc());
        }
        return result;
    }
}
