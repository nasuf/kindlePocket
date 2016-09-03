package com.kindlepocket.cms.service;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private static Logger logger = Logger.getLogger(MailService.class);

    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_HOST_VALUE = "smtp.163.com";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_TRANSPORT_PROTOCOL_VALUE = "smtp";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_AUTH_VALUE = "true";
    private static final String MAIL_CONTENT_FORMAT_CHARSET = "text/html;charset=UTF-8";

    @Autowired
    private GridFSService gridFSService;


    public static void sendSimpleMail(String title) {
        Properties prop = new Properties();
        prop.setProperty(MAIL_HOST, MAIL_HOST_VALUE);
        prop.setProperty(MAIL_TRANSPORT_PROTOCOL, MAIL_TRANSPORT_PROTOCOL_VALUE);
        prop.setProperty(MAIL_SMTP_AUTH, MAIL_SMTP_AUTH_VALUE);
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
                logger.error("email simple send failed!", e);
            }
        }
    }

    public void sendFileAttachedMail(String fromMail, String toMail, String fromMailPwd, String bookId) {
        Properties prop = new Properties();
        prop.setProperty(MAIL_HOST, MAIL_HOST_VALUE);
        prop.setProperty(MAIL_TRANSPORT_PROTOCOL, MAIL_TRANSPORT_PROTOCOL_VALUE);
        prop.setProperty(MAIL_SMTP_AUTH, MAIL_SMTP_AUTH_VALUE);
        Session session = Session.getInstance(prop);
        //session.setDebug(true);
        try {
            Transport ts = session.getTransport();
            String fromMailPrefix = fromMail.split("@")[0];
            ts.connect(MAIL_HOST_VALUE,fromMailPrefix, fromMailPwd);
            //String fromAdd = "binglingxueyou1031@163.com";
            //String toAdd = "842100455@qq.com";
            String subject = "FILE ATTACHED MAIL TEST";
            String content = "Mail Content RE";
            String fileSavePath = "E://attachMail.eml";
            Message message = createFileAttachedMail(session, fromMail, toMail, subject, content, bookId, fileSavePath);
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("send fileAttachedMail failed!",e);
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
        message.setContent(content, MAIL_CONTENT_FORMAT_CHARSET);
        // 返回创建好的邮件对象
        return message;
    }

    public MimeMessage createFileAttachedMail(Session session, String fromAdd, String toAdd, String subject, String content, String fileObjectId, String fileSavePath) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAdd));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAdd));
        message.setSubject(subject);
        // 邮件正文
        MimeBodyPart text  = new MimeBodyPart();
        text.setContent(content, MAIL_CONTENT_FORMAT_CHARSET);

        // 附件
        MimeBodyPart attach = new MimeBodyPart();
        File file = this.gridFSService.readFiles(fileObjectId);
        DataHandler handler = new DataHandler(new FileDataSource(file));
        attach.setDataHandler(handler);
        attach.setFileName(MimeUtility.encodeText(handler.getName()).replaceAll("\r", "").replaceAll("\n", ""));
        //attach.setHeader("Content-Type", "text/plain");

        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        //将创建的email写入到本地存储
        //message.writeTo(new FileOutputStream(fileSavePath));
        return message;
    }


}
