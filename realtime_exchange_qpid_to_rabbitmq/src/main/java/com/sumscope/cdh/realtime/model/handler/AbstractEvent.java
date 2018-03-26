package com.sumscope.cdh.realtime.model.handler;

/**
 * Created by liu.yang on 2017/10/18.
 */
public abstract class AbstractEvent{
    private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
