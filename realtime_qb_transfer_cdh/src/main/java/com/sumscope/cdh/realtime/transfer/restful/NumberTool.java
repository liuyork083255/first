package com.sumscope.cdh.realtime.transfer.restful;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liu.yang on 2017/10/30.
 */
@Component
public class NumberTool {

    public String convertDaysToYears(String daysStr){
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
        }
        return daysStr;
    }


    private static int getIntDay(String dayStr)
    {
        String temp = dayStr.trim();
        return Integer.parseInt(temp.substring(0, temp.length() - 1));
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
                        return trimTrailingZero(roundTwoDigitsString(_year + yearCnt)) + "Y";
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
                        return trimTrailingZero(roundTwoDigitsString(_year + yearCnt)) + "Y";
                    }
                }
                _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR) + 365) / 365;
                return trimTrailingZero(roundTwoDigitsString(_year + yearCnt)) + "Y";
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
                        return trimTrailingZero(roundTwoDigitsString(_year + yearCnt)) + "Y";
                    }
                }
                _year = (double) (targetCal.get(Calendar.DAY_OF_YEAR) - curCal.get(Calendar.DAY_OF_YEAR)) / 365;
                return trimTrailingZero(roundTwoDigitsString(_year + yearCnt)) + "Y";
            }
        }
        else
        {
            return Integer.toString(days) + "D";
        }
    }

    public static String trimTrailingZero(String s)
    {
        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
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

    public static String roundTwoDigitsString(double value)
    { // round and convert to string
        return String.format("%.2f", value);
    }

}
