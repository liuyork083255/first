package com.sumscope.cdh.realtime.transfer.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/9/30.
 */
@Component
public class TargetResetHandler implements EventHandler<TargetEventModel>{
    @Override
    public void onEvent(TargetEventModel event, long sequence, boolean endOfBatch) throws Exception {
        event.setFlag(true);
        event.setTargetBboDBModelList(null);
        event.setTargetTradeDBModelList(null);
    }
}
