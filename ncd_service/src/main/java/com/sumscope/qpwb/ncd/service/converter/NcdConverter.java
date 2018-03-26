package com.sumscope.qpwb.ncd.service.converter;

import com.sumscope.qpwb.ncd.data.model.db.Quotes;
import com.sumscope.qpwb.ncd.data.model.repository.OrdersRepo;
import com.sumscope.qpwb.ncd.data.model.repository.QuoteDetailsRepo;
import com.sumscope.qpwb.ncd.domain.QuoteIssuerDTO;
import com.sumscope.qpwb.ncd.domain.*;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.LimitEnum;
import com.sumscope.qpwb.ncd.global.enums.ReserveStatusEnum;
import com.sumscope.qpwb.ncd.utils.BigDecimalUtils;
import com.sumscope.qpwb.ncd.utils.BusinessUtils;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import com.sumscope.qpwb.ncd.web.condition.QuoteMatrixCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;

/**
 * Created by mengyang.sun on 2018/01/23.
 */
@Service
public class NcdConverter {
    private static final Logger logger = LoggerFactory.getLogger(NcdConverter.class);
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private QuoteDetailsRepo quoteDetailsRepo;

    public MatrixDTO buildMatrixInfos(List<Quotes> quotes, QuoteMatrixCondition condition) {
        MatrixDTO matrixDTO = new MatrixDTO();
        List<QuoteIssuerDTO> quoteMatrixDTOs = new ArrayList<>();
        List<NonBankDTO> nonBankDTOs = new ArrayList<>();
        List<ContainVolumeDTO> containVolumeDTOs = new ArrayList<>();
        for (Quotes item : quotes) {
            boolean isMatch = isMatchProfit(item, condition.getLimit(), condition.getProfit());
            if (isMatch) {
                buildMatrix(quoteMatrixDTOs, item);
                buildNonBank(nonBankDTOs, item);
                buildContainVolume(containVolumeDTOs, item);
            }
        }
        nonBankDTOs.sort(Comparator.comparing(NonBankDTO::getQuoteTime, nullsLast(reverseOrder())));
        containVolumeDTOs.sort(Comparator.comparing(ContainVolumeDTO::getQuoteTime, nullsLast(reverseOrder())));
        matrixDTO.setMatrix(quoteMatrixDTOs);
        matrixDTO.setNonBank(nonBankDTOs);
        matrixDTO.setContainVolume(containVolumeDTOs);
        return matrixDTO;
    }

    private boolean isMatchProfit(Quotes item, List<String> limits, BigDecimal profit) {
        if (limits.size() == LimitEnum.values().length && BigDecimalUtils.isZero(profit)) {
            return true;
        }
        for (String limit : limits)
            switch (limit) {
                case NcdConstants.LIMIT_1M:
                    if (BigDecimalUtils.isGE(item.getM1Price(), profit)) {
                        return true;
                    }
                    break;
                case NcdConstants.LIMIT_3M:
                    if (BigDecimalUtils.isGE(item.getM3Price(), profit)) {
                        return true;
                    }
                    break;
                case NcdConstants.LIMIT_6M:
                    if (BigDecimalUtils.isGE(item.getM6Price(), profit)) {
                        return true;
                    }
                    break;
                case NcdConstants.LIMIT_9M:
                    if (BigDecimalUtils.isGE(item.getM9Price(), profit)) {
                        return true;
                    }
                    break;
                case NcdConstants.LIMIT_1Y:
                    if (BigDecimalUtils.isGE(item.getY1Price(), profit)) {
                        return true;
                    }
                    break;
                default:
                    logger.error("the limit {} is not in support list", limit);
                    break;
            }
        return false;
    }

