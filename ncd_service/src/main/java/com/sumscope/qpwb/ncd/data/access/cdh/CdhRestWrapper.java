package com.sumscope.qpwb.ncd.data.access.cdh;

import com.sumscope.service.webbond.common.cdh.CdhConfig;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liu.yang on 2018/1/15.
 */
@Component
public class CdhRestWrapper extends CdhBaseAccess {

    protected <T> List<T> getAllRowsSimple(String url,String username,String password,int pageSize,int dataSourceId,String apiName,String condition,List<String> resultColumns,Class<T> clazz)
            throws HttpException {
        return super.getAllRows(new CdhConfig(url,username,password,pageSize), dataSourceId, apiName, "N", null, null, null, condition, resultColumns, clazz);
    }
}
