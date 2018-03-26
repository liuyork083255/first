package com.sumscope.cdh.realtime.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu.yang on 2017/10/18.
 */
public abstract class AbstractHandler implements EventHandler<BondEventModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHandler.class);

    @Override
    public void onEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception {
        if(event.isFlag()) {
            try
            {
                doEvent(event,sequence,endOfBatch);
            }
            catch (Exception e)
            {
                LOGGER.error(String.format("catch exception from doEvent.msg => [%s]",e.getMessage()));
            }
        }
    }

    abstract void doEvent(BondEventModel event, long sequence, boolean endOfBatch) throws Exception;
}
