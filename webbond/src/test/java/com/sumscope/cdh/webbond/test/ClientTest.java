package com.sumscope.cdh.webbond.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.webbond.RedisCacheV2;
import com.sumscope.cdh.webbond.RepoReloadFlag;
import com.sumscope.cdh.webbond.request.ComplexFilter;
import com.sumscope.cdh.webbond.request.BondFilter;
import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.model.*;
import com.sumscope.cdh.webbond.utils.Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by chengzhang.wang on 2017/2/15.
 */
public class ClientTest
{
    @Mock
    private HttpClient httpClient;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(ClientTest.class);
    }

    @Autowired
    private RedisCacheV2 redis;

    @Test
    public void testBestQuoteFilter() throws IOException
    {
        ComplexFilter request = new ComplexFilter();
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://0.0.0.0:5050/webbond/bestQuote").
                bodyString(mapper.writeValueAsString(request), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void testTradeFilter() throws IOException
    {
        ComplexFilter tradeFilter = new ComplexFilter();
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://localhost:5050/webbond/tradeToday").
                bodyString(mapper.writeValueAsString(tradeFilter), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void testBondFilter() throws IOException
    {
        BondFilter bondFilter = createFilter();
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://0.0.0.0:5050/webbond/request").
                bodyString(mapper.writeValueAsString(bondFilter), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void testBondSearch() throws IOException
    {
        BondSearchRequest bondSearchRequest = new BondSearchRequest();
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://0.0.0.0:5050/webbond/bondSearch").
                bodyString(mapper.writeValueAsString(bondSearchRequest), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void testLogon() throws IOException
    {
        LogonRequest logon = new LogonRequest();
        logon.userName = "mt1304";
        logon.passWord = "123456";
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://localhost:5050/webbond/logon").
                bodyString(mapper.writeValueAsString(logon), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void testLogout() throws IOException
    {
        RequestBase logout = new RequestBase();
        ObjectMapper mapper = new ObjectMapper();
        Content content = Request.Post("http://localhost:5050/webbond/logout").
                bodyString(mapper.writeValueAsString(logout), ContentType.APPLICATION_JSON).execute().returnContent();
        System.out.println(content.toString());
    }

    @Test
    public void concurrentSkipListSetTest() throws Exception
    {
        ConcurrentSkipListSet<WebbondBbo> set = new ConcurrentSkipListSet<>((o1, o2) ->
        {
            if (StringUtils.equals(o1.getBondKey(), o2.getBondKey()) && StringUtils.equals(o1.getListedMarket(), o2.getListedMarket()) && StringUtils.equals(o1.getBrokerId(), o2.getBrokerId()))
            {
                return 0;
            }
            else
            {
                return ObjectUtils.compare(o2.getUpdateTime(), o1.getUpdateTime());
            }
        });
        WebbondBbo entity1 = new WebbondBbo();
        entity1.setBondKey("G0001242016FINPBB10");
        entity1.setListedMarket("CIB");
        entity1.setBrokerId("3");
        entity1.setOfr("5.58");
        WebbondBbo entity2 = new WebbondBbo();
        entity2.setBondKey(new String(new String("G0001242016FINPBB10").getBytes("gbk"), StandardCharsets.UTF_8));
        //entity2.setBondKey(new String("G0001242016FINPBB10"));
        entity2.setListedMarket(new String("CIB"));
        entity2.setBrokerId("3");
        entity2.setBid("20.00");
        entity2.setOfr("--");
        WebbondBbo entity3 = new WebbondBbo();
        entity3.setBondKey(new String(new String("G0001242016FINPBB10").getBytes("gbk"), StandardCharsets.UTF_8));
        //entity2.setBondKey(new String("G0001242016FINPBB10"));
        entity3.setListedMarket(new String("CIB"));
        entity3.setBrokerId("3");
        entity3.setBid("20.00000");
        entity3.setOfr("--");

        assert set.add(entity1);
        assert set.remove(entity3);
        assert set.add(entity2);
        assert !set.add(entity1);
        assert set.size() == 1;

        String st1 = "{\"code\":0,\"message\":\"OK\",\"result\":[{\"bondKey\":\"S0000222016CORLSP06\",\"listedMarket\":\"CIB\",\"brokerId\":\"3\",\"remaintTime\":\"24D\",\"bondCode\":\"011698600.IB\",\"shortName\":\"16鲁钢铁SCP006\",\"latestTransaction\":\"--\",\"bidVolume\":\"0\",\"bid\":\"Bid\",\"bidBarginFlag\":0,\"ofr\":\"Ofr\",\"cdcValuation\":\"--\",\"csiValuation\":\"3.2198\",\"issuerBondRating\":\"AAA/--\",\"expection\":\"稳定\",\"ratingInstitutionName\":\"--\",\"bidSubCdc\":\"--\",\"cdcSubOfr\":\"--\",\"bidSubCsi\":\"--\",\"csiSubOfr\":\"--\",\"brokerName\":\"中诚\",\"updateTime\":\"16:41:05\",\"duration\":\"--\",\"ofrBarginFlag\":0,\"ofrVolume\":\"0\",\"bidPriceComment\":\"\",\"ofrPriceComment\":\"\",\"remaintTimeValue\":24,\"latestTransactionValue\":-999.0,\"bidVolumeValue\":0.0,\"ofrVolumeValue\":0.0,\"bidValue\":-998.0,\"ofrValue\":-998.0,\"cdcValuationValue\":-999.0,\"csiValuationValue\":3.2198,\"bidSubCdcValue\":-999.0,\"bidSubCsiValue\":-999.0,\"cdcSubOfrValue\":-999.0,\"csiSubOfrValue\":-999.0,\"updateDateTime\":\"2017-03-27 16:41:05\",\"createDateTime\":\"2017-03-27 16:41:06\"},{\"bondKey\":\"S0000222016CORLSP06\",\"listedMarket\":\"CIB\",\"brokerId\":\"3\",\"remaintTime\":\"24D\",\"bondCode\":\"011698600.IB\",\"shortName\":\"16鲁钢铁SCP006\",\"latestTransaction\":\"--\",\"bidVolume\":\"0\",\"bid\":\"Bid\",\"bidBarginFlag\":0,\"ofr\":\"Ofr\",\"cdcValuation\":\"--\",\"csiValuation\":\"3.2198\",\"issuerBondRating\":\"AAA/--\",\"expection\":\"稳定\",\"ratingInstitutionName\":\"--\",\"bidSubCdc\":\"--\",\"cdcSubOfr\":\"--\",\"bidSubCsi\":\"--\",\"csiSubOfr\":\"--\",\"brokerName\":\"中诚\",\"updateTime\":\"16:27:03\",\"duration\":\"--\",\"ofrBarginFlag\":0,\"ofrVolume\":\"1000\",\"bidPriceComment\":\"\",\"ofrPriceComment\":\"\",\"remaintTimeValue\":24,\"latestTransactionValue\":-999.0,\"bidVolumeValue\":0.0,\"ofrVolumeValue\":1000.0,\"bidValue\":-998.0,\"ofrValue\":-998.0,\"cdcValuationValue\":-999.0,\"csiValuationValue\":3.2198,\"bidSubCdcValue\":-999.0,\"bidSubCsiValue\":-999.0,\"cdcSubOfrValue\":-999.0,\"csiSubOfrValue\":-999.0,\"updateDateTime\":\"2017-03-27 16:27:03\",\"createDateTime\":\"2017-03-27 16:27:04\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        ResponseBase<List<WebbondBbo>> responseBase = mapper.readValue(st1, ResponseBase.class);
        WebbondBbo b1 = mapper.convertValue(responseBase.getResult().get(0), WebbondBbo.class);
        WebbondBbo b2 = mapper.convertValue(responseBase.getResult().get(0), WebbondBbo.class);
        assert set.add(b1);
        assert set.add(b2);
    }

    @Test
    public void concurrentSkipListSetTest2() throws Exception
    {
        WebbondBbo entity1 = new WebbondBbo();
        entity1.setBondKey("T0000862012CORLEB01");
        entity1.setListedMarket("CIB");
        entity1.setBrokerId("1");
        entity1.setUpdateTime("10:36:04");
        entity1.setOfrVolume("200");
        entity1.setOfrVolumeValue(200D);
        WebbondBbo entity2 = new WebbondBbo();
        entity2.setBondKey("T0000862012CORLEB01");
        entity2.setListedMarket("CIB");
        entity2.setBrokerId("1");
        entity2.setUpdateTime("09:56:25");
        entity2.setOfrVolume("--");
        entity2.setOfrVolumeValue(-999D);
        ConcurrentSkipListSet<WebbondBbo> set = new ConcurrentSkipListSet<>((o1, o2) ->
        {
            if (StringUtils.equals(o1.getBondKey(), o2.getBondKey()) && StringUtils.equals(o1.getListedMarket(), o2.getListedMarket()) && StringUtils.equals(o1.getBrokerId(), o2.getBrokerId()))
            {
                return 0;
            }
            else
            {
                return ObjectUtils.compare(o2.getUpdateTime(), o1.getUpdateTime());
            }
        });
        assert set.add(entity1);
        assert !set.add(entity2);
        assert set.size() == 1;
    }

    @Test
    public void concurrentSkipListSetTest3() throws InterruptedException
    {
        ConcurrentSkipListSet<WebbondBbo> set = new ConcurrentSkipListSet<>((o1, o2) ->
        {
            if (StringUtils.equals(o1.getBondKey(), o2.getBondKey()) && StringUtils.equals(o1.getListedMarket(), o2.getListedMarket()) && StringUtils.equals(o1.getBrokerId(), o2.getBrokerId()))
            {
                return 0;
            }
            else
            {

                int result = ObjectUtils.compare(o2.getUpdateTime(), o1.getUpdateTime());
                return result == 0 ? 1 : result;
            }
        });
        WebbondBbo entity1 = new WebbondBbo();
        entity1.setBondKey("T0000862012CORLEB01");
        entity1.setListedMarket("CIB");
        entity1.setBrokerId("1");
        entity1.setUpdateTime("10:36:04");
        entity1.setOfrVolume("200");
        entity1.setOfrVolumeValue(200D);
        set.add(entity1);
        WebbondBbo entity2 = new WebbondBbo();
        entity2.setBondKey("T0000862012CORLEB01");
        entity2.setListedMarket("CIB");
        entity2.setBrokerId("12");
        entity2.setUpdateTime("10:36:04");
        entity2.setOfrVolume("--");
        entity2.setOfrVolumeValue(-999D);
        set.add(entity2);
        assert set.size() == 2;
    }

    @Test
    public void testReload()
    {

        assert RepoReloadFlag.STATICINFO.contains(RepoReloadFlag.NONE);
        assert RepoReloadFlag.STATICINFO.contains(RepoReloadFlag.DICTIONARY);
        assert RepoReloadFlag.STATICINFO.contains(RepoReloadFlag.BONDS);
        assert RepoReloadFlag.STATICINFO.contains(RepoReloadFlag.USER_ACCOUNTS);

        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.NONE);
        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.DICTIONARY);
        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.BONDS);
        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.USER_ACCOUNTS);
        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.BESTQUOTES);
        assert RepoReloadFlag.ALL.contains(RepoReloadFlag.TRADESTODAY);

    }

    private static BondFilter createFilter()
    {
        BondFilter filter = new BondFilter();
        filter.setSsSecCode("");
        filter.setQbBondTypes(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        filter.setBondSubTypes(Arrays.asList("BGB", "SCB", "EGB"));
        filter.setBondResidualMaturities(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
        filter.setBondResidualMaturityStart("100D");
        filter.setBondResidualMaturityEnd("2.1Y");
        filter.setBondMaturityDateStart("20150101");
        filter.setBondMaturityDateEnd("20161231");
        filter.setPerpetualTypes(Arrays.asList("01", "02"));
        filter.setIssuers(Arrays.asList("A000001", "S000077", "T000068"));
        filter.setIssuerTypes(Arrays.asList("CGE", "LGE", "PVE", "OTE"));
        filter.setMunicipalTypes(Arrays.asList("01", "02", "03"));
        filter.setIssuerRatings(Arrays.asList("01", "02", "03", "04", "05", "06", "07"));
        filter.setBondRatings(Arrays.asList("01", "02", "03", "04", "05", "06", "07"));
        filter.setRatingAugments(Arrays.asList("GUA", "NON", "SEC"));
        filter.setFinancialBondTypes(Arrays.asList("01", "02"));
        filter.setCorporateBondTypes(Arrays.asList("01", "02"));
        filter.setCouponTypes(Arrays.asList("01", "02", "03"));
        filter.setOptionTypes(Arrays.asList("01", "02"));
        filter.setSectors(Arrays.asList("CJY0201", "CMY0101", "DQS0101"));
        filter.setProvinces(Arrays.asList("AHPRN", "BJPRN", "CQPRN"));
        filter.setIssueYears(Arrays.asList("2016", "2015", "2014", "2013", "2012", "2011"));
        filter.setSpecialTypes(Arrays.asList("01", "02", "03", "04", "05"));
        return filter;
    }

    @Test
    public void testCouponType() throws ParseException
    {
        Bond bond = new Bond();
        bond.coupon_type = "FRN";
        bond.couponType = "01";
        bond.cashflow_paymentdate = "2016-11-22 00:00:00/2015-11-22 00:00:00/2014-11-22 00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        if (bond.coupon_type.equals("FRN"))
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
        assert !bond.couponType.equals("03");
    }

    @Test
    public void chineseCompare()
    {
        Collator collator = Collator.getInstance(Locale.CHINA);
        assert 0 < collator.compare("你", "nai");
        assert 0 < collator.compare("他", "你");
    }

    @Test
    public void compareShortName()
    {
        assert Utils.compareExpection("稳定", "稳定") == 0;
        assert Utils.compareExpection("稳定", "正面") > 0;
        assert Utils.compareExpection("正面", "负面") > 0;
        assert Utils.compareExpection("负面", "列入观察名单") > 0;
        assert Utils.compareChinese("16我你他", "16你我他") > 0;
        assert Utils.compareChinese("16我你他", "16他你我") > 0;
        assert Utils.compareChinese("--", "--") == 0;
        assert Utils.compareChinese("gg", "--") > 0;
        assert Utils.compareChinese("--", "我") < 0;
    }


}
