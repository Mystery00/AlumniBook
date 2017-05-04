package com.weily.alumnibook.classs;

import java.io.Serializable;
import java.util.List;

public class Email implements Serializable
{
    private int studentID;
    private List<String> emailList;

    public Email()
    {
    }

    public Email(int studentID, List<String> emailList)
    {
        this.studentID = studentID;
        this.emailList = emailList;
    }

    public int getStudentID()
    {
        return studentID;
    }

    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }

    public List<String> getEmailList()
    {
        return emailList;
    }

    public void setEmailList(List<String> emailList)
    {
        this.emailList = emailList;
    }
}
