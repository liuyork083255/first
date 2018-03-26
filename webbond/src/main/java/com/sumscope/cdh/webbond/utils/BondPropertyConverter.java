package com.sumscope.cdh.webbond.utils;

import com.sumscope.cdh.webbond.model.Bond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class BondPropertyConverter {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    private BondPropertyConverter() {}

    private static boolean bondSubTypeInArray(String bondSubtype) {
        if (StringTool.contains(Arrays.asList("BGB","SCB","PBB","PSB","LLB","TLB"), bondSubtype))
            return true;
        return false;
    }

    public static String issuerType(String issuerCode, Map<String, List<String>> institutions, int institutionSubtypeIndex, String bondSubtype) {
        if (bondSubTypeInArray(bondSubtype))
            return Bond.INVALID_VALUE;

        if (!institutions.containsKey(issuerCode)) {
            logger.warn("issuerCode: no issuer code {} found in institution_info", issuerCode);
            return Bond.INVALID_VALUE;
        }
        return institutions.get(issuerCode).get(institutionSubtypeIndex);
    }

    public static String issuerRating(String issuerCode, Map<String, List<String>> institutions, int institutionRatingIndex,
                                      String bondSubtype, String bondIssuerRatingCurrent) {
        if (!institutions.containsKey(issuerCode)) {
            logger.warn("issuerRating: no issuer code {} found in institution_info", issuerCode);
            return "01";
        }

        String institutionRating = institutions.get(issuerCode).get(institutionRatingIndex);
        if (institutionRating.equals("AAA+") && bondIssuerRatingCurrent.equals("AAA"))
            return "07";

        if (bondSubTypeInArray(bondSubtype))
            return "01";

        if (StringTool.isEmpty(bondIssuerRatingCurrent))
            return "01";

        if (bondIssuerRatingCurrent.equals("AAA+"))
            return "07";
        if (bondIssuerRatingCurrent.equals("AAA"))
            return "06";
        if (bondIssuerRatingCurrent.equals("AA+"))
            return "05";
        if (bondIssuerRatingCurrent.equals("AA"))
            return "04";
        if (bondIssuerRatingCurrent.equals("AA-"))
            return "03";
        if (bondIssuerRatingCurrent.equals("A+"))
            return "02";
        return "01";
    }

    public static List<String> municipalType(String issuerCode, Map<String, List<String>> issuerInfos, int cbrcFinancingPlatformIndex,
                                             String bondSubtype, String bondIsMunicipal) {
        List<String> municipalTypes = new ArrayList<>();
        if (issuerInfos.containsKey(issuerCode)) {
            String cbrcFinancingPlatform = issuerInfos.get(issuerCode).get(cbrcFinancingPlatformIndex);
            if (cbrcFinancingPlatform.equals("Y") || cbrcFinancingPlatform.equals("是")) {
                municipalTypes.add("03"); //平台债
            }
        }
        if (StringTool.isEmpty(bondIsMunicipal)) {
            municipalTypes.add(Bond.INVALID_VALUE);
        } else {
            if (bondIsMunicipal.equals("Y")) {
                municipalTypes.add("01"); //城投债
            } else {
                municipalTypes.add("02"); //非城投债
            }
        }
        return municipalTypes;
    }

    public static List<String> specialTypes(Bond bond) {
        List<String> specialTypes = new ArrayList<>();
        if (!StringTool.isEmpty(bond.newListed) && bond.newListed.equals("1"))
            specialTypes.add("01");
        if (!StringTool.isEmpty(bond.crossMarket) && bond.crossMarket.equals("1"))
            specialTypes.add("02");
        if (!StringTool.isEmpty(bond.pledge) && bond.pledge.equals("1"))
            specialTypes.add("03");
        if (!StringTool.isEmpty(bond.interBank) && bond.interBank.equals("1"))
            specialTypes.add("04");
        if (!StringTool.isEmpty(bond.exchange) && bond.exchange.equals("1"))
            specialTypes.add("05");
        return specialTypes;
    }
}

