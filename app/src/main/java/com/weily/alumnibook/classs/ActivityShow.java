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
    private String studentList;
    private String teacherList;

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

    public String getStudentList()
    {
        return studentList;
    }

    public void setStudentList(String studentList)
    {
        this.studentList = studentList;
    }

    public String getTeacherList()
    {
        return teacherList;
    }

    public void setTeacherList(String teacherList)
    {
        this.teacherList = teacherList;
    }

    public ActivityShow(String activityName, String activityContent, String activityDate, String studentList, String teacherList)
    {

        this.activityName = activityName;
        this.activityContent = activityContent;
        this.activityDate = activityDate;
        this.studentList = studentList;
        this.teacherList = teacherList;
    }
}