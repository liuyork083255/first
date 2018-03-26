package com.sumscope.qpwb.ncd.management.timedtask;

import com.sumscope.qpwb.ncd.BaseApplicationTest;
import com.sumscope.qpwb.ncd.data.model.db.Quotes;
import com.sumscope.qpwb.ncd.data.model.repository.QuotesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class DataManagementServiceTest extends BaseApplicationTest{
    private static int count = 0;
    @Autowired
    private QuotesRepo quotesRepo;
    @Autowired
    private DataManagementService dataManagementService;

    /**
     * 首先获取全量数据，然后执行更新操作，然后再去查询一遍，如果在更新后还有状态为0的，则测试失败
     * 为了对测试数据不造成影响，需要还原最初的状态
     */
    @Test
    public void outdatedQuotesTest(){
        List<Quotes> initTemp = quotesRepo.findAll();
        System.out.println("update before ...");
        print(initTemp,0);
        print(initTemp,1);
        dataManagementService.outdatedQuotes();
        List<Quotes> updateAll = quotesRepo.findAll();
        System.out.println("update after ...");
        print(updateAll,0);
        print(updateAll,1);
        quotesRepo.save(initTemp);

        // 将 recommend 字段改为 outdated 即可
        List<Quotes> filterAll = updateAll.stream().filter(
                quotes -> quotes.getOutdated().intValue() == 0 ? true : false)
                .collect(Collectors.toList());

        Assert.isTrue(filterAll.size() == 0,"update quotes'outdated field error.");

    }

    public static void print(List<Quotes> list, int n){
        count = 0;
        list.forEach(l ->{
            if(l.getOutdated() == n) count++;
        });
        String format = String.format("outdated = %d size is : %d", n, count);
        System.out.println(format);
    }
}
