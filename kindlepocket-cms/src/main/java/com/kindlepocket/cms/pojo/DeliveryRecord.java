package com.kindlepocket.cms.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by admin on 2016/9/4.
 */
@Document
public class DeliveryRecord {

    @Id
    private String id;
    private String subscriberOpenId;
    private String textBookId;
    private Date deliveryDate;
    private Integer isDelivered;
    private String fromEmailAdd;
    private String toEmailAdd;
    private String bookTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriberOpenId() {
        return subscriberOpenId;
    }

    public void setSubscriberOpenId(String subscriberOpenId) {
        this.subscriberOpenId = subscriberOpenId;
    }

    public String getTextBookId() {
        return textBookId;
    }

    public void setTextBookId(String textBookId) {
        this.textBookId = textBookId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Integer isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getFromEmailAdd() {
        return fromEmailAdd;
    }

    public void setFromEmailAdd(String fromEmailAdd) {
        this.fromEmailAdd = fromEmailAdd;
    }

    public String getToEmailAdd() {
        return toEmailAdd;
    }

    public void setToEmailAdd(String toEmailAdd) {
        this.toEmailAdd = toEmailAdd;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public String toString() {
        return "DeliveryRecord{" +
                "id='" + id + '\'' +
                ", subscriberOpenId='" + subscriberOpenId + '\'' +
                ", textBookId='" + textBookId + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", isDelivered=" + isDelivered +
                ", fromEmailAdd='" + fromEmailAdd + '\'' +
                ", toEmailAdd='" + toEmailAdd + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                '}';
    }
}
