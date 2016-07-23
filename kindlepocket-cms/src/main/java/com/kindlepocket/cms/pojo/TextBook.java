package com.kindlepocket.cms.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// ignore unknown fields
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class TextBook {

    @Id
    private Long id;

    private String title;

    private String author;

    private Long uploadDate;

    private String uploaderName;

    private Long mailTimes;

    private Long kindleMailTimes;

    private Long downloadTimes;

    private Long size;

    private String format;

    public TextBook() {
        super();
    }

    public TextBook(String title, String author, Long uploadDate, String uploaderName, Long mailTimes,
            Long kindleMailTimes, Long downloadTimes, Long size, String format) {
        super();
        this.title = title;
        this.author = author;
        this.uploadDate = uploadDate;
        this.uploaderName = uploaderName;
        this.mailTimes = mailTimes;
        this.kindleMailTimes = kindleMailTimes;
        this.downloadTimes = downloadTimes;
        this.size = size;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getMailTimes() {
        return mailTimes;
    }

    public void setMailTimes(Long mailTimes) {
        this.mailTimes = mailTimes;
    }

    public Long getKindleMailTimes() {
        return kindleMailTimes;
    }

    public void setKindleMailTimes(Long kindleMailTimes) {
        this.kindleMailTimes = kindleMailTimes;
    }

    public Long getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Long downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


    @Override
    public String toString() {
        return "TextBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", uploadDate=" + uploadDate +
                ", uploaderName='" + uploaderName + '\'' +
                ", mailTimes=" + mailTimes +
                ", kindleMailTimes=" + kindleMailTimes +
                ", downloadTimes=" + downloadTimes +
                ", size=" + size +
                ", format='" + format + '\'' +
                '}';
    }
}
