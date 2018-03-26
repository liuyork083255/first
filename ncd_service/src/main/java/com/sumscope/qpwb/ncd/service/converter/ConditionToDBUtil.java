package com.sumscope.qpwb.ncd.service.converter;

import com.sumscope.qpwb.ncd.data.access.qpwb.QpwbNcdIssuersAccess;
import com.sumscope.qpwb.ncd.data.model.db.Favourites;
import com.sumscope.qpwb.ncd.data.model.db.Orders;
import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import com.sumscope.qpwb.ncd.data.model.db.UserFilters;
import com.sumscope.qpwb.ncd.data.model.iam.IssuerInfo;
import com.sumscope.qpwb.ncd.data.model.repository.QuoteDetailsRepo;
import com.sumscope.qpwb.ncd.web.condition.AttentionCondition;
import com.sumscope.qpwb.ncd.web.condition.FilterCondition;
import com.sumscope.qpwb.ncd.web.condition.OrderCondition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ConditionToDBUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConditionToDBUtil.class);
    @Autowired
    private QpwbNcdIssuersAccess qpwbNcdIssuersAccess;
    @Autowired
    private QuoteDetailsRepo quoteDetailsRepo;

    public UserFilters convertFilterConditionToUserFilter(FilterCondition fc) {
        UserFilters userFilters = new UserFilters();
        userFilters.setUserId(fc.getUserId());
        userFilters.setBrokerId(fc.getBrokerId());
        userFilters.setRating(getStringByList(fc.getRate()));
        userFilters.setInstitutionType(getStringByList(fc.getInstitutionType()));
        userFilters.setTotalAsset(checkParamIsUndefined(fc.getTotalAsset()));
        userFilters.setNetAsset(checkParamIsUndefined(fc.getNetAsset()));
        userFilters.setRevenue(checkParamIsUndefined(fc.getRevenue()));
        userFilters.setNetProfit(checkParamIsUndefined(fc.getNetProfit()));
        userFilters.setLdp(checkParamIsUndefined(fc.getLdp()));
        userFilters.setCcar(checkParamIsUndefined(fc.getCcar()));
        userFilters.setBadRatio(checkParamIsUndefined(fc.getBadRatio()));
        return userFilters;
    }

    public Favourites convertAttentionConditionToFavourites(AttentionCondition ac) {
        Favourites favourites = new Favourites();
        if (ac == null) return favourites;

        favourites.setUserId(ac.getUserId());
        favourites.setIssuerId(ac.getInstitution());

        return favourites;
    }

    public Orders convertOrderConditionToOrders(OrderCondition oc) {
        Orders orders = new Orders();
        Long detailId = quoteDetailsRepo.findIdByOfferIdAndQuoteTimeAndIssuePrice(oc.getQuoteOfferId(),
                oc.getQuoteTime(), oc.getPrice());
        orders.setQuoteDetailId(detailId);
        orders.setUserId(oc.getUserId());
        orders.setBrokerId(oc.getBrokerId());
        orders.setBrokerName(oc.getBrokerName());
        orders.setIssuerName(oc.getIssuerName());
        orders.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        orders.setIssuerId(oc.getIssuerId());
        return orders;
    }

    public UserContact convertToUserContact(OrderCondition userContactCondition) {
        UserContact userContact = new UserContact();
        if (userContactCondition == null) return userContact;
        userContact.setUserId(userContactCondition.getUserId());
        userContact.setQq(userContactCondition.getQq());
        userContact.setMobile(userContactCondition.getMobile());
        userContact.setTelephone(userContactCondition.getTelephone());
        userContact.setUserName(userContactCondition.getUserName());
        userContact.setCompanyCode(userContactCondition.getBrokerId()); // brokerId 即为 companyCode
        userContact.setCustomerOrgName(userContactCondition.getCustomerOrgName());
        return userContact;
    }

    private String getStringByList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) return null;
        String s = "";
        for (String l : list) {
            s += l + ":";
            if (StringUtils.equals(l, "RRD"))
                s += "RTD:";
        }
        if (s.length() > 0) s = s.substring(0, s.length() - 1);
        return s;
    }

    private String checkParamIsUndefined(String param) {
        if (StringUtils.isBlank(param)) return "0:0";
        else return param.replace("undefined", "0");
    }
}
