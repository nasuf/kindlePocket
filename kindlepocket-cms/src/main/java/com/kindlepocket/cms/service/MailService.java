package com.kindlepocket.cms.service;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * Created by nasuf on 16/9/17.
 */
public interface MailService {

    void sendSimpleMail(String title);

    void sendFileAttachedMail(String fromMail, String toMail, String fromMailPwd, String bookId) throws Exception;

    MimeMessage createSimpleMail(Session session, String subject, String content);

    MimeMessage createFileAttachedMail(Session session, String fromAdd, String toAdd,
                                       String subject, String content, String fileObjectId,
                                       String fileSavePath);

}
