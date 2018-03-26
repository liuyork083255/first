package com.sumscope.cdh.realtime.transfer.model.handler;

import com.sumscope.cdh.realtime.transfer.model.db.SourceBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.SourceSingleBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.SourceTradeDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.TargetTradeDBModel;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/28.
 */
public class SourceEventModel extends AbstractModel {
    private String message;
    private List<SourceBboDBModel> sourceBboDBModelList;
    private List<SourceSingleBboDBModel> sourceSingleBboDBModelList;
    private List<TargetTradeDBModel> targetTradeDBModelList;

    private List<SourceTradeDBModel> sourceTradeDBModelList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SourceBboDBModel> getSourceBboDBModelList() {
        return sourceBboDBModelList;
    }

    public void setSourceBboDBModelList(List<SourceBboDBModel> sourceBboDBModelList) {
        this.sourceBboDBModelList = sourceBboDBModelList;
    }

    public List<SourceSingleBboDBModel> getSourceSingleBboDBModelList() {
        return sourceSingleBboDBModelList;
    }

    public void setSourceSingleBboDBModelList(List<SourceSingleBboDBModel> sourceSingleBboDBModelList) {
        this.sourceSingleBboDBModelList = sourceSingleBboDBModelList;
    }

    public List<TargetTradeDBModel> getTargetTradeDBModelList() {
        return targetTradeDBModelList;
    }

    public void setTargetTradeDBModelList(List<TargetTradeDBModel> targetTradeDBModelList) {
        this.targetTradeDBModelList = targetTradeDBModelList;
    }

    public List<SourceTradeDBModel> getSourceTradeDBModelList() {
        return sourceTradeDBModelList;
    }

    public void setSourceTradeDBModelList(List<SourceTradeDBModel> sourceTradeDBModelList) {
        this.sourceTradeDBModelList = sourceTradeDBModelList;
    }
}
