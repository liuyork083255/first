package com.sumscope.cdh.realtime.transfer.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by liu.yang on 2017/11/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {
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



    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        if(toDirect.length() > 0)
            message.setTo(toDirect.split(";"));
        if(toCC.length() > 0)
            message.setCc(toCC.split(";"));
        if(toBCC.length() > 0)
            message.setBcc(toBCC.split(";"));
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
    }
}
