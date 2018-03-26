package com.sumscope.qpwb.ncd.data.service;

import com.sumscope.qpwb.ncd.data.access.qpwb.QpwbNcdIssuersAccess;
import com.sumscope.qpwb.ncd.data.model.db.Issuers;
import com.sumscope.qpwb.ncd.data.model.qpwb.NcdIssuer;
import com.sumscope.qpwb.ncd.data.model.repository.IssuersRepo;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.global.enums.RateEnum;
import com.sumscope.qpwb.ncd.utils.BigDecimalUtils;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NcdIssuersService {
    @Value("${qpwb.restful.default.userid}")
    private String userId;

    @PostConstruct
    public void init() {
        saveIssuersByQpwb();
    }

    @Autowired
    IssuersRepo issuersRepo;
    @Autowired
    StateOwnBankService stateOwnBankService;
    @Autowired
    QpwbNcdIssuersAccess webBondNcdIssuers;
    private static final Logger logger = LoggerFactory.getLogger(NcdIssuersService.class);
    public static Map<String, NcdIssuer> cachedAllNcdIssuers = new ConcurrentHashMap<>();

    @Transactional
    public void saveIssuersByQpwb() {
        try {
            List<NcdIssuer> ncdLst = loadAllNcdIssuers(userId);
            issuersRepo.deleteAllInBatch();
            Map<String, String> ownBankMap = StateOwnBankService.stateOwnBankMap;
            List<Issuers> issuersList = new ArrayList<>();
            ncdLst.forEach(item -> {
                issuersList.add(convertToIssuersEntity(item, ownBankMap));
            });
            issuersRepo.save(issuersList);
            logger.info("end to load ncd issuers from qpwb by restful, size:{}", issuersList.size());
        } catch (Exception ex) {
            logger.error("Failed to load ncd issuers from qpwb by restful,err:{}", ex.getMessage());
        }
    }

    public List<NcdIssuer> loadAllNcdIssuers(String userId) {
        logger.info("start to load ncd issuers from qpwb by restful");
        List<NcdIssuer> ncdLst = webBondNcdIssuers.getNcdIssuers(userId);
        cachedAllNcdIssuers = ncdLst.stream().collect(Collectors.toMap(NcdIssuer::getIssuerCode, Function.identity()));
        return ncdLst;
    }

    private Issuers convertToIssuersEntity(NcdIssuer ncdIssuers, Map<String, String> ownBankMap) {
        Issuers issuersModel = new Issuers();
        String issuerCode = ncdIssuers.getIssuerCode();
        issuersModel.setCode(issuerCode);
        issuersModel.setId(ncdIssuers.getIssuerId());
        issuersModel.setFullName(ncdIssuers.getFullName());
        issuersModel.setShortName(ncdIssuers.getShortName());
        RateEnum rateEnum = RateEnum.rateEnum(ncdIssuers.getRate());
        if (rateEnum != null) {
            issuersModel.setFirstLevelOrder(rateEnum.getSortLevel());
        }
        String bankSortValue = ownBankMap.get(issuerCode);
        if (bankSortValue != null) {
            issuerCode = bankSortValue;
        }
        issuersModel.setSecondLevelOrder(issuerCode);
        issuersModel.setTotalAsset(BigDecimalUtils.toDecimal(ncdIssuers.getTotalAsset(), 2));
        issuersModel.setNetAsset(BigDecimalUtils.toDecimal(ncdIssuers.getNetAsset(), 2));
        issuersModel.setRevenue(BigDecimalUtils.toDecimal(ncdIssuers.getRevenue(), 2));
        issuersModel.setNetProfit(BigDecimalUtils.toDecimal(ncdIssuers.getNetProfit(), 2));
        issuersModel.setLdp(BigDecimalUtils.toDecimal(ncdIssuers.getLdp(), 2));
        issuersModel.setCcar(BigDecimalUtils.toDecimal(ncdIssuers.getCcar(), 2));
        issuersModel.setBadRatio(BigDecimalUtils.toDecimal(ncdIssuers.getBadRatio(), 2));
        issuersModel.setRate(ncdIssuers.getRate());
        issuersModel.setId(ncdIssuers.getIssuerId());
        Date indexDate = DateUtils.convertToSqlDate(
                ncdIssuers.getIndexDate(), NcdConstants.DATE_YYYY_MM_DD);
        if (indexDate != null) {
            issuersModel.setIndexDate(new Timestamp(indexDate.getTime()));
        }
        return issuersModel;
    }
}
