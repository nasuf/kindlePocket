package com.kindlepocket.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

// ignore unknown fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscriber {

    private String id;

    private String userName;

    private String phone;

    private String email;

    private String emailPwd;

    private String kindleEmail;

    private Date subscribeDate;

    public Subscriber() {
    }

    public Subscriber(String id, String userName, String phone, String email, Date subscribeDate, String kindleEmail, String emailPwd) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.subscribeDate = subscribeDate;
        this.kindleEmail = kindleEmail;
        this.emailPwd = emailPwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    public String getKindleEmail() {
        return kindleEmail;
    }

    public void setKindleEmail(String kindleEmail) {
        this.kindleEmail = kindleEmail;
    }

    public Date getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", emailPwd='" + emailPwd + '\'' +
                ", kindleEmail='" + kindleEmail + '\'' +
                ", subscribeDate=" + subscribeDate +
                '}';
    }
}
