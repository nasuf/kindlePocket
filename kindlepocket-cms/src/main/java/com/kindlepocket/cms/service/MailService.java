package com.kindlepocket.cms.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private static Logger logger = Logger.getLogger(MailService.class);

    public static void sendMail(String title) {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        // 使用JavaMail发送邮件的5个步骤
        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        try {
            // 2、通过session得到transport对象
            Transport ts = session.getTransport();
            // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect("smtp.163.com", "binglingxueyou1031", "blxyst103166");
            // 4、创建邮件
            Message message = createSimpleMail(session, title + "'s book",
                    "please do not response this email!");
            // 5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            logger.info("email send successfully");
            ts.close();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("email send failed!", e);
            }
        }
    }

    public static MimeMessage createSimpleMail(Session session, String subject, String content)
            throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("binglingxueyou1031@163.com"));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("840104066@qq.com"));
        // 邮件的标题
        message.setSubject(subject);
        // 邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }
}
