package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.data.model.db.Quotes;
import com.sumscope.qpwb.ncd.management.timedtask.DataManagementServiceTest;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuotesRepoTest extends BaseApplicationTest {
    @Autowired
    private QuotesRepo quotesRepo;


    @Before
    public void init(){
        quotesRepo.truncateTable();
        Quotes q1 = new Quotes();
        q1.setBrokerId("4");
        q1.setIssuerId("1");
        q1.setOutdated(new Byte("1"));
        q1.setIssuerDate(DateUtils.convertToSqlDate("2018-03-12","yyyy-MM-dd"));
        quotesRepo.save(q1);

        Quotes q2 = new Quotes();
        q2.setBrokerId("4");
        q2.setIssuerId("2");
        q2.setOutdated(new Byte("0"));
        q2.setIssuerDate(DateUtils.convertToSqlDate("2018-03-13","yyyy-MM-dd"));
        quotesRepo.save(q2);

        Quotes q3 = new Quotes();
        q3.setBrokerId("4");
        q3.setIssuerId("3");
        q3.setOutdated(new Byte("1"));
        q3.setIssuerDate(DateUtils.convertToSqlDate("2018-03-14","yyyy-MM-dd"));
        quotesRepo.save(q3);

        Quotes q4 = new Quotes();
        q4.setBrokerId("4");
        q4.setIssuerId("4");
        q4.setOutdated(new Byte("0"));
        q4.setIssuerDate(DateUtils.convertToSqlDate("2018-03-15","yyyy-MM-dd"));
        quotesRepo.save(q4);

        Quotes q5 = new Quotes();
        q5.setBrokerId("4");
        q5.setIssuerId("5");
        q5.setOutdated(new Byte("1"));
        q5.setIssuerDate(DateUtils.convertToSqlDate("2018-03-16","yyyy-MM-dd"));
        quotesRepo.save(q5);

        Quotes q6 = new Quotes();
        q6.setBrokerId("4");
        q6.setIssuerId("6");
        q6.setOutdated(new Byte("0"));
        q6.setIssuerDate(DateUtils.convertToSqlDate("2018-03-17","yyyy-MM-dd"));
        quotesRepo.save(q6);

    }

    /**
     * 根据指定条件查询 quotes 记录，并且有排序功能
     * 1 首先根据 first_level_order 降序排列，二级排序根据 second_level_order 降序排序
     * 2 测试点首先是按照条件查询，然后得到所有记录，然后本地再根据这个排序规则进行排序，如果
     *      两者保持一致，则测试通过
     */
    @Test
    public void findByBrokerIdAndIssuerDateOrderByFirstLevelOrderAscSecondLevelOrderAscTest(){
        String brokerId = "4";

        List<Quotes> list = quotesRepo.findByBrokerIdAndIssuerDateOrderByFirstLevelOrderAscSecondLevelOrderAsc(brokerId);

        Assert.isTrue(list.size() == 3,"find quotes error, result is wrong.");

    }
}
