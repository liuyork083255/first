package com.sumscope.cdh.realtime.transfer.mapper;

import com.sumscope.cdh.realtime.transfer.model.db.SourceTradeDBModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Repository
public interface SourceTradeMapper {
    int insertSourceTradeModel(List<SourceTradeDBModel> sourceTradeDBModelList);
}
