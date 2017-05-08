package com.weily.alumnibook.classs;

import java.io.Serializable;

/**
 * Created by HS on 2017/5/4.
 */

public class Classmates implements Serializable {
    private String name;
    private String schoolClass;
    private String schoolNumber;
    private int sex;
    private String birthday;
    private String address;
    private String work;
    private String home;
    private String withMe;
    private String embarrassing;
    private String remark;
    private String phoneList;
    private String emailList;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getWithMe() {
        return withMe;
    }

    public void setWithMe(String withMe) {
        this.withMe = withMe;
    }

    public String getEmbarrassing() {
        return embarrassing;
    }

    public void setEmbarrassing(String embarrassing) {
        this.embarrassing = embarrassing;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(String phoneList) {
        this.phoneList = phoneList;
    }

    public String getEmailList() {
        return emailList;
    }

    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Classmates()
    {
    }

    public Classmates(String name, String schoolClass, String schoolNumber, int sex, String birthday, String address, String work, String home, String withMe, String embarrassing, String remark, String phoneList, String emailList, int type, int userID) {

        this.name = name;
        this.schoolClass = schoolClass;
        this.schoolNumber = schoolNumber;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
        this.work = work;
        this.home = home;
        this.withMe = withMe;
        this.embarrassing = embarrassing;
        this.remark = remark;
        this.phoneList = phoneList;
        this.emailList = emailList;
        this.type = type;
    }
}