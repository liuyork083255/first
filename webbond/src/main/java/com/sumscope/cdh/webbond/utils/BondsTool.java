package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sumscope.cdh.webbond.model.Bond;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class NumberTool
{
    private NumberTool()
    {
    }

    public static String trimTrailingZero(String s)
    {
        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static String roundTwoDigitsString(double value)
    { // round and convert to string
        return String.format("%.2f", value);
    }
}

public class BondsTool
{
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");
    private static final int HashMap = 0;

    public static void generateBonds(ICdhRestfulConfig restfulConfig, Map<String, Bond> bonds, JsonNode dictionaryRootNode) throws IOException
    {
        updateBondsWithCodes(restfulConfig, bonds, dictionaryRootNode, null);
    }

    private static boolean isLeapYear(int year)
    {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private static String convertIntDaysToYears(int days)
    {
        int yearCnt = 0;
        Date curDate = new Date();
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, days);
        do
        {
            ca.add(Calendar.YEAR, -1);
            yearCnt++;
        } while (ca.getTime().getTime() > curDate.getTime());

        if (ca.getTime().getTime() == curDate.getTime())
        {
            return Integer.toString(yearCnt) + "Y";
        }

        yearCnt--;
        if (yearCnt > 0)
        {
            ca.add(Calendar.YEAR, 1);
            Date targetDate = ca.getTime();
            Calendar curCal = Calendar.getInstance();
            curCal.setTime(curDate);
            int curYear = curCal.get(Calendar.YEAR);

            Calendar targetCal = Calendar.getInstance();
            targetCal.setTime(targetDate);
            int targetYear = targetCal.get(Calendar.YEAR);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            double _year = 0;
            if (targetYear > curYear)
            {
                if (!isLeapYear(curYear) && isLeapYear(targetYear))
                {
                    String leapDayStr = Integer.toString(targetYear) + "-02-29";
                    Date leapDay = null;
                    try
                    {
                        leapDay = sdf.parse(leapDayStr);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    if (targetDate.getTime() > leapDay.getTime())
                    {
                        _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR) + 366) / 366;
                        return NumberTool.trimTrailingZero(NumberTool.roundTwoDigitsString(_year + yearCnt)) + "Y";
                    }
                }
                else if (isLeapYear(curYear) && !isLeapYear(targetYear))
                {
                    String leapDayStr = Integer.toString(curYear) + "-02-29";
                    Date leapDay = null;
                    try
                    {
                        leapDay = sdf.parse(leapDayStr);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    if (curDate.getTime() < leapDay.getTime())
                    {
                        _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR) + 366) / 366;
                        return NumberTool.trimTrailingZero(NumberTool.roundTwoDigitsString(_year + yearCnt)) + "Y";
                    }
                }
                _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR) + 365) / 365;
                return NumberTool.trimTrailingZero(NumberTool.roundTwoDigitsString(_year + yearCnt)) + "Y";
            }
            else
            {
                if (isLeapYear(curYear))
                {
                    String leapDayStr = Integer.toString(curYear) + "-02-29";
                    Date leapDay = null;
                    try
                    {
                        leapDay = sdf.parse(leapDayStr);
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    if (curDate.getTime() < leapDay.getTime() && targetDate.getTime() > leapDay.getTime())
                    {
                        _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR)) / 366;
                        return NumberTool.trimTrailingZero(NumberTool.roundTwoDigitsString(_year + yearCnt)) + "Y";
                    }
                }
                _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR)) / 365;
                return NumberTool.trimTrailingZero(NumberTool.roundTwoDigitsString(_year + yearCnt)) + "Y";
            }
        }
        else
        {
            return Integer.toString(days) + "D";
        }
    }

    private static int getIntDay(String dayStr)
    {
        String temp = dayStr.trim();
        return Integer.parseInt(temp.substring(0, temp.length() - 1));
    }

    public static String convertDaysToYears(String bondKey, String daysStr)
    {
        try
        {
            List<String> strs = Arrays.stream(daysStr.split("\\+"))
                    .map(dayStr ->
                            dayStr.trim().compareTo("N") == 0 ? dayStr :
                                    convertIntDaysToYears(getIntDay(dayStr)))
                    .collect(Collectors.toList());
            if (strs.size() > 0)
            {
                int year_index = strs.get(0).indexOf('Y');
                if (year_index > 0)
                {
                    int dot_index = strs.get(0).indexOf('.');
                    if (dot_index < 0)
                    {
                        strs.set(0, strs.get(0).substring(0, year_index) + ".00Y");
                    }
                    else
                    {
                        if (year_index - dot_index - 1 == 1)
                        {
                            strs.set(0, strs.get(0).substring(0, year_index) + "0Y");
                        }
                    }
                }
            }
            return String.join(" + ", strs);
        }
        catch (Exception e)
        {
            logger.warn(String.format("Convert maturity (%s) for bond (%s) failed", daysStr, bondKey));
        }
        return daysStr;
    }

    public static String residualMaturityLabel(String residualMaturity)
    {
        int _residualMaturity = Integer.valueOf(residualMaturity);
        String label = null;
        if (_residualMaturity < 90 && _residualMaturity >= 0)
        {
            label = "01";
        }
        else if (_residualMaturity < 180 && _residualMaturity >= 90)
        {
            label = "02";
        }
        else if (_residualMaturity < 270 && _residualMaturity >= 180)
        {
            label = "03";
        }
        else if (_residualMaturity < 365 && _residualMaturity >= 270)
        {
            label = "04";
        }
        else if (_residualMaturity >= 365)
        {
            String _residualMaturityYearStr = convertIntDaysToYears(_residualMaturity);
            if (_residualMaturityYearStr.indexOf("Y") < 0) {
                label = "04";
            } else {
                Double _residualMaturityYear = Double.valueOf(_residualMaturityYearStr.substring(0, _residualMaturityYearStr.indexOf("Y")));
                if (_residualMaturityYear < 3d && _residualMaturityYear >= 1d) {
                    label = "05";
                } else if (_residualMaturityYear < 5d && _residualMaturityYear >= 3d) {
                    label = "06";
                } else if (_residualMaturityYear < 7d && _residualMaturityYear >= 5d) {
                    label = "07";
                } else if (_residualMaturityYear < 10d && _residualMaturityYear >= 7d) {
                    label = "08";
                } else if (_residualMaturityYear >= 10d) {
                    label = "09";
                }
            }
        }
        return label;
    }

    // marketType in ('CIB', 'SSE', 'SZE')
    private static String getRecentNthWorkday(ICdhRestfulConfig restfulConfig, String marketType, int n) throws IOException {
        String restfulApiKey = "application.restful.api.recent_Nth_workday_" + marketType;
        List<String> lists = new ArrayList<>();
        CdhRestful.query(restfulConfig, restfulApiKey, (ArrayNode jsonNodes) -> {
            for (JsonNode nd : jsonNodes) {
                lists.add(nd.path("recent_Nth_workday").asText());
            }
        });
        if (lists.size() < n) {
            logger.error("holiday_info wrong!");
            return "";
        }
        return lists.get(n - 1);
    }

    private static void updateNewListed(Bond bond, Map<String, String> recentNthWorkdays)
    {
        if(bond.listed_market == null)
            return;

        if (recentNthWorkdays.get(bond.listed_market) == "") // holiday_info wrong
            return;

        if (bond.listed_date == null || bond.listed_date == "")
            return;

        bond.newListed = bond.listed_date.compareTo(recentNthWorkdays.get(bond.listed_market)) >= 0 ? "1" : "0";
    }

    public static void updateBondsWithCodes(
            ICdhRestfulConfig restfulConfig, Map<String, Bond> bonds, JsonNode dictionaryRootNode, List<String> bondCodes) throws IOException
    {

        Map<String, List<String>> institutions = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.institution_info", "institution_code", Arrays.asList(
                        "institution_subtype", "institution_rating", "full_name_c"));

        Map<String, List<String>> issuers = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.p_issuer_info", "institution_code", Arrays.asList(
                        "sw_sector_code", "sw_subsector_code", "province_code", "cbrc_financing_platform"));

        Map<String, List<String>> residualMaturities = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.residual_maturities", "bond_key", Arrays.asList(
                        "residual_maturity", "Now_to_OptionDate_or_to_MaturityDate_days"));

        Map<String, List<String>> crossMarketBonds = CdhRestful.loadRestfulObjects(restfulConfig,
                "application.restful.api.cross_market_bonds", "bond_key", Arrays.asList(
                        "bond_count"));

        Map<String, String> recentNthWorkdays = new HashMap<>();
        int n = 6; // last 6 working days including today
        recentNthWorkdays.put("CIB", getRecentNthWorkday(restfulConfig, "CIB", n));
        recentNthWorkdays.put("SSE", getRecentNthWorkday(restfulConfig, "SSE", n));
        recentNthWorkdays.put("SZE", getRecentNthWorkday(restfulConfig, "SZE", n));

        CdhRestful.queryWithCodes(restfulConfig, "application.restful.api.bonds", bondCodes, (arrayNodes) ->
        {
        	// 
        	ObjectMapper mapper = JsonTool.createObjectMapper();
        	ObjectNode staticDictionary = null;

        	Map<String, ArrayList<String>> map = new  HashMap<String, ArrayList<String>>();
			try {
				staticDictionary = ModuleResources.getStaticDictionary(mapper);
				for (int i = 0; i < staticDictionary.get("msg").get("frequentlyUsedSector").size(); ++i) {
					ArrayList<String> value = new ArrayList<>();
					
					JsonNode jsonNode = staticDictionary.get("msg").get("frequentlyUsedSector").get(i).get("child");
					if (jsonNode!= null) {
						for(int j = 0; j < jsonNode.size(); j ++) {
							value.add(jsonNode.get(j).toString());
						}
					}
					map.put(staticDictionary.get("msg").get("frequentlyUsedSector").get(i).get("code").toString(), value);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            int count = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            for (JsonNode nd : arrayNodes)
            {
                try
                {
                    Bond bond = mapper.treeToValue(nd, Bond.class);

                    int institutionSubtypeIndex = 0;
                    bond.issuerType = BondPropertyConverter.issuerType(bond.issuer_code, institutions, institutionSubtypeIndex, bond.bond_subtype);

                    int institutionRatingIndex = 1;
                    bond.issuerRating = BondPropertyConverter.issuerRating(bond.issuer_code, institutions, institutionRatingIndex, bond.bond_subtype, bond.issuer_rating_current);

                    // 城投
                    int cbrcFinancingPlatformIndex = 3;
                    bond.municipalType = BondPropertyConverter.municipalType(bond.issuer_code, issuers, cbrcFinancingPlatformIndex, bond.bond_subtype, bond.is_municipal);

                    if (bond.municipalType.contains("01")) {
                        if (bond.frequentlyUsedSector == null) {
                            bond.frequentlyUsedSector = new ArrayList<String>();
                        }
                        bond.frequentlyUsedSector.add("01");
                    }

                    if (issuers.containsKey(bond.issuer_code)) {
                        bond.sector = issuers.get(bond.issuer_code).get(1);
                        if (bond.frequentlyUsedSector == null) {
                            bond.frequentlyUsedSector = new ArrayList<String>();
                        }
                        if (map.keySet().contains("\"" + bond.sector + "\"")) {
                        	bond.frequentlyUsedSector.add(bond.sector);
						} else {
							for(String key : map.keySet()) {
								ArrayList<String> arrayList = map.get(key);
								if (arrayList.contains("\"" + bond.sector + "\"")) {
									bond.frequentlyUsedSector.add(key.split("\"")[1]);
									break;
								}
							}
						}
                        bond.province = issuers.get(bond.issuer_code).get(2);
                    } else {
                        logger.warn("No issuer found for Bond {}, Issuer {}", bond.bond_key, bond.issuer_code);
                    }

                    bond.crossMarket = crossMarketBonds.containsKey(bond.bond_key) ? "1" : "0";

                    // fix newListed before setting specialTypes
                    updateNewListed(bond, recentNthWorkdays);

                    bond.specialTypes = BondPropertyConverter.specialTypes(bond);
                    if (residualMaturities.containsKey(bond.bond_key))
                    {
                        List<String> residualMaturityList = residualMaturities.get(bond.bond_key);
                        String residualMaturityFormat = residualMaturityList.get(0);
                        String residualMaturity = residualMaturityList.get(1);
                        bond.bondResidualMaturityFormat = convertDaysToYears(bond.bond_key, residualMaturityFormat);
                        if (residualMaturityFormat.indexOf(residualMaturity) < 0) {
                            String _residualMaturity = residualMaturityFormat.substring(0, residualMaturityFormat.indexOf('D'));
                            bond.bondResidualMaturity = Double.valueOf(_residualMaturity);
                            bond.bondResidualMaturityLabel = residualMaturityLabel(_residualMaturity);
                        } else {
                            bond.bondResidualMaturity = Double.valueOf(residualMaturity);
                            bond.bondResidualMaturityLabel = residualMaturityLabel(residualMaturity);
                        }
                    }
                    else
                    {
                        // 过期债券
                        continue;
                    }

                    if (StringUtils.equals(bond.coupon_type, "FRN") && !StringUtils.isEmpty(bond.cashflow_paymentdate))
                    {
                        String[] cashFlowPaymentDateArray = bond.cashflow_paymentdate.split("/");
                        if (cashFlowPaymentDateArray.length > 1)
                        {
                            Date firstDate = sdf.parse(cashFlowPaymentDateArray[0]);
                            Date secondDate = sdf.parse(cashFlowPaymentDateArray[1]);
                            if (firstDate.getTime() > now.getTime() && secondDate.getTime() <= now.getTime())
                            {
                                bond.couponType = "03";
                            }
                        }
                    }
                    
                    // 铁道
                    bond.bondSubTypeLevel1 = DictionaryTool.getLevel1FromLevel2(dictionaryRootNode, "bondSubType", bond.bond_subtype, "N");
                    if (bond.bond_subtype.equals("RAB")) {
                        if (bond.frequentlyUsedSector == null) {
                            bond.frequentlyUsedSector = new ArrayList<String>();
                        }
                    	bond.frequentlyUsedSector.add(bond.bond_subtype);
                    }
                    bond.sectorLevel1 = DictionaryTool.getLevel1FromLevel2(dictionaryRootNode, "sector", bond.sector, "");

                    // FIX PPN:
                    if (bond.bond_subtype.equals("PPN"))
                        bond.bond_subtype = (bond.ent_cor == null) ? "PPN" : null;

                    bond.bondKeyListMarket = String.format("%s.%s", bond.bond_key, bond.listed_market); // for cache

                    bonds.put(bond.bondKeyListMarket, bond);
                    count++;
                }
                catch (Exception e)
                {
                    logger.error("Load bond error for exception", e);
                }
            }
            logger.info("BondsTool.updateBondsWithCodes: count {}", count);
        });
    }
}
