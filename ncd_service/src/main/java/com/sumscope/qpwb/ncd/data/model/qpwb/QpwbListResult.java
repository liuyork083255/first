package com.sumscope.qpwb.ncd.data.model.qpwb;
import java.util.List;

/**
 * Created by mengyang.sun on 2018/01/19
 */
public class QpwbListResult<T> extends WebbondResult {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
