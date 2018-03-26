package com.sumscope.cdh.realtime.transfer.mapper;

import com.sumscope.cdh.realtime.transfer.model.db.SourceBboDBModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liu.yang on 2017/9/29.
 */
@Repository
public interface SourceBboMapper {
    int insertSourceBboModel(List<SourceBboDBModel> sourceBboDBModelList);
}
