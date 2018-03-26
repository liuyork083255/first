package com.sumscope.cdh.realtime.transfer.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.model.handler.AbstractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liu.yang on 2017/9/28.
 */
public abstract class AbstractHandler<T extends AbstractModel> implements EventHandler<T>{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHandler.class);

    @Override
    public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
        if(event.isFlag()){
            try {
                doEvent(event,sequence,endOfBatch);
            } catch (Exception e) {
                LOGGER.error(String.format("catch exception from doEvent.msg => [%s]",e.getMessage()));
            }
        }
    }

    abstract void doEvent(T event, long sequence, boolean endOfBatch) throws Exception;
}
