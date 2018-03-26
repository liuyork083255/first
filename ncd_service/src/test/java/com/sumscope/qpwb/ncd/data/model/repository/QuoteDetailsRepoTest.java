package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.data.model.db.QuoteDetails;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuoteDetailsRepoTest extends BaseApplicationTest {

    @Autowired
    private QuoteDetailsRepo quoteDetailsRepo;

    @Before
    public void init(){
        quoteDetailsRepo.truncateTable();
        String offerId1 = "123456789";
        String offerId2 = "987654321";
        QuoteDetails qd1 = new QuoteDetails();
        qd1.setFlowId(new Long(1));
        qd1.setIssuePrice(new BigDecimal("123.123"));
        qd1.setOfferId(offerId1);
        qd1.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-21 01:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd1);
        QuoteDetails qd2 = new QuoteDetails();
        qd2.setFlowId(new Long(2));
        qd2.setIssuePrice(new BigDecimal("123.123"));
        qd2.setOfferId(offerId2);
        qd2.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-22 02:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd2);
        QuoteDetails qd3 = new QuoteDetails();
        qd3.setFlowId(new Long(3));
        qd3.setIssuePrice(new BigDecimal("123.123"));
        qd3.setOfferId(offerId1);
        qd3.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-23 02:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd3);
        QuoteDetails qd4 = new QuoteDetails();
        qd4.setFlowId(new Long(4));
        qd4.setIssuePrice(new BigDecimal("100.001"));
        qd4.setId(1000);
        qd4.setOfferId(offerId2);
        qd4.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-24 02:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd4);
        QuoteDetails qd5 = new QuoteDetails();
        qd5.setFlowId(new Long(5));
        qd5.setIssuePrice(new BigDecimal("123.123"));
        qd5.setOfferId(offerId1);
        qd5.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-25 03:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd5);
        QuoteDetails qd6 = new QuoteDetails();
        qd6.setFlowId(new Long(6));
        qd6.setIssuePrice(new BigDecimal("123.000"));
        qd6.setOfferId(offerId2);
        qd6.setModifyTime(Timestamp.valueOf(LocalDateTime.parse("2018-03-24 03:01:01",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        quoteDetailsRepo.save(qd6);


    }

    /**
     * 根据 offerId 和 modifyTime 查询所有记录，然后判断日期是否为指定日期，不符合则测试失败
     * @throws ParseException
     */
    @Test
    public void findByOfferIdOrderByModifyTimeTest() throws ParseException {
        String dateStr = "2018-03-24";
        String offerId = "987654321";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(sdf.parse(dateStr).getTime());

        List<QuoteDetails> quoteDetailsList = quoteDetailsRepo.findByOfferIdOrderByModifyTime(offerId, date);

        Assert.isTrue(quoteDetailsList.size() == 2, "test error, result size greater than 1.");

        QuoteDetails qd1 = quoteDetailsList.get(0);
        QuoteDetails qd2 = quoteDetailsList.get(1);

        Assert.isTrue(qd1.getModifyTime().toLocalDateTime().isBefore(qd2.getModifyTime().toLocalDateTime()),
                "order by modifyTime error.");

    }

    /**
     * 测试根据指定条件查询 id 最大值
     */
    @Test
    public void findIdByOfferIdAndQuoteTimeAndIssuePriceTest(){
        String offerId = "987654321";
        String quoteTime = "02:01:01";
        BigDecimal price = new BigDecimal("100.001");
        Long maxId = quoteDetailsRepo.findIdByOfferIdAndQuoteTimeAndIssuePrice(offerId, quoteTime, price);
        Assert.isTrue(maxId == 1000,"find the max Id error.");
    }
}
