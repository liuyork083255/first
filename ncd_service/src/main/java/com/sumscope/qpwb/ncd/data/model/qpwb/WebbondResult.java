package com.sumscope.qpwb.ncd.data.model.qpwb;

import com.sumscope.service.webbond.common.web.response.MetaData;

/**
 * Created by mengyang.sun on 2018/01/19
 */
public class WebbondResult<T> {
    public final static int SUCCESS_ERR_NUM = 0;

    private MetaData meta;

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }
}
