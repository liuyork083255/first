package com.sumscope.qpwb.ncd.management.timedtask;

import com.sumscope.qpwb.ncd.data.access.cdh.CdhHolidayAccess;
import com.sumscope.qpwb.ncd.data.access.qpwb.QpwbNcdIssuersAccess;
import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import com.sumscope.qpwb.ncd.data.model.iam.IssuerInfo;
import com.sumscope.qpwb.ncd.data.model.repository.QuotesRepo;
import com.sumscope.qpwb.ncd.data.model.repository.UserContactRepo;
import com.sumscope.qpwb.ncd.data.service.NcdIssuersService;
import com.sumscope.qpwb.ncd.utils.DateUtils;
import com.sumscope.service.webbond.common.http.exception.HttpException;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class DataManagementService {
    private static final Logger logger = LoggerFactory.getLogger(DataManagementService.class);
    @Autowired
    NcdIssuersService ncdIssuersService;
    @Autowired
    private CdhHolidayAccess cdhHolidayAccess;
    @Autowired
    private QpwbNcdIssuersAccess qpwbNcdIssuersAccess;
    @Autowired
    private UserContactRepo userContactRepo;
    @Autowired
    private QuotesRepo quotesRepo;
    public static Date currentDate = DateUtils.getCurrentDate();

    /**
     * 每一个小时更新内存里的数据
     */
    @Scheduled(cron = "${qpwb.restful.schedule.load.ncd.issuers}")
    public void timedTaskExecutedEveryOneHourClock() {
        logger.info("start run saveIssuersByQpwb every hour");
        ncdIssuersService.saveIssuersByQpwb();
        logger.info("end run saveIssuersByQpwb every hour");
    }

    /**
     * load holiday from CIB SSZ SZE by restful
     */
    @Scheduled(cron = "00 00 01 * * *")
    public void loadHolidayList() {
        logger.info("load holiday from CIB SSZ SZE by restful start ...");
        cdhHolidayAccess.init();
    }

    /**
     * update user contact
     */
    @Scheduled(cron = "00 00 01 * * *")
    public void updateUserContact() {
        logger.info("update user contact by edm every night ...");
        Map<String, UserContact> userContactMap = QpwbNcdIssuersAccess.cachedUserContactByUserId;
        for (UserContact userContact: userContactMap.values()) {
            IssuerInfo issuerInfoByEdm = qpwbNcdIssuersAccess.getIssuerInfoByEdm(userContact.getUserId());

            //如果用戶所属机构名有变化，修改userContact表
            if (!userContact.getCustomerOrgName().equals(issuerInfoByEdm.getCustomerOrgName())
                    || userContact.getUserName().equals(userContact.getUserName())) {
                logger.info("the user {} info has changed, {} => {}, {} => {} ", userContact.getUserId(),
                        userContact.getUserName(), issuerInfoByEdm.getUsername(),
                        userContact.getCustomerOrgName(), issuerInfoByEdm.getCustomerOrgName());
                userContact.setCustomerOrgName(issuerInfoByEdm.getCustomerOrgName());
                userContact.setUserName(issuerInfoByEdm.getUsername());
                userContactRepo.save(userContact);
            }
        }
    }

    @Scheduled(cron = "${qpwb.restful.schedule,outdated.quotes}")
    public void outdatedQuotes() {
        logger.info("set quotes to outdated");
        quotesRepo.setOutdated();
    }

    @Scheduled(cron = "${application.schedule.refresh.current.date}")
    public void refreshCurrentDate() {
        currentDate = DateUtils.getCurrentDate();
        logger.info("load the current date: {}", currentDate);
    }
}
