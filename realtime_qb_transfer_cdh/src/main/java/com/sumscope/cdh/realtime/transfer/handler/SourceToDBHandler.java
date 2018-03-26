package com.sumscope.cdh.realtime.transfer.handler;

import com.alibaba.fastjson.JSON;
import com.sumscope.cdh.realtime.transfer.mapper.SourceBboMapper;
import com.sumscope.cdh.realtime.transfer.mapper.SourceSingleBboMapper;
import com.sumscope.cdh.realtime.transfer.mapper.SourceTradeMapper;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.db.SourceBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.SourceSingleBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.SourceTradeDBModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class SourceToDBHandler extends AbstractHandler<SourceEventModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceToDBHandler.class);

    @Value("${rabbitmq.receiver.trade.from}")
    private String tradeFrom;
    @Autowired
    private SourceBboMapper sourceBboMapper;
    @Autowired
    private SourceSingleBboMapper sourceSingleBboMapper;
    @Autowired
    private SourceTradeMapper sourceTradeMapper;

    private List<SourceBboDBModel> bboBuffer = new ArrayList<>();
    private List<SourceSingleBboDBModel> singleBboBuffer = new ArrayList<>();
    private List<SourceTradeDBModel> tradeBuffer = new ArrayList<>();

    @Override
    void doEvent(SourceEventModel event, long sequence, boolean endOfBatch) throws Exception {

        switch (event.getBondType()){
            case BBO:
                bboBuffer.addAll(event.getSourceBboDBModelList());
                break;
            case SINGLEBBO:
                singleBboBuffer.addAll(event.getSourceSingleBboDBModelList());
                break;
            case TRADE:
                if(StringUtils.equalsIgnoreCase(tradeFrom,"qb")){
                    tradeBuffer.addAll(event.getSourceTradeDBModelList());
                }
                break;
            default:
                LOGGER.error(String.format("no match Model type. message =>[%s]",event.getMessage()));
                event.setFlag(false);
        }


        if(bboBuffer.size() != 0 && (endOfBatch || bboBuffer.size() > 100)){
            try {
                sourceBboMapper.insertSourceBboModel(bboBuffer);
                LOGGER.info(String.format("insert source bbo_2 rows [%d]",bboBuffer.size()));
            } catch (Exception e) {
                LOGGER.error(String.format("insert source bbo_2 fail.exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(bboBuffer)));
            } finally {
                bboBuffer.clear();
            }
        }

        if(singleBboBuffer.size() != 0 && (endOfBatch || singleBboBuffer.size() > 100)){
            try {
                sourceSingleBboMapper.insertSourceSingleBboModel(singleBboBuffer);
                LOGGER.info(String.format("insert source bbo_1 rows [%d]",singleBboBuffer.size()));
            } catch (Exception e) {
                LOGGER.error(String.format("insert source bbo_1 fail.exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(singleBboBuffer)));
            } finally {
                singleBboBuffer.clear();
            }
        }
        if(StringUtils.equalsIgnoreCase(tradeFrom,"qb")){
            if(tradeBuffer.size() != 0 && (endOfBatch || tradeBuffer.size() > 100)){
                try {
                    sourceTradeMapper.insertSourceTradeModel(tradeBuffer);
                    LOGGER.info(String.format("insert source trade rows [%d]",tradeBuffer.size()));
                } catch (Exception e) {
                    LOGGER.error(String.format("insert source trade fail.exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(tradeBuffer)));
                } finally {
                    tradeBuffer.clear();
                }
            }
        }

    }
}




















