package com.sumscope.cdh.realtime.transfer.mapper;

import com.sumscope.cdh.realtime.transfer.model.db.SourceSingleBboDBModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Repository
public interface SourceSingleBboMapper {
    int insertSourceSingleBboModel(List<SourceSingleBboDBModel> sourceSingleBboDBModelList);
}
