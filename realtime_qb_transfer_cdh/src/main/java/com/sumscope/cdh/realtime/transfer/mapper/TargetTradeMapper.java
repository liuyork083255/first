package com.sumscope.cdh.realtime.transfer.mapper;

import com.sumscope.cdh.realtime.transfer.model.db.TargetTradeDBModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Repository
public interface TargetTradeMapper {
    int insertTargetTradeModel(List<TargetTradeDBModel> targetTradeDBModelList);
}
