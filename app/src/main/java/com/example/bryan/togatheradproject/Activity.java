package com.example.bryan.togatheradproject;

public class Activity {
    private String activityID;
    private String activityName;
    //photo

    public Activity(String activityID, String activityName) {
        this.activityID = activityID;
        this.activityName = activityName;
    }
    public Activity(){}

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
