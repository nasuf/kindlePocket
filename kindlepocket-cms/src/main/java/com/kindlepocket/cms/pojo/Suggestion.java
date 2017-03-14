package com.kindlepocket.cms.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Suggestion {
	
	@Id
	private String id;
	private String subscriberOpenId;
	private Date createdDate;
	private String content;
	private String callback;
	
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	@Override
	public String toString() {
		return "Suggestion [id=" + id + ", subscriberOpenId=" + subscriberOpenId + ", createdDate=" + createdDate
				+ ", content=" + content + ", callback=" + callback + "]";
	}
	
	

}
