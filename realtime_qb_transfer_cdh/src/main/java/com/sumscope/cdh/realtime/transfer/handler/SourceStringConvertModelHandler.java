package com.sumscope.cdh.realtime.transfer.handler;

import com.lmax.disruptor.EventHandler;
import com.sumscope.cdh.realtime.transfer.common.StringBboConvertToModelUtil;
import com.sumscope.cdh.realtime.transfer.common.StringSingleBboConvertToModelUtil;
import com.sumscope.cdh.realtime.transfer.common.StringTradeConvertToModelUtil;
import com.sumscope.cdh.realtime.transfer.common.StringTradeFromProcessorConvertToModelUtil;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Component
public class SourceStringConvertModelHandler implements EventHandler<SourceEventModel>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceStringConvertModelHandler.class);

    @Value("${rabbitmq.receiver.trade.from}")
    private String tradeFrom;
    @Autowired
    private StringBboConvertToModelUtil bboConvertUtil;
    @Autowired
    private StringSingleBboConvertToModelUtil singleBboConvertUtil;
    @Autowired
    private StringTradeConvertToModelUtil tradeConvertUtil;

    @Autowired
    private StringTradeFromProcessorConvertToModelUtil tradeFromProcessorConvertToModelUtil;

    @Override
    public void onEvent(SourceEventModel event, long sequence, boolean endOfBatch) throws Exception {
        switch (event.getBondType()) {
            case BBO:
                bboConvertUtil.convert(event);
                break;
            case SINGLEBBO:
                singleBboConvertUtil.convert(event);
                break;
            case TRADE:
                if(StringUtils.equalsIgnoreCase(tradeFrom,"qb")){
                    tradeConvertUtil.convert(event);
                }else{
                    tradeFromProcessorConvertToModelUtil.convert(event);
                }
                break;
            default:
                LOGGER.error(String.format("no match Model type. message =>[%s]",event.getMessage()));
                event.setFlag(false);
        }
    }
}
