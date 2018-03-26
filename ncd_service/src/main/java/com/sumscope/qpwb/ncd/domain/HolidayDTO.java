package com.sumscope.qpwb.ncd.domain;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "期限休息日模型")
public class HolidayDTO {
    private String date = "";
    private int gap;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }
}
