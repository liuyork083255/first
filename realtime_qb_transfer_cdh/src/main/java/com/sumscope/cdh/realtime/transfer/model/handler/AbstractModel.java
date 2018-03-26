package com.sumscope.cdh.realtime.transfer.model.handler;


/**
 * Created by liu.yang on 2017/9/27.
 */
public abstract class AbstractModel {

    private boolean Flag = true;
    private BondType bondType;

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public BondType getBondType() {
        return bondType;
    }

    public void setBondType(BondType bondType) {
        this.bondType = bondType;
    }
}
