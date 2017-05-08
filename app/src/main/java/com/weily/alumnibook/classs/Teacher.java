package com.weily.alumnibook.classs;

import java.io.Serializable;

/**
 * Created by myste on 2017/5/9.
 */

public class Teacher implements Serializable
{
    private String name;
    private int sex;
    private String subject;
    private int type;
    private String birthday;
    private String address;
    private String phoneList;
    private String emailList;
    private String remark;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
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

    public String getPhoneList()
    {
        return phoneList;
    }

    public void setPhoneList(String phoneList)
    {
        this.phoneList = phoneList;
    }

    public String getEmailList()
    {
        return emailList;
    }

    public void setEmailList(String emailList)
    {
        this.emailList = emailList;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Teacher()
    {

    }

    public Teacher(String name, int sex, String subject, int type, String birthday, String address, String phoneList, String emailList, String remark)
    {

        this.name = name;
        this.sex = sex;
        this.subject = subject;
        this.type = type;
        this.birthday = birthday;
        this.address = address;
        this.phoneList = phoneList;
        this.emailList = emailList;
        this.remark = remark;
    }
}