    //生成表格每一个机构报价数据
    private void buildMatrix(List<QuoteIssuerDTO> quoteIssuerDTOS, Quotes item) {
        QuoteIssuerDTO quoteMatrixDTO = new QuoteIssuerDTO();
        String issuerCode = item.getIssuerCode();
        String issuerName = item.getIssuerName();
        quoteMatrixDTO.setRate(item.getRate());
        quoteMatrixDTO.setIssuerCode(issuerCode);
        quoteMatrixDTO.setIssuerName(issuerName);
        quoteMatrixDTO.setFullName(item.getFullName());
        quoteMatrixDTO.setRecommend(NcdConstants.RECOMMEND.equals(item.getRecommend().toString()));
        quoteMatrixDTO.setQuoteDate(DateUtils.convertSqlDateToString(item.getIssuerDate()));
        List<QuoteInfoDTO> limits = buildAllLimitQuoteInfo(item);
        if (limits.size() < 5) {
            logger.error("Failed to generate all limit quote by quotes");
            return;
        }
        QuoteInfoDTO m1 = limits.get(LimitEnum.M1.getLimitIndex());
        QuoteInfoDTO m3 = limits.get(LimitEnum.M3.getLimitIndex());
        QuoteInfoDTO m6 = limits.get(LimitEnum.M6.getLimitIndex());
        QuoteInfoDTO m9 = limits.get(LimitEnum.M9.getLimitIndex());
        QuoteInfoDTO y1 = limits.get(LimitEnum.Y1.getLimitIndex());
        if (!BigDecimalUtils.isZero(m1.getPrice())) {
            quoteMatrixDTO.setM1(m1);
        }
        if (!BigDecimalUtils.isZero(m3.getPrice())) {
            quoteMatrixDTO.setM3(m3);
        }
        if (!BigDecimalUtils.isZero(m6.getPrice())) {
            quoteMatrixDTO.setM6(m6);
        }
        if (!BigDecimalUtils.isZero(m9.getPrice())) {
            quoteMatrixDTO.setM9(m9);
        }
        if (!BigDecimalUtils.isZero(y1.getPrice())) {
            quoteMatrixDTO.setY1(y1);
        }
        quoteIssuerDTOS.add(quoteMatrixDTO);
    }

    // 所有期限(m1, m3,m6,m9.y1)的报价信息
    private List<QuoteInfoDTO> buildAllLimitQuoteInfo(Quotes item) {
        QuoteInfoDTO m1 = new QuoteInfoDTO();
        String m1Volume = item.getM1Volume();
        m1.setVolume(m1Volume);
        m1.setChange(item.getM1Change());
        m1.setAvailable(BusinessUtils.isAvailable(item.getM1Available()));
        m1.setPrice(item.getM1Price());
        m1.setMark(item.getM1Mark());
        m1.setOfferId(item.getM1DetailId());
        m1.setQuoteTime(DateUtils.convertTimeToString(item.getM1QuoteTime()));

        QuoteInfoDTO m3 = new QuoteInfoDTO();
        m3.setVolume(item.getM3Volume());
        m3.setChange(item.getM3Change());
        m3.setAvailable(BusinessUtils.isAvailable(item.getM3Available()));
        m3.setPrice(item.getM3Price());
        m3.setMark(item.getM3Mark());
        m3.setOfferId(item.getM3DetailId());
        m3.setQuoteTime(DateUtils.convertTimeToString(item.getM3QuoteTime()));

        QuoteInfoDTO m6 = new QuoteInfoDTO();
        m6.setVolume(item.getM6Volume());
        m6.setChange(item.getM6Change());
        m6.setAvailable(BusinessUtils.isAvailable(item.getM6Available()));
        m6.setPrice(item.getM6Price());
        m6.setMark(item.getM6Mark());
        m6.setOfferId(item.getM6DetailId());
        m6.setQuoteTime(DateUtils.convertTimeToString(item.getM6QuoteTime()));

        QuoteInfoDTO m9 = new QuoteInfoDTO();
        m9.setVolume(item.getM9Volume());
        m9.setChange(item.getM9Change());
        m9.setAvailable(BusinessUtils.isAvailable(item.getM9Available()));
        m9.setPrice(item.getM9Price());
        m9.setMark(item.getM9Mark());
        m9.setOfferId(item.getM9DetailId());
        m9.setQuoteTime(DateUtils.convertTimeToString(item.getM9QuoteTime()));

        QuoteInfoDTO y1 = new QuoteInfoDTO();
        y1.setVolume(item.getY1Volume());
        y1.setChange(item.getY1Change());
        y1.setAvailable(BusinessUtils.isAvailable(item.getY1Available()));
        y1.setPrice(item.getY1Price());
        y1.setMark(item.getY1Mark());
        y1.setOfferId(item.getY1DetailId());
        y1.setQuoteTime(DateUtils.convertTimeToString(item.getY1QuoteTime()));

        return Arrays.asList(m1, m3, m6, m9, y1);
    }

