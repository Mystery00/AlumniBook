package com.weily.alumnibook.classs;

import java.io.Serializable;

public class Classmates implements Serializable
{
    private String name;
    private String class_number;
    private String school_number;
    private int sex;
    private String birthday;
    private String address;
    private String work;
    private String home;
    private String withMe;
    private String embarrassing;
    private String remark;
    private int type;
    private int userID;

    public Classmates()
    {
    }

    public Classmates(String name, String class_number, String school_number, int sex, String birthday, String address, String work, String home, String withMe, String embarrassing, String remark, int type, int userID)
    {
        this.name = name;
        this.class_number = class_number;
        this.school_number = school_number;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
        this.work = work;
        this.home = home;
        this.withMe = withMe;
        this.embarrassing = embarrassing;
        this.remark = remark;
        this.type = type;
        this.userID = userID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getClass_number()
    {
        return class_number;
    }

    public void setClass_number(String class_number)
    {
        this.class_number = class_number;
    }

    public String getSchool_number()
    {
        return school_number;
    }

    public void setSchool_number(String school_number)
    {
        this.school_number = school_number;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getWork()
    {
        return work;
    }

    public void setWork(String work)
    {
        this.work = work;
    }

    public String getHome()
    {
        return home;
    }

    public void setHome(String home)
    {
        this.home = home;
    }

    public String getWithMe()
    {
        return withMe;
    }

    public void setWithMe(String withMe)
    {
        this.withMe = withMe;
    }

    public String getEmbarrassing()
    {
        return embarrassing;
    }

    public void setEmbarrassing(String embarrassing)
    {
        this.embarrassing = embarrassing;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }
}
