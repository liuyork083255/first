package com.sumscope.cdh.realtime.transfer.common;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.model.db.SourceSingleBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.restful.RestfulUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class StringSingleBboConvertToModelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringSingleBboConvertToModelUtil.class);

    private String businessCode;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RestfulUtil restfulUtil;

    public void convert(SourceEventModel eventModel){
        try{

            businessCode = commonUtil.getBusinessCode(eventModel.getMessage());

            String _message = commonUtil.getMessage(eventModel.getMessage());
            List<SourceSingleBboDBModel> sourceSingleBboDBModels = JSON.parseArray(_message, SourceSingleBboDBModel.class);
            eventModel.setSourceSingleBboDBModelList(setDBField(sourceSingleBboDBModels));
        }catch (Exception e){
            LOGGER.error(String.format("catch string convert to bbo_1 model exception.exception=>[%s]   message=>[%s]",e.getMessage(),eventModel.getMessage()));
            eventModel.setFlag(false);
        }
    }

    private List<SourceSingleBboDBModel> setDBField(List<SourceSingleBboDBModel> sourceSingleBboDBModels){
        if(sourceSingleBboDBModels == null || sourceSingleBboDBModels.size() == 0)
            throw new RuntimeException("sourceSingleBboDBModels size is 0 or null");

        sourceSingleBboDBModels.forEach((source) -> {
             /*
                db field setting
             */

            String bondKey_listedMarket = source.getBk()+"_"+source.getLm();

            source.setUuid(commonUtil.getUUID());
            source.setCreateTime(commonUtil.convertLongToStringDatetime(source.getCt()*1000L));
            source.setModifyTime(commonUtil.convertLongToStringDatetime(source.getMt()*1000L));
            source.setSingleBboMonth(commonUtil.getDBPartition());

            if(businessCode == null || businessCode.equals("ALL")){
                source.setBusinessCode(restfulUtil.getBusinessCode(bondKey_listedMarket));
            }else{
                source.setBusinessCode(businessCode);
            }

        });
        return sourceSingleBboDBModels;
    }
}




