    private void buildNonBank(List<NonBankDTO> nonBankDTOS, Quotes quote) {
        if (markHasNonBank(quote.getM1Mark())) {
            String limit = NcdConstants.LIMIT_1M;
            String quoteRate = BigDecimalUtils.toString(quote.getM1Price(), 2);
            String quoteTime = DateUtils.convertTimeToString(quote.getM1QuoteTime());
            nonBankDTOS.add(setNonBankDto(quote, limit, quoteRate, quoteTime));
        }
        if (markHasNonBank(quote.getM3Mark())) {
            String limit = NcdConstants.LIMIT_3M;
            String quoteRate = BigDecimalUtils.toString(quote.getM3Price(), 2);
            String quoteTime = DateUtils.convertTimeToString(quote.getM3QuoteTime());
            nonBankDTOS.add(setNonBankDto(quote, limit, quoteRate, quoteTime));
        }
        if (markHasNonBank(quote.getM6Mark())) {
            String limit = NcdConstants.LIMIT_6M;
            String quoteRate = BigDecimalUtils.toString(quote.getM6Price(), 2);
            String quoteTime = DateUtils.convertTimeToString(quote.getM6QuoteTime());
            nonBankDTOS.add(setNonBankDto(quote, limit, quoteRate, quoteTime));
        }
        if (markHasNonBank(quote.getM9Mark())) {
            String limit = NcdConstants.LIMIT_9M;
            String quoteRate = BigDecimalUtils.toString(quote.getM9Price(), 2);
            String quoteTime = DateUtils.convertTimeToString(quote.getM9QuoteTime());
            nonBankDTOS.add(setNonBankDto(quote, limit, quoteRate, quoteTime));
        }
        if (markHasNonBank(quote.getY1Mark())) {
            String limit = NcdConstants.LIMIT_1Y;
            String quoteRate = BigDecimalUtils.toString(quote.getY1Price(), 2);
            String quoteTime = DateUtils.convertTimeToString(quote.getY1QuoteTime());
            nonBankDTOS.add(setNonBankDto(quote, limit, quoteRate, quoteTime));
        }
    }

    private NonBankDTO setNonBankDto(Quotes quote, String limit, String quoteRate, String quoteTime) {
        NonBankDTO nonBankDTO = new NonBankDTO();
        nonBankDTO.setIssuerCode(quote.getIssuerCode());
        nonBankDTO.setIssuerName(quote.getIssuerName());
        nonBankDTO.setFullName(quote.getFullName());
        nonBankDTO.setRate(quote.getRate());
        nonBankDTO.setLimit(limit);
        nonBankDTO.setQuoteRate(quoteRate);
        nonBankDTO.setQuoteTime(quoteTime);
        nonBankDTO.setQuoteDate(DateUtils.convertSqlDateToString(quote.getIssuerDate()));
        return nonBankDTO;
    }

    private boolean markHasNonBank(String mark) {
        return !StringUtils.isEmpty(mark) && mark.contains(NcdConstants.NON_BANK_TEXT);
    }

    private void buildContainVolume(List<ContainVolumeDTO> containVolumeDTOS, Quotes quote) {
        if (!BigDecimalUtils.isZeroStr(quote.getM1Volume())) {
            String limit = NcdConstants.LIMIT_1M;
            String quoteRate = BigDecimalUtils.toString(quote.getM1Price(), 2);
            String volume = quote.getM1Volume();
            String quoteTime = DateUtils.convertTimeToString(quote.getM1QuoteTime());
            containVolumeDTOS.add(setContainVolumeDto(quote, limit, quoteRate, volume, quoteTime));
        }
        if (!BigDecimalUtils.isZeroStr(quote.getM3Volume())) {
            String limit = NcdConstants.LIMIT_3M;
            String quoteRate = BigDecimalUtils.toString(quote.getM3Price(), 2);
            String volume = quote.getM3Volume();
            String quoteTime = DateUtils.convertTimeToString(quote.getM3QuoteTime());
            containVolumeDTOS.add(setContainVolumeDto(quote, limit, quoteRate, volume, quoteTime));
        }
        if (!BigDecimalUtils.isZeroStr(quote.getM6Volume())) {
            String limit = NcdConstants.LIMIT_6M;
            String quoteRate = BigDecimalUtils.toString(quote.getM6Price(), 2);
            String volume = quote.getM6Volume();
            String quoteTime = DateUtils.convertTimeToString(quote.getM6QuoteTime());
            containVolumeDTOS.add(setContainVolumeDto(quote, limit, quoteRate, volume, quoteTime));
        }
        if (!BigDecimalUtils.isZeroStr(quote.getM9Volume())) {
            String limit = NcdConstants.LIMIT_9M;
            String quoteRate = BigDecimalUtils.toString(quote.getM9Price(), 2);
            String volume = quote.getM9Volume();
            String quoteTime = DateUtils.convertTimeToString(quote.getM9QuoteTime());
            containVolumeDTOS.add(setContainVolumeDto(quote, limit, quoteRate, volume, quoteTime));
        }
        if (!BigDecimalUtils.isZeroStr(quote.getY1Volume())) {
            String limit = NcdConstants.LIMIT_1Y;
            String quoteRate = BigDecimalUtils.toString(quote.getY1Price(), 2);
            String volume = quote.getY1Volume();
            String quoteTime = DateUtils.convertTimeToString(quote.getY1QuoteTime());
            containVolumeDTOS.add(setContainVolumeDto(quote, limit, quoteRate, volume, quoteTime));
        }
    }


