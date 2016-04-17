package com.kindlepocket.cms.pojo;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ignore unknown fields
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscriber {

    @Field("id")
    private String id;

    @Field("subscriberName")
    private String subscriberName;

    @Field("subscribeDate")
    private Date subscribeDate;

    public Subscriber() {
        super();
    }

    public Subscriber(String subscriberName, Date subscribeDate) {
        super();
        this.subscriberName = subscriberName;
        this.subscribeDate = subscribeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public Date getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    @Override
    public String toString() {
        return "Subscriber [id=" + id + ", subscriberName=" + subscriberName + ", subscribeDate="
                + subscribeDate + "]";
    }

}
