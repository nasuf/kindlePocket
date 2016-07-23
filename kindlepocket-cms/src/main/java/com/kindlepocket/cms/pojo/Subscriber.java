package com.kindlepocket.cms.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

// ignore unknown fields
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class Subscriber {

    private String id;

    private String userName;

    private String phone;

    private String email;

    private String emailPwd;

    private String kindleEmail;

    private Date subscribeDate;

    private Integer isBinded;

    private Date lastChangeDate;

    private Integer isActive;

    public Subscriber() {
    }

    public Subscriber(String id, String userName, String phone, String email, Date subscribeDate, String kindleEmail, String emailPwd, Integer isBinded, Integer isActive) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.subscribeDate = subscribeDate;
        this.kindleEmail = kindleEmail;
        this.emailPwd = emailPwd;
        this.isBinded = isBinded;
        this.lastChangeDate = new Date();
        this.isActive = isActive;
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

    public Integer getIsBinded() {
        return isBinded;
    }

    public void setIsBinded(Integer isBinded) {
        this.isBinded = isBinded;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
                ", isBinded=" + isBinded +
                ", lastChangeDate=" + lastChangeDate +
                ", isActive=" + isActive +
                '}';
    }
}
