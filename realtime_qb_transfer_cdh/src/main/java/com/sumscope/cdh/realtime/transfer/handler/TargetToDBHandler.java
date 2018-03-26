package com.sumscope.cdh.realtime.transfer.handler;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.mapper.TargetBboMapper;
import com.sumscope.cdh.realtime.transfer.mapper.TargetSingleBboMapper;
import com.sumscope.cdh.realtime.transfer.mapper.TargetTradeMapper;
import com.sumscope.cdh.realtime.transfer.model.db.TargetSingleBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import com.sumscope.cdh.realtime.transfer.model.db.TargetBboDBModel;
import com.sumscope.cdh.realtime.transfer.model.db.TargetTradeDBModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu.yang on 2017/9/30.
 */
@Component
public class TargetToDBHandler implements EventHandler<TargetEventModel>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TargetToDBHandler.class);

    @Autowired
    private TargetBboMapper targetBboMapper;
    @Autowired
    private TargetSingleBboMapper targetSingleBboMapper;
    @Autowired
    private TargetTradeMapper targetTradeMapper;

    private List<TargetBboDBModel> bboBuffer = new ArrayList<>();
    private List<TargetSingleBboDBModel> singleBuffer = new ArrayList<>();
    private List<TargetTradeDBModel> tradeBuffer = new ArrayList<>();

    @Override
    public void onEvent(TargetEventModel event, long sequence, boolean endOfBatch) throws Exception {

        switch (event.getBondType()){
            case BBO:
                bboBuffer.addAll(event.getTargetBboDBModelList());
                break;
            case TRADE:
                tradeBuffer.addAll(event.getTargetTradeDBModelList());
                break;
            case SINGLEBBO:
                singleBuffer.addAll(event.getTargetSingleBboDBModelList());
                break;
            default:
                LOGGER.info("no match Model type.");
        }


        if(bboBuffer.size() != 0 && (endOfBatch || bboBuffer.size() > 150)){
            try {
                targetBboMapper.insertTargetBboModel(bboBuffer);
                LOGGER.info(String.format("insert target bbo_2  rows [%d]",bboBuffer.size()));
            } catch (Exception e) {
                LOGGER.error(String.format("insert target bbo_2 fail.exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(bboBuffer)));
            } finally {
                bboBuffer.clear();
            }
        }


        if(tradeBuffer.size() != 0 && (endOfBatch || tradeBuffer.size() > 150)){
            try {
                targetTradeMapper.insertTargetTradeModel(tradeBuffer);
                LOGGER.info(String.format("insert target trade rows [%d]",tradeBuffer.size()));
            } catch (Exception e) {
                LOGGER.error(String.format("insert target trade fail.msg=>exception=>[%s]   message=>[%s]",e.getMessage(),JSON.toJSONString(tradeBuffer)));
            } finally {
                tradeBuffer.clear();
            }
        }

        if(singleBuffer.size() != 0 && (endOfBatch || singleBuffer.size() > 150)){
            try {
                targetSingleBboMapper.insertTargetSingleBboModel(singleBuffer);
                LOGGER.info("insert target bbo_1 rows {}",singleBuffer.size());
            }catch (Exception e){
                LOGGER.error("insert target bbo_1 fail. message={}    exception={}",JSON.toJSONString(singleBuffer),e.getMessage());
            }finally {
                singleBuffer.clear();
            }
        }

    }
}
