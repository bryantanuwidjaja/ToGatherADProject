package com.example.bryan.togatheradproject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String password;
    private String userName;
    private String userEmail;
    private int userRating=0;
    //private userProfilepic;
    private ArrayList<String> userInterests;
    private String userID;

    public User() {
    }

    public User(String password, String userName, String userEmail, int userRating, ArrayList<String> userInterests, String userID) {
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRating = userRating;
        this.userInterests = userInterests;
        this.userID = null;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
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

    public ArrayList<String> getUserInterests() {
        return userInterests;
    }

    public void setUserInterests(ArrayList<String> userInterests) {
        this.userInterests = userInterests;
    }
}
