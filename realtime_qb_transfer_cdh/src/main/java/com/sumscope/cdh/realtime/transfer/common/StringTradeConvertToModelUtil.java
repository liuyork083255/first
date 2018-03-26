package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.db.SourceTradeDBModel;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.message.SourceTradeMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是处理从 QB 接入的 trade 行情
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class StringTradeConvertToModelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringTradeConvertToModelUtil.class);

    private String businessCode;
    @Autowired
    private CommonUtil commonUtil;

    public void convert(SourceEventModel eventModel){
        try{

            businessCode = commonUtil.getBusinessCode(eventModel.getMessage());

            String _message = commonUtil.getMessage(eventModel.getMessage());
            List<SourceTradeMessageModel> sourceTradeMessageModel = JSON.parseArray(_message, SourceTradeMessageModel.class);
            eventModel.setSourceTradeDBModelList(getDBModel(sourceTradeMessageModel));
        }catch (Exception e){
            LOGGER.error(String.format("catch string convert to trade model exception.exception=>[%s]   message=>[%s]",e.getMessage(),eventModel.getMessage()));
            eventModel.setFlag(false);
        }
    }

    private List<SourceTradeDBModel> getDBModel(List<SourceTradeMessageModel> sourceTradeMessageModel){
        if(sourceTradeMessageModel == null || sourceTradeMessageModel.size() == 0)
            throw new RuntimeException("sourceTradeMessageModel size is 0 or null");

        List<SourceTradeDBModel> sourceTradeDBModels = new ArrayList<>();
        sourceTradeMessageModel.forEach((source) -> {
            /*
                source field setting
             */
            SourceTradeDBModel target = new SourceTradeDBModel();
            target.setMS_OPERATE(source.getMS_OPERATE());
            target.setMS_GOODS_CODE(source.getMS_GOODS_CODE());
            target.setMS_COMPANY_NAME(source.getMS_COMPANY_NAME());
            target.setMS_BOND_KEY_LISTED_MARKET(source.getMS_BOND_KEY_LISTED_MARKET());
            target.setMS_TYPE(source.getMS_TYPE());
            target.setMS_BOND_KEY(source.getMS_BOND_KEY());
            target.setMS_id(source.getMS_BODY().getMS_id());
            target.setMS_price(source.getMS_BODY().getMS_price());
            target.setMS_fullPrice(source.getMS_BODY().getMS_fullPrice());
            target.setMS_goodsShortName(source.getMS_BODY().getMS_goodsShortName());
            target.setMS_dealStatus(source.getMS_BODY().getMS_dealStatus());
            target.setMS_netPrice(source.getMS_BODY().getMS_netPrice());
            target.setMS_rebate(source.getMS_BODY().getMS_rebate());
            target.setMS_goodsCode(source.getMS_BODY().getMS_goodsCode());
            target.setMS_createTime(source.getMS_BODY().getMS_createTime());
            target.setMS_yield(source.getMS_BODY().getMS_yield());
            target.setMS_COMPANY_ID(source.getMS_COMPANY_ID());
            target.setMS_CREATE_TIME(source.getMS_CREATE_TIME());
            target.setMS_Version(source.getMS_Version());
            target.setMS_MODIFY_TIME(source.getMS_MODIFY_TIME());
            target.setMS_LISTED_MARKET(source.getMS_LISTED_MARKET());
            target.setMS_GOODS_ID(source.getMS_GOODS_ID());
            target.setMS_goodsID(source.getMS_BODY().getMS_goodsID());
            target.setMS_volume(source.getMS_BODY().getMS_volume());
            target.setMS_exercise(source.getMS_BODY().getMS_exercise());
            target.setMS_return_point(source.getMS_BODY().getMS_return_point());
            target.setMS_pre_close_price(source.getMS_BODY().getMS_pre_close_price());

            target.setBusinessCode(businessCode);

            target.setInternally("1");  // 这个字段内外报价字段，默认QB过来的成交行情全部都是外部报价
            target.setStatus("1");      // 这个字段是成交有效字段，默认全部都是有效
            /*
                db field setting
            */
            target.setCreateTime(commonUtil.convertLongToStringDatetime(source.getMS_CREATE_TIME()*1000L));
            target.setModifyTime(commonUtil.convertLongToStringDatetime(source.getMS_MODIFY_TIME()*1000L));

            sourceTradeDBModels.add(target);
        });
        return sourceTradeDBModels;
    }
}




















