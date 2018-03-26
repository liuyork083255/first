package com.sumscope.cdh.realtime.transfer.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liu.yang on 2017/11/13.
 */
@Component
public class EmailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${spring.mail.to.direct}")
    private String toDirect;
    @Value("${spring.mail.to.cc}")
    private String toCC;
    @Value("${spring.mail.to.bcc}")
    private String toBCC;
    @Value("${spring.mail.come.from}")
    private String fromFlag;

    private static AtomicBoolean MAIL_FLAG = new AtomicBoolean(false);


    @Scheduled(cron = "00 00 07 * * 2-6")
    public void startSetMailFlag(){
        MAIL_FLAG.set(false);
    }


    @Scheduled(cron = "${mail.monitor.check1}")
    public void checkFirst(){
        LOGGER.info("First check schedule start ...");
        if(MAIL_FLAG.get()){
            LOGGER.info("first check market quotations is TRUE");
        }else{
            sendEmail(fromFlag + "First");
        }
    }

    @Scheduled(cron = "${mail.monitor.check2}")
    public void checkSecond(){
        LOGGER.info("Second check schedule start ...");
        if(MAIL_FLAG.get()){
            LOGGER.info("second check market quotations is TRUE");
        }else{
            sendEmail(fromFlag + "Second");
        }
    }

    private void sendEmail(String type){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            if(toDirect.length() > 0)
                message.setTo(toDirect.split(";"));
            if(toCC.length() > 0)
                message.setCc(toCC.split(";"));
            if(toBCC.length() > 0)
                message.setBcc(toBCC.split(";"));
            message.setSubject("主题：transfer 行情监控");
            message.setText(type + " 点检无行情！");
            mailSender.send(message);
            LOGGER.info("send email success");
        } catch (Exception e) {
            LOGGER.error("send email error.   exception={}",e.getMessage());
        }
    }


    public boolean getMailFlag() {
        return MAIL_FLAG.get();
    }

    public void setMailFlag(boolean mailFlag) {
        MAIL_FLAG.set(mailFlag);
    }
}
