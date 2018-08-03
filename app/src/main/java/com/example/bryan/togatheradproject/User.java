package com.example.bryan.togatheradproject;

public class User {
    private String userID;
    private String userName;
    private String userEmail;
    private int userRating=0;
    //private userProfilepic;
    private String[]userInterests;

    public User(){};

    public User(String userID, String userName, String userEmail, String[] userInterests) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userInterests = userInterests;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserRating() {
        return userRating;
    }

    public void rateUser() {
        this.userRating++;
    }

    public String[] getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(String[] userInterests) {
        this.userInterests = userInterests;
    }
}
