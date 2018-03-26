package com.sumscope.cdh.webbond.utils;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.resultset.ResultSet;
import com.sumscope.cdh.webbond.request.BondFilter;
import com.sumscope.cdh.webbond.model.Bond;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BondFilterTool
{
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    private BondFilterTool()
    {
    }

    // this is the original filterBonds, now we use CQEngine version instead
    public static Collection<String> filterBonds(Collection<Bond> bonds, BondFilter filter)
    {
        //StopWatch stopWatch = new LoggingStopWatch();
        return bonds.parallelStream()
                .filter(generatePredicate(filter))
                .map(b -> b.bondKeyListMarket)
                .collect(Collectors.toSet());
        //PerformanceLogger.
    }

    public static Set<String> filterBonds(IndexedCollection<Bond> indexedBonds, BondFilter filter) {
        Query<Bond> bondQuery = generateQuery(filter);

        if (bondQuery == null) {
            HashSet<String> set = new HashSet();
            set.add("*");
            return set;
        }

        ResultSet<Bond> results = indexedBonds.retrieve(bondQuery);

        HashSet<String> strSet = new HashSet<>();
        for (Bond bond : results) {
            strSet.add(bond.bondKeyListMarket);
        }

        return strSet;
    }

    private static boolean filterOneResidualMaturity(String filterCode, int valueInDays)
    {
        double valueInYears = (((double) valueInDays) / 365.25);
        switch (filterCode)
        {
            case "01": // <3M, <90D
                return (valueInDays < 90);
            case "02": // 3-6M, >=90D && <180D
                return (valueInDays >= 90 && valueInDays < 180);
            case "03": // 6-9M, >=180D && <270D
                return (valueInDays >= 180 && valueInDays < 270);
            case "04": // 9-12M, >=270D && <365D
                return (valueInDays >= 270 && valueInDays < 365);
            case "05": // 1-3Y
                return (valueInDays >= 365 && valueInYears < 3);
            case "06": // 3-5Y
                return (valueInYears >= 3 && valueInYears < 5);
            case "07": // 5-7Y
                return (valueInYears >= 5 && valueInYears < 7);
            case "08": // 7-10Y
                return (valueInYears >= 7 && valueInYears < 10);
            case "09": // >=10Y
                return (valueInYears >= 10);
        }
        logger.error("unknown residual maturity: {}", valueInDays);
        return false;
    }

    private static boolean filterResidualMaturity(List<String> filterMaturities, String valueInDays, String bondKey)
    {
        int intValue = -1;
        try
        {
            intValue = Integer.parseInt(valueInDays);
        }
        catch (NumberFormatException e)
        {
            logger.error("unknown residual maturity: {} for bond {}", valueInDays, bondKey);
            return false;
        }
        for (String filter : filterMaturities)
            if (filterOneResidualMaturity(filter, intValue))
                return true;
        return false;
    }

    private static Pair<Boolean, Double> parseMaturity(String maturity)
    {
        String unit = StringTool.right(maturity, 1);
        if (StringTool.isEmpty(unit))
            return Pair.of(false, 0d);
        unit = unit.toLowerCase();
        if (!unit.equals("y") && !unit.equals("d"))
            return Pair.of(false, 0d);
        try
        {
            double value = Double.parseDouble(maturity.substring(0, maturity.length() - 1));
            if (unit.equals("y"))
                value = value * 365;
            return Pair.of(true, value);
        }
        catch (Exception e)
        {
            return Pair.of(false, 0d);
        }
    }

    private static boolean filterResidualMaturity(String start, String end, String valueInDays)
    {
        int intValue = -1;
        try
        {
            intValue = Integer.parseInt(valueInDays);
        }
        catch (NumberFormatException e)
        {
            logger.error("unknown residual maturity: {}", valueInDays);
            return false;
        }
        Pair<Boolean, Double> parsedStart = parseMaturity(start);
        Pair<Boolean, Double> parsedEnd = parseMaturity(end);
        if (parsedStart.getLeft())
        {
            if (intValue < parsedStart.getRight())
                return false;
        }
        if (parsedEnd.getLeft())
        {
            if (intValue > parsedEnd.getRight())
                return false;
        }
        return true;
    }

    private static boolean filterMunicipalTypes(List<String> filterMunicipalTypes, List<String> bondMunicipalTypes) {
        for (String filter : filterMunicipalTypes) {
            if (StringTool.contains(bondMunicipalTypes, filter)) {
                return true;
            }
        }
        return false;
    }

    private static boolean filterSpecialTypes(List<String> filterSpecialTypes, List<String> bondSpecialTypes)
    {
        for (String filter : filterSpecialTypes)
        {
            if (StringTool.contains(bondSpecialTypes, filter))
                return true;
        }
        return false;
    }

    private static Predicate<Bond> generatePredicate(BondFilter filter)
    {
        Predicate<Bond> p1 = bond -> StringTool.isEmptyList(filter.getQbBondTypes()) || StringTool.contains(filter.getQbBondTypes(), bond.qbBondType); // qbBondType uses second level only

        Predicate<Bond> p2 = bond -> StringTool.isEmptyList(filter.getBondSubTypes()) || StringTool.contains(filter.getBondSubTypes(), bond.bondSubTypeLevel1)
                || StringTool.contains(filter.getBondSubTypes(), bond.bond_subtype);

        Predicate<Bond> p3 = bond -> StringTool.isEmptyList(filter.getBondResidualMaturities()) ||
                filterResidualMaturity(filter.getBondResidualMaturities(), Double.toString(bond.bondResidualMaturity), bond.bond_key);

        Predicate<Bond> p4 = bond -> (StringTool.isEmpty(filter.getBondResidualMaturityStart()) && StringTool.isEmpty(filter.getBondResidualMaturityEnd()))
                || filterResidualMaturity(filter.getBondResidualMaturityStart(), filter.getBondResidualMaturityEnd(), Double.toString(bond.bondResidualMaturity));

        Predicate<Bond> p5 = bond -> StringTool.isEmpty(filter.getBondMaturityDateStart())
                || (!StringTool.isEmpty(String.valueOf(bond.maturity_date)) && bond.maturity_date.compareTo(Integer.valueOf(filter.getBondMaturityDateStart())) >= 0);

        Predicate<Bond> p6 = bond -> StringTool.isEmpty(filter.getBondMaturityDateEnd())
                || (!StringTool.isEmpty(String.valueOf(bond.maturity_date)) && bond.maturity_date.compareTo(Integer.valueOf(filter.getBondMaturityDateEnd())) <= 0);

        Predicate<Bond> p7 = bond -> StringTool.isEmptyList(filter.getPerpetualTypes()) || StringTool.contains(filter.getPerpetualTypes(), bond.perpetualType);
        Predicate<Bond> p8 = bond -> StringTool.isEmptyList(filter.getIssuers()) || StringTool.contains(filter.getIssuers(), bond.issuer_code);
        Predicate<Bond> p9 = bond -> StringTool.isEmptyList(filter.getIssuerTypes()) || StringTool.contains(filter.getIssuerTypes(), bond.issuerType);
        Predicate<Bond> p10 = bond -> StringTool.isEmptyList(filter.getMunicipalTypes()) || filterMunicipalTypes(filter.getMunicipalTypes(), bond.municipalType);
        Predicate<Bond> p11 = bond -> StringTool.isEmptyList(filter.getIssuerRatings()) || StringTool.contains(filter.getIssuerRatings(), bond.issuerRating);
        Predicate<Bond> p12 = bond -> StringTool.isEmptyList(filter.getBondRatings()) || StringTool.contains(filter.getBondRatings(), bond.bondRating);
        Predicate<Bond> p13 = bond -> StringTool.isEmptyList(filter.getRatingAugments()) || StringTool.contains(filter.getRatingAugments(), bond.rating_augment);
        Predicate<Bond> p14 = bond -> StringTool.isEmptyList(filter.getFinancialBondTypes()) || StringTool.contains(filter.getFinancialBondTypes(), bond.financialBondType);
        Predicate<Bond> p15 = bond -> StringTool.isEmptyList(filter.getCorporateBondTypes()) || StringTool.contains(filter.getCorporateBondTypes(), bond.corporateBondType);
        Predicate<Bond> p16 = bond -> StringTool.isEmptyList(filter.getCouponTypes()) || StringTool.contains(filter.getCouponTypes(), bond.couponType);
        Predicate<Bond> p17 = bond -> StringTool.isEmptyList(filter.getOptionTypes()) || StringTool.contains(filter.getOptionTypes(), bond.optionType);

        Predicate<Bond> p18 = bond -> StringTool.isEmptyList(filter.getSectors()) || StringTool.contains(filter.getSectors(), bond.sector) || StringTool.contains(filter.getSectors(), bond.sectorLevel1);
        Predicate<Bond> p19 = bond -> StringTool.isEmptyList(filter.getProvinces()) || StringTool.contains(filter.getProvinces(), bond.province);
        Predicate<Bond> p20 = bond -> StringTool.isEmptyList(filter.getIssueYears()) || StringTool.contains(filter.getIssueYears(), bond.issue_year);

        Predicate<Bond> p21 = bond -> StringTool.isEmptyList(filter.getSpecialTypes()) || filterSpecialTypes(filter.getSpecialTypes(), bond.specialTypes);

        Predicate<Bond> predicateAll = p1.and(p2).and(p3).and(p4).and(p5).and(p6).and(p7).and(p8)
                .and(p9).and(p10).and(p11).and(p12).and(p13)
                .and(p14).and(p15).and(p16).and(p17).and(p18).and(p19).and(p20).and(p21);

        return predicateAll;
    }

    private static Query<Bond> generateQuery(BondFilter filter) {

        ArrayList<Query<Bond>> queryList = new ArrayList<Query<Bond>>();

        if (!StringTool.isEmptyList(filter.getFrequentlyUsedSector())) {
        	queryList.add(QueryFactory.in(Bond.BOND_FREQUENTLYUSEDSECTOR_TYPE, filter.getFrequentlyUsedSector()));
		}

        if (!StringTool.isEmptyList(filter.getQbBondTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_TYPE, filter.getQbBondTypes()));
        }
        if (!StringTool.isEmptyList(filter.getBondSubTypes())) {
            queryList.add(QueryFactory.or(QueryFactory.in(Bond.BOND_SUB_TYPE, filter.getBondSubTypes()), QueryFactory.in(Bond.BOND_SUB_TYPE_LEVEL1, filter.getBondSubTypes())));
        }
        if (!StringTool.isEmptyList(filter.getBondResidualMaturities())) {
            queryList.add(QueryFactory.in(Bond.BOND_RESIDUAL_MATURITY_LABEL, filter.getBondResidualMaturities()));
        }

        if (!StringTool.isEmpty(filter.getBondResidualMaturityStart()) && !StringTool.isEmpty(filter.getBondResidualMaturityEnd())) {
            Pair<Boolean, Double> parsedStart = parseMaturity(filter.getBondResidualMaturityStart());
            Pair<Boolean, Double> parsedEnd = parseMaturity(filter.getBondResidualMaturityEnd());
            if (parsedStart.getLeft() && parsedEnd.getLeft()) {
                queryList.add(QueryFactory.and(QueryFactory.greaterThanOrEqualTo(Bond.BOND_RESIDUAL_MATURITY,parsedStart.getRight()),QueryFactory.lessThanOrEqualTo(Bond.BOND_RESIDUAL_MATURITY,parsedEnd.getRight())));
            } else {
                queryList.add(QueryFactory.equal(Bond.BOND_RESIDUAL_MATURITY_LABEL, "#"));
            }
        } else if (!StringTool.isEmpty(filter.getBondResidualMaturityStart()) && StringTool.isEmpty(filter.getBondResidualMaturityEnd())) {
            Pair<Boolean, Double> parsedStart = parseMaturity(filter.getBondResidualMaturityStart());
            if (parsedStart.getLeft()) {
                queryList.add(QueryFactory.greaterThanOrEqualTo(Bond.BOND_RESIDUAL_MATURITY, parsedStart.getRight()));
            } else {
                queryList.add(QueryFactory.equal(Bond.BOND_RESIDUAL_MATURITY_LABEL, "#"));
            }
        } else if (StringTool.isEmpty(filter.getBondResidualMaturityStart()) && !StringTool.isEmpty(filter.getBondResidualMaturityEnd())) {
            Pair<Boolean, Double> parsedEnd = parseMaturity(filter.getBondResidualMaturityEnd());
            if (parsedEnd.getLeft()) {
                queryList.add(QueryFactory.between(Bond.BOND_RESIDUAL_MATURITY, 0d, true, parsedEnd.getRight(), true));
            } else {
                queryList.add(QueryFactory.equal(Bond.BOND_RESIDUAL_MATURITY_LABEL, "#"));
            }
        }

        if (!StringTool.isEmpty(filter.getBondMaturityDateStart()) && !StringTool.isEmpty(filter.getBondMaturityDateEnd())) {
            queryList.add(QueryFactory.and(QueryFactory.greaterThanOrEqualTo(Bond.BOND_MATURITY_DATE,Integer.valueOf(filter.getBondMaturityDateStart())),QueryFactory.lessThanOrEqualTo(Bond.BOND_MATURITY_DATE,Integer.valueOf(filter.getBondMaturityDateEnd()))));
        } else if (!StringTool.isEmpty(filter.getBondMaturityDateStart()) && StringTool.isEmpty(filter.getBondMaturityDateEnd())) {
            queryList.add(QueryFactory.greaterThanOrEqualTo(Bond.BOND_MATURITY_DATE, Integer.valueOf(filter.getBondMaturityDateStart())));
        } else if (StringTool.isEmpty(filter.getBondMaturityDateStart()) && !StringTool.isEmpty(filter.getBondMaturityDateEnd())) {
            queryList.add(QueryFactory.between(Bond.BOND_MATURITY_DATE, 0, false, Integer.valueOf(filter.getBondMaturityDateEnd()), true));
        }

        if (!StringTool.isEmptyList(filter.getPerpetualTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_PERPETUAL_TYPE, filter.getPerpetualTypes()));
        }
        if (!StringTool.isEmptyList(filter.getIssuers())) {
            queryList.add(QueryFactory.in(Bond.BOND_ISSUER_CODE, filter.getIssuers()));
        }
        if (!StringTool.isEmptyList(filter.getIssuerTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_ISSUER_TYPE, filter.getIssuerTypes()));
        }
        if (!StringTool.isEmptyList(filter.getMunicipalTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_MUNICIPAL_TYPE, filter.getMunicipalTypes()));
        }
        if (!StringTool.isEmptyList(filter.getIssuerRatings())) {
            queryList.add(QueryFactory.in(Bond.BOND_ISSUER_RATING, filter.getIssuerRatings()));
        }
        if (!StringTool.isEmptyList(filter.getBondRatings())) {
            queryList.add(QueryFactory.in(Bond.BOND_RATING, filter.getBondRatings()));
        }
        if (!StringTool.isEmptyList(filter.getRatingAugments())) {
            queryList.add(QueryFactory.in(Bond.BOND_RATING_AUGMENT, filter.getRatingAugments()));
        }
        if (!StringTool.isEmptyList(filter.getFinancialBondTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_FINANCIAL_BOND_TYPE, filter.getFinancialBondTypes()));
        }
        if (!StringTool.isEmptyList(filter.getCorporateBondTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_CORPORATE_BOND_TYPE, filter.getCorporateBondTypes()));
        }
        if (!StringTool.isEmptyList(filter.getCouponTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_COUPON_TYPE, filter.getCouponTypes()));
        }
        if (!StringTool.isEmptyList(filter.getOptionTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_OPTION_TYPE, filter.getOptionTypes()));
        }
        if (!StringTool.isEmptyList(filter.getSectors())) {
            queryList.add(QueryFactory.or(QueryFactory.in(Bond.BOND_SECTOR, filter.getSectors()), QueryFactory.in(Bond.BOND_SECTOR_LEVEL1, filter.getSectors())));
        }
        if (!StringTool.isEmptyList(filter.getProvinces())) {
            queryList.add(QueryFactory.in(Bond.BOND_PROVINCE, filter.getProvinces()));
        }
        if (!StringTool.isEmptyList(filter.getIssueYears())) {
            queryList.add(QueryFactory.in(Bond.BOND_ISSUER_YEAR, filter.getIssueYears()));
        }
        if (!StringTool.isEmptyList(filter.getSpecialTypes())) {
            queryList.add(QueryFactory.in(Bond.BOND_SPECIAL_TYPES, filter.getSpecialTypes()));
        }
        if (queryList.size() > 2) {
            return QueryFactory.and(queryList.get(0), queryList.get(1), queryList.subList(2, queryList.size()));
        } else if (queryList.size() == 2) {
            return QueryFactory.and(queryList.get(0), queryList.get(1));
        } else if (queryList.size() < 2 && queryList.size() > 0) {
            return queryList.get(0);
        } else {
            return null;
        }
    }
}
