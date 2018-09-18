package com.example.bryan.togatheradproject;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class Promotion {
    private String detail;
    private String activity;
    private int drawable;

    public Promotion() {
    }

    public Promotion(String detail, String activity, int drawable) {
        this.detail = detail;
        this.activity = activity;
        this.drawable = drawable;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
