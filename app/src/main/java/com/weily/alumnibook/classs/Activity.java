package com.weily.alumnibook.classs;

/**
 * Created by HS on 2017/5/8.
 */

public class Activity {
    private int number;
    private ActivityShow []activitys;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ActivityShow[] getActivityShow() {
        return activitys;
    }

    public void setActivityShow(ActivityShow[] activitys) {
        this.activitys = activitys;
    }

    public Activity(int number, ActivityShow[] activitys) {

        this.number = number;
        this.activitys = activitys;
    }
}
