package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.db.SourceBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.message.SourceBboMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class StringBboConvertToModelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringBboConvertToModelUtil.class);

    private String businessCode;
    @Autowired
    private CommonUtil commonUtil;

    public void convert(SourceEventModel eventModel){
        try{

            businessCode = commonUtil.getBusinessCode(eventModel.getMessage());

            String _message = commonUtil.getMessage(eventModel.getMessage());
            List<SourceBboMessageModel> sourceBboMessageModels = JSON.parseArray(_message, SourceBboMessageModel.class);

            eventModel.setSourceBboDBModelList(getDBModel(sourceBboMessageModels));

        }catch (Exception e){
            LOGGER.error(String.format("catch string convert to bbo_2 model exception.exception=>[%s] message=>[%s]",e.getMessage(),eventModel.getMessage()));
            eventModel.setFlag(false);
        }
    }

    private List<SourceBboDBModel> getDBModel(List<SourceBboMessageModel> sourceBboMessageModels){
        if(sourceBboMessageModels == null || sourceBboMessageModels.size() == 0)
            throw new RuntimeException("sourceBboMessageModels size is 0 or null");

        List<SourceBboDBModel> sourceBboDBModels = new ArrayList<>();
        sourceBboMessageModels.forEach((source) -> {
            /*
                source field setting
             */
            SourceBboDBModel target = new SourceBboDBModel();
            target.setMS_lastDealPrice(source.getMS_lastDealPrice());
            target.setMS_OPERATE(source.getMS_OPERATE());
            target.setMS_GOODS_CODE(source.getMS_GOODS_CODE());
            target.setMS_BOND_KEY_LISTED_MARKET(source.getMS_BOND_KEY_LISTED_MARKET());
            target.setMS_BestVersion(source.getMS_BestVersion());
            target.setMS_lastDealNetPrice(source.getMS_lastDealNetPrice());
            target.setMS_TYPE(source.getMS_TYPE());
            target.setMS_BOND_KEY(source.getMS_BOND_KEY());
            target.setMS_bidPrice(source.getMS_BODY().getMS_bidPrice());
            target.setMS_ofrYield(source.getMS_BODY().getMS_ofrYield());
            target.setMS_ofrOfferId(source.getMS_BODY().getMS_ofrOfferId());
            target.setMS_bidOfferId(source.getMS_BODY().getMS_bidOfferId());
            target.setMS_bidYield(source.getMS_BODY().getMS_bidYield());
            target.setMS_ofrNetPrice(source.getMS_BODY().getMS_ofrNetPrice());
            target.setMS_ofrPrice(source.getMS_BODY().getMS_ofrPrice());
            target.setMS_ofrVolume(source.getMS_BODY().getMS_ofrVolume());
            target.setMS_bidNetPrice(source.getMS_BODY().getMS_bidNetPrice());
            target.setMS_goodsCode(source.getMS_BODY().getMS_goodsCode());
            target.setMS_createTime(source.getMS_BODY().getMS_createTime());
            target.setMS_bidVolume(source.getMS_BODY().getMS_bidVolume());
            target.setMS_bidFlagRelation(source.getMS_BODY().getMS_bidFlagRelation());
            target.setMS_bidRebate(source.getMS_BODY().getMS_bidRebate());
            target.setMS_ofrPriceDescription(source.getMS_BODY().getMS_ofrPriceDescription());
            target.setMS_bidPriceDescription(source.getMS_BODY().getMS_bidPriceDescription());
            target.setMS_ofrFlagBargain(source.getMS_BODY().getMS_ofrFlagBargain());
            target.setMS_ofrRebate(source.getMS_BODY().getMS_ofrRebate());
            target.setMS_bidExercise(source.getMS_BODY().getMS_bidExercise());
            target.setMS_bidFlagBargain(source.getMS_BODY().getMS_bidFlagBargain());
            target.setMS_bidFlagRelation(source.getMS_BODY().getMS_bidFlagRelation());
            target.setMS_ofrExercise(source.getMS_BODY().getMS_ofrExercise());
            target.setMS_COMPANY_ID(source.getMS_COMPANY_ID());
            target.setMS_CREATE_TIME(source.getMS_CREATE_TIME());
            target.setMS_Version(source.getMS_Version());
            target.setMS_MODIFY_TIME(source.getMS_MODIFY_TIME());
            target.setMS_LISTED_MARKET(source.getMS_LISTED_MARKET());
            target.setMS_ofrFlagRelation(source.getMS_BODY().getMS_ofrFlagRelation());

            target.setBusinessCode(businessCode);

            /*
                db field setting
             */
            target.setUuid(commonUtil.getUUID());
            target.setCreateTime(commonUtil.convertLongToStringDatetime(source.getMS_CREATE_TIME()*1000L));
            target.setModifyTime(commonUtil.convertLongToStringDatetime(source.getMS_MODIFY_TIME()*1000L));
            target.setBboMonth(commonUtil.getDBPartition());

            sourceBboDBModels.add(target);
        });
        return sourceBboDBModels;
    }
}




















