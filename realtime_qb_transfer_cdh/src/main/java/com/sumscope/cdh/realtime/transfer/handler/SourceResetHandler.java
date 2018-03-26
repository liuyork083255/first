package com.sumscope.cdh.realtime.transfer.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class SourceResetHandler implements EventHandler<SourceEventModel> {
    @Override
    public void onEvent(SourceEventModel event, long sequence, boolean endOfBatch) throws Exception {
        event.setFlag(true);
        event.setMessage(null);
        event.setSourceBboDBModelList(null);
        event.setSourceSingleBboDBModelList(null);
        event.setTargetTradeDBModelList(null);
//        event.setSourceTradeDBModelList(null);
    }
}
