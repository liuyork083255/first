package com.sumscope.qpwb.ncd.service.service;

import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import com.sumscope.qpwb.ncd.data.model.webserver.WebBondResponse;
import com.sumscope.qpwb.ncd.domain.*;
import com.sumscope.qpwb.ncd.web.condition.*;
import org.apache.http.HttpResponse;

import java.util.Collection;
import java.util.List;

/**
 * Created by liu.yang on 2018/1/10.
 */
public interface NcdServiceI {
    AttentionDTO setAttention(AttentionCondition jsonObject);

    AttentionDTO getAttention(String userId);

    MatrixDTO getQuoteMatrix(QuoteMatrixCondition condition);

    NullDTO order(OrderCondition orderCondition);

    List<IssuerDetailDTO> getQuoteDetail(String userId, String institutionCode, String rate, String issuerDate);

//    /**
//     * 根据 offer_id 查询 quote_details 表，获取最大 max(id)，并赋值给detail_id
//     * @param userId
//     * @param institutionCode
//     * @return
//     */
//    List<IssuerDetailDTO> getQuoteDetailWithDetailId(String userId, String institutionCode);

    List<QuoteDetailDTO> getTimeDetail(String detailId, String userId);

    List<ReserveDTO> getReserve(String userId);

    PageBasicDTO getPageBasic(String userId);

    NullDTO saveFilter(FilterCondition filterCondition);

    FilterDTO filter(String userId);

    Collection<String> getBrokers(String userId);

    WebBondResponse subscribeNcdFilter(NcdSubCondition body);

    WebBondResponse unSubscribeNcdFilter(NcdSubCondition body);
}
