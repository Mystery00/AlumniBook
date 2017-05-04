package com.weily.alumnibook.classs;

import java.io.Serializable;
import java.util.List;

public class Phone implements Serializable
{
    private int studentID;
    private List<String> phoneList;

    public Phone()
    {
    }

    public Phone(int studentID, List<String> phoneList)
    {
        this.studentID = studentID;
        this.phoneList = phoneList;
    }

    public int getStudentID()
    {
        return studentID;
    }

    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }

    public List<String> getPhoneList()
    {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList)
    {
        this.phoneList = phoneList;
    }
}
