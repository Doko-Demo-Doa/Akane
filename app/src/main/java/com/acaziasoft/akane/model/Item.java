package com.acaziasoft.akane.model;

import com.orm.SugarRecord;

import java.util.Date;

public class Item extends SugarRecord {
    private String name;
    private Date uploadDate;
    private Double size;
    private String Url;

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Item(String name, Date uploadDate, Double size, String url) {
        this.name = name;
        this.uploadDate = uploadDate;
        this.size = size;
        Url = url;
    }
}