    private ContainVolumeDTO setContainVolumeDto(Quotes quote, String limit,
                                                 String quoteRate, String volume, String quoteTime) {
        ContainVolumeDTO containVolumeDTO = new ContainVolumeDTO();
        String quoteDate = DateUtils.convertSqlDateToString(quote.getIssuerDate());
        containVolumeDTO.setIssuerCode(quote.getIssuerCode());
        containVolumeDTO.setIssuerName(quote.getIssuerName());
        containVolumeDTO.setFullName(quote.getFullName());
        containVolumeDTO.setRate(quote.getRate());
        containVolumeDTO.setQuoteDate(quoteDate);
        containVolumeDTO.setLimit(limit);
        containVolumeDTO.setQuoteRate(quoteRate);
        containVolumeDTO.setVolume(volume);
        containVolumeDTO.setQuoteTime(quoteTime);
        return containVolumeDTO;
    }

    // 明细信息的表格信息
    public List<IssuerDetailDTO> buildQuoteDetail(String userId, Map<String, List<Quotes>> quoteByBrokerMap, List<BrokerDTO> brokers) {
        List<IssuerDetailDTO> result = new ArrayList<>();
        IssuerDetailDTO issuerDetailDTO = new IssuerDetailDTO();
        for (BrokerDTO broker : brokers) {
            List<Quotes> quotesByBroker = quoteByBrokerMap.get(broker.getKey());
            if (quotesByBroker == null) {
                continue;
            }
            BrokerLimitDTO brokerLimitDTO = new BrokerLimitDTO();
            for (Quotes quote : quotesByBroker) {
                List<QuoteInfoDTO> limits = buildAllLimitQuoteInfo(quote);
                if (limits.size() < 5) {
                    logger.error("Failed to generate all limit quote by quotes");
                    continue;
                }
                QuoteInfoDTO m1 = limits.get(LimitEnum.M1.getLimitIndex());
                QuoteInfoDTO m3 = limits.get(LimitEnum.M3.getLimitIndex());
                QuoteInfoDTO m6 = limits.get(LimitEnum.M6.getLimitIndex());
                QuoteInfoDTO m9 = limits.get(LimitEnum.M9.getLimitIndex());
                QuoteInfoDTO y1 = limits.get(LimitEnum.Y1.getLimitIndex());
                String issuerId = quote.getIssuerCode();
                m1.setStatus(getReserveStatus(m1, userId, issuerId, broker.getKey()));
                m3.setStatus(getReserveStatus(m3, userId, issuerId, broker.getKey()));
                m6.setStatus(getReserveStatus(m6, userId, issuerId, broker.getKey()));
                m9.setStatus(getReserveStatus(m9, userId, issuerId, broker.getKey()));
                y1.setStatus(getReserveStatus(y1, userId, issuerId, broker.getKey()));
                if (!BigDecimalUtils.isZero(m1.getPrice())) {
                    brokerLimitDTO.setM1(m1);
                }
                if (!BigDecimalUtils.isZero(m3.getPrice())) {
                    brokerLimitDTO.setM3(m3);
                }
                if (!BigDecimalUtils.isZero(m6.getPrice())) {
                    brokerLimitDTO.setM6(m6);
                }
                if (!BigDecimalUtils.isZero(m9.getPrice())) {
                    brokerLimitDTO.setM9(m9);
                }
                if (!BigDecimalUtils.isZero(y1.getPrice())) {
                    brokerLimitDTO.setY1(y1);
                }
                issuerDetailDTO.setBrokerDetail(brokerLimitDTO);
                issuerDetailDTO.setCode(broker.getKey());
            }
            result.add(issuerDetailDTO);
        }
        return result;
    }

    //获取订阅状态
    private String getReserveStatus(QuoteInfoDTO quote, String userId, String issuerId, String brokerId) {
        if (!quote.isAvailable()) {
            return ReserveStatusEnum.FINISH.getStatusCode();
        } else {
            List<Long> idsByOfferId = quoteDetailsRepo.findIdsByOfferId(userId, brokerId, issuerId, quote.getOfferId());
//            Orders order = ordersRepo.findByQuoteDetailIdAndUserIdAndBrokerIdAndIssuerId(quote.getDetailId(), userId, brokerId, issuerId);
            if (CollectionUtils.isEmpty(idsByOfferId)) {
                return ReserveStatusEnum.WITHOUT.getStatusCode();
            }
            return ReserveStatusEnum.PENDING.getStatusCode();
        }
    }
}
