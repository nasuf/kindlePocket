package com.kindlepocket.cms.pojo;

import java.util.Date;

public class Comment {
	
	private String id;
	private String bookId;
	private String subscriberOpenId;
	private Date createdDate;
	private String content;
	private String title;
	

	@Override
	public String toString() {
		return "Comment [id=" + id + ", bookId=" + bookId + ", subscriberOpenId=" + subscriberOpenId + ", createdDate="
				+ createdDate + ", content=" + content + ", title=" + title + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
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
	
	

}
