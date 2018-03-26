package com.sumscope.cdh.realtime.transfer.model.handler;

/**
 * Created by liu.yang on 2017/9/29.
 */
public enum BondType {

    TRADE(0),SINGLEBBO(1),BBO(2);

    private final int value;

    BondType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
