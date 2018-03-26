package com.sumscope.cdh.realtime.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Repository
public interface BondMapper {
    int insertBondModel(List<HashMap<String, Object>> bondList);
}
