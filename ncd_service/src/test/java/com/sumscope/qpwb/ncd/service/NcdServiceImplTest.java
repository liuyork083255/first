package com.sumscope.qpwb.ncd.service;

import com.alibaba.fastjson.JSON;
import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.data.access.cdh.CdhHolidayAccess;
import com.sumscope.qpwb.ncd.data.model.db.Quotes;
import com.sumscope.qpwb.ncd.data.model.repository.QuoteDetailsRepo;
import com.sumscope.qpwb.ncd.data.model.repository.QuotesRepo;
import com.sumscope.qpwb.ncd.domain.IssuerDetailDTO;
import com.sumscope.qpwb.ncd.global.constants.NcdConstants;
import com.sumscope.qpwb.ncd.service.service.NcdServiceI;
import com.sumscope.qpwb.ncd.service.service.NcdServiceImpl;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NcdServiceImplTest extends BaseApplicationTest {

    @Autowired
    private QuotesRepo quotesRepo;
    @Autowired
    private NcdServiceI ncdService;
    @Autowired
    private CdhHolidayAccess cdhHolidayAccess;
    @Autowired
    private QuoteDetailsRepo quoteDetailsRepo;

    @Test
    public void getQuoteDetail(){
        quotesRepo.truncateTable();
        insertQuoteDetailSql();

        LocalDate nowDate = LocalDate.now();
        LocalDate beforeDate = nowDate.minusDays(1);
        LocalDate nextDate = nowDate.plusDays(1);

        String issuerCode1 = "N000120";
        String issuerCode2 = "S001019";

        String rateFix = "AAA";
        String rateFloat = "浮息";

        List<Quotes> list01 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1,DateUtils.convertToSqlDate(beforeDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list01.size() == 1 && list01.get(0).getId() == 100,String.format("%s + %s + %s test error.",issuerCode1,beforeDate,rateFix));

        List<Quotes> list02 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1, DateUtils.convertToSqlDate(nowDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list02.size() == 1 && list02.get(0).getId() == 104,String.format("%s + %s + %s test error.",issuerCode1,nowDate,rateFix));

        List<Quotes> list03 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1, DateUtils.convertToSqlDate(nextDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list03.size() == 1 && list03.get(0).getId() == 108,String.format("%s + %s + %s test error.",issuerCode1,beforeDate,rateFix));

        List<Quotes> list04 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1, DateUtils.convertToSqlDate(beforeDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list04.size() == 1 && list04.get(0).getId() == 112,String.format("%s + %s + %s test error.",issuerCode1,beforeDate,rateFloat));

        List<Quotes> list05 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1, DateUtils.convertToSqlDate(nowDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list05.size() == 1 && list05.get(0).getId() == 116,String.format("%s + %s + %s test error.",issuerCode1,nowDate,rateFloat));

        List<Quotes> list06 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode1, DateUtils.convertToSqlDate(nextDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list06.size() == 1 && list06.get(0).getId() == 120,String.format("%s + %s + %s test error.",issuerCode1,beforeDate,rateFloat));

        List<Quotes> list07 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(beforeDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list07.size() == 1 && list07.get(0).getId() == 102,String.format("%s + %s + %s test error.",issuerCode2,beforeDate,rateFix));

        List<Quotes> list08 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(nowDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list08.size() == 1 && list08.get(0).getId() == 106,String.format("%s + %s + %s test error.",issuerCode2,nowDate,rateFix));

        List<Quotes> list09 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(nextDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFix);
        Assert.isTrue(list09.size() == 1 && list09.get(0).getId() == 110,String.format("%s + %s + %s test error.",issuerCode2,beforeDate,rateFix));

        List<Quotes> list10 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(beforeDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list10.size() == 1 && list10.get(0).getId() == 114,String.format("%s + %s + %s test error.",issuerCode2,beforeDate,rateFloat));

        List<Quotes> list11 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(nowDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list11.size() == 1 && list11.get(0).getId() == 118,String.format("%s + %s + %s test error.",issuerCode2,nowDate,rateFloat));

        List<Quotes> list12 = quotesRepo.findByIssuerCodeAndIssuerDateAndRateEquals(issuerCode2, DateUtils.convertToSqlDate(nextDate.toString(),NcdConstants.DATE_YYYY_MM_DD),rateFloat);
        Assert.isTrue(list12.size() == 1 && list12.get(0).getId() == 122,String.format("%s + %s + %s test error.",issuerCode2,beforeDate,rateFloat));
    }

    private void insertQuoteDetailSql(){
        LocalDate nowDate = LocalDate.now();
        LocalDate beforeDate = nowDate.minusDays(1);
        LocalDate nextDate = nowDate.plusDays(1);
        String issuerId = "test001";
        String brokerId = "4";

        String issuerCode1 = "N000120";
        String issuerCode2 = "S001019";

        String outdated0 = "0";
        String outdated1 = "1";

        String rateFix = "AAA";
        String rateFloat = "浮息";

        List<String> list = new ArrayList<>();
        //  issuer_code = ?1 and issuer_date = ?2 and rate = ?3 and outdated = 0
        String sqlTemple = "insert into quotes (id,rate,issuer_id,issuer_date,broker_id,issuer_code,outdated) values (%s,'%s','%s','%s','%s','%s',%s)";
        String sql100 = String.format(sqlTemple,100,rateFix,issuerId,beforeDate,brokerId,issuerCode1,outdated0);
        String sql101 = String.format(sqlTemple,101,rateFix,issuerId,beforeDate,brokerId,issuerCode1,outdated1);
        String sql102 = String.format(sqlTemple,102,rateFix,issuerId,beforeDate,brokerId,issuerCode2,outdated0);
        String sql103 = String.format(sqlTemple,103,rateFix,issuerId,beforeDate,brokerId,issuerCode2,outdated1);
        String sql104 = String.format(sqlTemple,104,rateFix,issuerId,nowDate,brokerId,issuerCode1,outdated0);
        String sql105 = String.format(sqlTemple,105,rateFix,issuerId,nowDate,brokerId,issuerCode1,outdated1);
        String sql106 = String.format(sqlTemple,106,rateFix,issuerId,nowDate,brokerId,issuerCode2,outdated0);
        String sql107 = String.format(sqlTemple,107,rateFix,issuerId,nowDate,brokerId,issuerCode2,outdated1);
        String sql108 = String.format(sqlTemple,108,rateFix,issuerId,nextDate,brokerId,issuerCode1,outdated0);
        String sql109 = String.format(sqlTemple,109,rateFix,issuerId,nextDate,brokerId,issuerCode1,outdated1);
        String sql110 = String.format(sqlTemple,110,rateFix,issuerId,nextDate,brokerId,issuerCode2,outdated0);
        String sql111 = String.format(sqlTemple,111,rateFix,issuerId,nextDate,brokerId,issuerCode2,outdated1);
        String sql112 = String.format(sqlTemple,112,rateFloat,issuerId,beforeDate,brokerId,issuerCode1,outdated0);
        String sql113 = String.format(sqlTemple,113,rateFloat,issuerId,beforeDate,brokerId,issuerCode1,outdated1);
        String sql114 = String.format(sqlTemple,114,rateFloat,issuerId,beforeDate,brokerId,issuerCode2,outdated0);
        String sql115 = String.format(sqlTemple,115,rateFloat,issuerId,beforeDate,brokerId,issuerCode2,outdated1);
        String sql116 = String.format(sqlTemple,116,rateFloat,issuerId,nowDate,brokerId,issuerCode1,outdated0);
        String sql117 = String.format(sqlTemple,117,rateFloat,issuerId,nowDate,brokerId,issuerCode1,outdated1);
        String sql118 = String.format(sqlTemple,118,rateFloat,issuerId,nowDate,brokerId,issuerCode2,outdated0);
        String sql119 = String.format(sqlTemple,119,rateFloat,issuerId,nowDate,brokerId,issuerCode2,outdated1);
        String sql120 = String.format(sqlTemple,120,rateFloat,issuerId,nextDate,brokerId,issuerCode1,outdated0);
        String sql121 = String.format(sqlTemple,121,rateFloat,issuerId,nextDate,brokerId,issuerCode1,outdated1);
        String sql122 = String.format(sqlTemple,122,rateFloat,issuerId,nextDate,brokerId,issuerCode2,outdated0);
        String sql123 = String.format(sqlTemple,123,rateFloat,issuerId,nextDate,brokerId,issuerCode2,outdated1);
        list.add(sql100);list.add(sql101);list.add(sql102);list.add(sql103);list.add(sql104);
        list.add(sql105);list.add(sql106);list.add(sql107);list.add(sql108);list.add(sql109);
        list.add(sql110);list.add(sql111);list.add(sql112);list.add(sql113);list.add(sql114);
        list.add(sql115);list.add(sql116);list.add(sql117);list.add(sql118);list.add(sql119);
        list.add(sql120);list.add(sql121);list.add(sql122);list.add(sql123);

        JdbcUtil.save(list);

    }
}
