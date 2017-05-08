package com.weily.alumnibook.classs;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShow {
    private String activityName;
    private String activityContent;
    private String activityDate;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public ActivityShow(String activityName, String activityContent, String activityDate) {

        this.activityName = activityName;
        this.activityContent = activityContent;
        this.activityDate = activityDate;
    }
}