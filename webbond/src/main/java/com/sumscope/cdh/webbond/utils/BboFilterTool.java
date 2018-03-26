package com.sumscope.cdh.webbond.utils;

import com.sumscope.cdh.webbond.WebBondException;
import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.model.BidOfrValue;
import com.sumscope.cdh.webbond.model.PriceRange;
import com.sumscope.cdh.webbond.request.ComplexFilter;
import com.sumscope.cdh.webbond.request.FilterByBondRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by chengzhang.wang on 2017/2/6.
 */
public class BboFilterTool
{
    private final Collator chineseCollator = Collator.getInstance(Locale.CHINA);

    private static final int LIMIT = 1000;

    public Collection<WebbondBbo> doFilter(Collection<String> bondKeyListMarkets, Collection<WebbondBbo> bestQuotes, FilterByBondRequest filterRequest)
    {
        if (filterRequest.bboPageNumber * filterRequest.bboPageSize >= LIMIT)
        {
            return new ArrayList<>();
        }
        Predicate<WebbondBbo> p1 = bestQuote -> filterRequest.quoteBrokerIds.contains(bestQuote.getBrokerId());
        Predicate<WebbondBbo> p2 = bestQuote -> bondKeyListMarkets.contains("*") || bondKeyListMarkets.contains(String.format("%s.%s", bestQuote.getBondKey(), bestQuote.getListedMarket()));
        return bestQuotes.stream()
                .filter(p1.and(p2))
                .skip(filterRequest.bboPageNumber * filterRequest.bboPageSize)
                .limit(filterRequest.bboPageSize)
                .collect(Collectors.toList());
    }

    public Collection<WebbondBbo> doFilter(Collection<String> bonds, Collection<WebbondBbo> bestQuotes, ComplexFilter filter)
    {
        if (filter.bboPageNumber * filter.bboPageSize >= LIMIT)
        {
            return new ArrayList<>();
        }
        if (filter.bboOrderByColumn == null || filter.bboOrderType == null || filter.bboOrderType == ComplexFilter.OrderType.NORMAL)
            return bestQuotes.stream()
                    .filter(generatePredicate(bonds, filter))
                    .skip(filter.bboPageNumber * filter.bboPageSize)
                    .limit(filter.bboPageSize)
                    .collect(Collectors.toList());
        else
            return bestQuotes.stream()
                    .filter(generatePredicate(bonds, filter)).sorted((a, b) ->
                    {
                        int result;
                        switch (filter.bboOrderByColumn)
                        {
                            case "remaintTime":
                                result = ObjectUtils.compare(a.getRemaintTimeValue(), b.getRemaintTimeValue());
                                break;
                            case "bondCode":
                                result = ObjectUtils.compare(a.getBondCode(), b.getBondCode());
                                break;
                            case "shortName":
                                result = compareChinese(a.getShortName(), b.getShortName());
                                break;
                            case "latestTransaction":
                                result = ObjectUtils.compare(a.getLatestTransactionValue(), b.getLatestTransactionValue());
                                break;
                            case "bidVolume":
                                result = ObjectUtils.compare(a.getBidVolumeSortValue(), b.getBidVolumeSortValue());
                                break;
                            case "bid":
                                result = ObjectUtils.compare(a.getBidValue(), b.getBidValue());
                                break;
                            case "ofr":
                                result = ObjectUtils.compare(a.getOfrValue(), b.getOfrValue());
                                break;
                            case "ofrVolume":
                                result = ObjectUtils.compare(a.getOfrVolumeSortValue(), b.getOfrVolumeSortValue());
                                break;
                            case "cdcValuation":
                                result = ObjectUtils.compare(a.getCdcValuationValue(), b.getCdcValuationValue());
                                break;
                            case "csiValuation":
                                result = ObjectUtils.compare(a.getCsiValuationValue(), b.getCsiValuationValue());
                                break;
                            case "duration":
                                result = ObjectUtils.compare(a.getDuration(), b.getDuration());
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
                            case "bidSubCdc":
                                result = ObjectUtils.compare(a.getBidSubCdcValue(), b.getBidSubCdcValue());
                                break;
                            case "cdcSubOfr":
                                result = ObjectUtils.compare(a.getCdcSubOfrValue(), b.getCdcSubOfrValue());
                                break;
                            case "bidSubCsi":
                                result = ObjectUtils.compare(a.getBidSubCsiValue(), b.getBidSubCsiValue());
                                break;
                            case "csiSubOfr":
                                result = ObjectUtils.compare(a.getCsiSubOfrValue(), b.getCsiSubOfrValue());
                                break;
                            case "brokerName":
                                result = ObjectUtils.compare(a.getBrokerId(), b.getBrokerId());
                                break;
                            case "updateTime":
                                result = ObjectUtils.compare(a.getUpdateTime(), b.getUpdateTime());
                                break;
                            default:
                                return -1;
                        }
                        return result * filter.bboOrderType.getValue();
                    }).skip(filter.bboPageNumber * filter.bboPageSize)
                    .limit(filter.bboPageSize)
                    .collect(Collectors.toList());

    }

