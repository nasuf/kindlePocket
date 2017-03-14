package com.kindlepocket.cms.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SearchRecord {

	@Id
	private String id;
	private Date searchDate;
	private String content;
	private String subscriberOpenId;
	
	@Override
	public String toString() {
		return "SearchRecord [id=" + id + ", searchDate=" + searchDate + ", content=" + content + ", subscriberOpenId="
				+ subscriberOpenId + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubscriberOpenId() {
		return subscriberOpenId;
	}
	public void setSubscriberOpenId(String subscriberOpenId) {
		this.subscriberOpenId = subscriberOpenId;
	}
	
	

}
