package com.example.bryan.togatheradproject;

import java.util.Date;

public class Promotion {
    private String location;
    private String detail;
    private Date date;

    public Promotion() {
    }

    public Promotion(String location, String detail, Date date) {
        this.location = location;
        this.detail = detail;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