    public Collection<WebbondBbo> doFilter(Collection<String> bonds, Collection<WebbondBbo> bestQuotes, ComplexFilter filter, boolean cdcAuthed)
    {
        if (!cdcAuthed)
        {
            if (null != filter.bidOfrValue &&
                    null != filter.bidOfrValue.getBidMinusCDC() &&
                    !StringUtils.isEmpty(filter.bidOfrValue.getBidMinusCDC().getValue()))
                return new ArrayList<>();
            if (null != filter.bidOfrValue &&
                    null != filter.bidOfrValue.getOfrMinusCDC() &&
                    !StringUtils.isEmpty(filter.bidOfrValue.getOfrMinusCDC().getValue()))
                return new ArrayList<>();
        }
        return doFilter(bonds, bestQuotes, filter);
    }

    public int compareChinese(String str1, String str2)
    {
        if (StringUtils.equals(str1, str2))
        {
            return 0;
        }
        if (null == str1 || str1.equals("--"))
        {
            return -1;
        }
        if (null == str2 || str2.equals("--"))
        {
            return 1;
        }
        return chineseCollator.compare(str1, str2);
    }

    public double getSortVolumeValue(String volumeStr)
    {
        if (volumeStr.contains("+"))
        {
            String firstNumber = volumeStr.split("\\+")[0].trim();
            if (NumberUtils.isNumber(firstNumber))
            {
                return Double.valueOf(firstNumber);
            }
            else
            {
                return 0D;
            }
        }
        else
        {
            if (NumberUtils.isNumber(volumeStr))
            {
                return Double.valueOf(volumeStr);
            }
            else
            {
                return -999D;
            }
        }
    }

    private Predicate<WebbondBbo> generatePredicate(Collection<String> bonds, ComplexFilter filter)
    {
        Predicate<WebbondBbo> p1 = bestQuote ->
        {
            if (filter.validQuote == null || (filter.isBothSides != null && filter.isBothSides))
            {
                return true;
            }
            else if (filter.validQuote)
            {
                return NumberUtils.isNumber(bestQuote.getBid()) || NumberUtils.isNumber(bestQuote.getOfr());
            }
            else
            {
                return true;
            }
        };
        Predicate<WebbondBbo> p2 = bestQuote ->
        {
            if (filter.isBothSides != null && filter.isBothSides == true)
            {
                if (!NumberUtils.isNumber(bestQuote.getOfr()) || !NumberUtils.isNumber(bestQuote.getBid()))
                {
                    return false;
                }
                else if (filter.bothSidesDiff == null)
                {
                    return true;
                }
                else
                {
                    if ((Double.parseDouble(bestQuote.getBid()) - Double.parseDouble(bestQuote.getOfr())) <= filter.bothSidesDiff / 100.0D)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                return true;
            }
        };
        Predicate<WebbondBbo> p3 = bestQuote ->
        {
            BidOfrValue bidOfrValue = filter.bidOfrValue;
            if (null == bidOfrValue)
            {
                return true;
            }
            else
            {
                return fulfil(bidOfrValue.getBidRange(), bestQuote.getBid()) &&
                        fulfil(bidOfrValue.getOfrRange(), bestQuote.getOfr()) &&
                        fulfil(bidOfrValue.getBidMinusCDC(), bestQuote.getBidSubCdc(), true) &&
                        fulfil(bidOfrValue.getOfrMinusCDC(), bestQuote.getCdcSubOfr(), true);
            }
        };
        Predicate<WebbondBbo> p4 = bestQuote -> filter.quoteBrokerIds.contains(bestQuote.getBrokerId());
        Predicate<WebbondBbo> p5 = bestQuote -> bonds.contains("*") || bonds.contains(String.format("%s.%s", bestQuote.getBondKey(), bestQuote.getListedMarket()));
        Predicate<WebbondBbo> predicateAll = p1.and(p2).and(p3).and(p4).and(p5);

        return predicateAll;
    }

    private boolean fulfil(PriceRange range, String valueStr)
    {
        return fulfil(range, valueStr, false);
    }

    private boolean fulfil(PriceRange range, String valueStr, boolean isAbsolute)
    {
        if (range == null)
        {
            return true;
        }
        else if (valueStr == null)
        {
            return false;
        }
        else
        {
            if (!NumberUtils.isNumber(range.getValue()))
            {
                throw WebBondException.PredefinedExceptions.DataTypeConvertError;
            }
            else if (!NumberUtils.isNumber(valueStr))
            {
                return false;
            }
            else
            {
                Double value = isAbsolute ? Math.abs(Double.parseDouble(valueStr)) : Double.parseDouble(valueStr);
                Double rangeValue = Double.parseDouble(range.getValue());
                switch (range.getOp())
                {
                    case GE:
                        if (rangeValue <= value)
                            return true;
                        else
                            return false;
                    case LE:
                        if (rangeValue >= value)
                            return true;
                        else
                            return false;
                    default:
                        throw WebBondException.PredefinedExceptions.DataTypeConvertError;
                }
            }
        }
    }

}
