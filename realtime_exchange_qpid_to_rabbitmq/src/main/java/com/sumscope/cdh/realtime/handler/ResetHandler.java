package com.sumscope.cdh.realtime.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class ResetHandler implements EventHandler<BondEventModel> {

    @Override
    public void onEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {
        event.setSource(null);
        event.getTarget().clear();
        event.setFlag(true);
    }
}
