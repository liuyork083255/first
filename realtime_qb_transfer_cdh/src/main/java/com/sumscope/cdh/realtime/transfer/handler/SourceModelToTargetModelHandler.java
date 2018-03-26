package com.sumscope.cdh.realtime.transfer.handler;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.transfer.common.SourceModelConvertToTargetUtil;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class SourceModelToTargetModelHandler extends AbstractHandler<SourceEventModel>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceModelToTargetModelHandler.class);

    @Autowired
    @Qualifier("TargetDisruptor")
    private Disruptor<TargetEventModel> targetDisruptor;
    @Autowired
    private SourceModelConvertToTargetUtil sourceModelConvertToTargetUtil;

    @Override
    void doEvent(SourceEventModel event, long sequence, boolean endOfBatch) throws Exception {
        try {
            targetDisruptor.publishEvent(sourceModelConvertToTargetUtil,event);
        } catch (Exception e) {
            LOGGER.error(String.format("targetDisruptor publish event fail,exception=>[%s].  message=>[%s]", e.getMessage(), event.getMessage()));
        }
    }
}
