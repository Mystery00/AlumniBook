package com.weily.alumnibook.classs;

import java.io.Serializable;

/**
 * Created by HS on 2017/5/7.
 */

public class ActivityShow implements Serializable
{
    private String activityName;
    private String activityContent;
    private String activityDate;
    private String userList;

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityContent()
    {
        return activityContent;
    }

    public void setActivityContent(String activityContent)
    {
        this.activityContent = activityContent;
    }

    public String getActivityDate()
    {
        return activityDate;
    }

    public void setActivityDate(String activityDate)
    {
        this.activityDate = activityDate;
    }

    public String getUserList()
    {
        return userList;
    }

    public void setUserList(String userList)
    {
        this.userList = userList;
    }

    public ActivityShow()
    {

    }

    public ActivityShow(String activityName, String activityContent, String activityDate, String userList)
    {

        this.activityName = activityName;
        this.activityContent = activityContent;
        this.activityDate = activityDate;
        this.userList = userList;
    }
}