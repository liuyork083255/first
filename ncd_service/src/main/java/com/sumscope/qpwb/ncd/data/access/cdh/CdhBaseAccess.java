package com.sumscope.qpwb.ncd.data.access.cdh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.sumscope.service.webbond.common.cdh.CdhConfig;
import com.sumscope.service.webbond.common.cdh.CdhRestClient;
import com.sumscope.service.webbond.common.cdh.request.CdhPagingArgs;
import com.sumscope.service.webbond.common.cdh.response.CdhPageResult;
import com.sumscope.service.webbond.common.http.exception.HttpException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base Data Access Service
 * Created by mengyang.sun on 2018/01/11.
 */
public class CdhBaseAccess {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取所有数据
     *
     * @return
     * @throws HttpException
     */
    protected <T> List<T> getAllRows(CdhConfig cdhConfig, int dataSourceId, String apiName, String apiVersion, Date startTime, Date endTime, List<String> codes, String condition, List<String> resultColumns, Class<T> clazz) throws HttpException {
        List<T> allRows = CdhRestClient.getAllRows(cdhConfig, dataSourceId, apiName, apiVersion, startTime, endTime, codes, condition, resultColumns, clazz);
        return CollectionUtils.isEmpty(allRows) ? new ArrayList<>() : allRows;
    }

    /**
     * 获取所有数据
     *
     * @return
     * @throws HttpException
     */
    protected <T> CdhPageResult<T> getPage(CdhConfig cdhConfig, int dataSourceId, String apiName, String apiVersion, Date startTime, Date endTime, List<String> codes, String condition, List<String> resultColumns, CdhPagingArgs pagingArgs, Class<T> clazz) throws HttpException {
        CdhPageResult<T> pageResult = CdhRestClient.getPageRows(cdhConfig, dataSourceId, apiName, apiVersion, startTime, endTime, codes, condition, resultColumns, pagingArgs, clazz);
        return pageResult;
    }
}
