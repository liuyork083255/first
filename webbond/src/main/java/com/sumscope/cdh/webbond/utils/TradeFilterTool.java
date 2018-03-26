package com.sumscope.cdh.webbond.utils;

import com.sumscope.cdh.webbond.request.ComplexFilter;
import com.sumscope.cdh.webbond.generated.WebbondTrade;
import com.sumscope.cdh.webbond.request.FilterByBondRequest;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sumscope.cdh.webbond.utils.Utils.compareChinese;
import static com.sumscope.cdh.webbond.utils.Utils.compareExpection;

/**
 * Created by chengzhang.wang on 2017/2/14.
 */
public class TradeFilterTool
{
    private static final int LIMIT = 1000;

    public Collection<WebbondTrade> doFilter(Collection<String> bondKeyListMarkets, Collection<WebbondTrade> trades, FilterByBondRequest filterRequest)
    {
        if (filterRequest.tradePageNumber * filterRequest.tradePageSize >= LIMIT)
        {
            return new ArrayList<>();
        }
        return trades.stream()
                .filter(generatePredicate(bondKeyListMarkets, filterRequest.tradeBrokerIds))
                .skip(filterRequest.tradePageNumber * filterRequest.tradePageSize)
                .limit(filterRequest.tradePageSize)
                .collect(Collectors.toList());
    }

    public Collection<WebbondTrade> doFilter(Collection<String> bonds, Collection<WebbondTrade> trades, ComplexFilter filter)
    {
        if (filter.tradePageNumber * filter.tradePageSize >= LIMIT)
        {
            return new ArrayList<>();
        }
        if (filter.tradeOrderByColumn == null || filter.tradeOrderType == null || filter.tradeOrderType == ComplexFilter.OrderType.NORMAL)
            return trades.stream()
                    .filter(generatePredicate(bonds, filter.tradeBrokerIds))
                    .skip(filter.tradePageNumber * filter.tradePageSize)
                    .limit(filter.tradePageSize)
                    .collect(Collectors.toList());
        else
            return trades.stream()
                    .filter(generatePredicate(bonds, filter.tradeBrokerIds)).sorted((a, b) ->
                    {
                        int result;
                        switch (filter.tradeOrderByColumn)
                        {
                            case "dealType":
                                result = ObjectUtils.compare(a.getDealType(), b.getDealType());
                                break;
                            case "remaintTime":
                                result = ObjectUtils.compare(a.getRemaintTimeValue(), b.getRemaintTimeValue());
                                break;
                            case "bondCode":
                                result = ObjectUtils.compare(a.getBondCode(), b.getBondCode());
                                break;
                            case "shortName":
                                result = ObjectUtils.compare(a.getPinyinFull(), b.getPinyinFull());
                                break;
                            case "dealPrice":
                                result = ObjectUtils.compare(a.getDealPriceValue(), b.getDealPriceValue());
                                break;
                            case "cdcValuation":
                                result = ObjectUtils.compare(a.getCdcValuationValue(), b.getCdcValuationValue());
                                break;
                            case "csiValuation":
                                result = ObjectUtils.compare(a.getCsiValuationValue(), b.getCsiValuationValue());
                                break;
                            case "issuerBondRating":
                                result = ObjectUtils.compare(a.getIssuerBondRating(), b.getIssuerBondRating());
                                break;
                            case "expection":
                                result = ObjectUtils.compare(a.getExpectionSortValue(), b.getExpectionSortValue());
                                break;
                            case "ratingInstitutionName":
                                result = ObjectUtils.compare(a.getRatingInstitutionPinyin(), b.getRatingInstitutionPinyin());
                                break;
                            case "brokerName":
                                result = ObjectUtils.compare(a.getBrokerId(), b.getBrokerId());
                                break;
                            case "createDateTime":
                                result = ObjectUtils.compare(a.getCreateDateTime(), b.getCreateDateTime());
                                break;
                            default:
                                return -1;
                        }
                        return result * filter.tradeOrderType.getValue();
                    }).skip(filter.tradePageNumber * filter.tradePageSize)
                    .limit(filter.tradePageSize)
                    .collect(Collectors.toList());
    }

    private Predicate<WebbondTrade> generatePredicate(Collection<String> bondKeyListMarkets, Collection<String> tradeBrokerIds)
    {
        Predicate<WebbondTrade> p1 = trade -> tradeBrokerIds.contains(trade.getBrokerId());
        Predicate<WebbondTrade> p2 = trade -> bondKeyListMarkets.contains("*") || bondKeyListMarkets.contains(String.format("%s.%s", trade.getBondKey(), trade.getListedMarket()));
        Predicate<WebbondTrade> predicateAll = p1.and(p2);
        return predicateAll;
    }

}
