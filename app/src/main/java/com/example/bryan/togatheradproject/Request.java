package com.example.bryan.togatheradproject;

import java.io.Serializable;

public class Request implements Serializable {
    private String requestID;
    private User user;
    private String state;

    public Request(String requestID, User user) {
        this.requestID = requestID;
        this.user = user;
    }

    public Request(String requestID, User user, String state) {
        this.requestID = requestID;
        this.user = user;
        this.state = state;
    }

    public Request() {

    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
