package com.weily.alumnibook.classs;

public class ShowList
{
    private Classmates[] classmates;
    private Teacher[] teachers;

    public ShowList()
    {
    }

    public Classmates[] getClassmates()
    {
        return classmates;
    }

    public void setClassmates(Classmates[] classmates)
    {
        this.classmates = classmates;
    }

    public Teacher[] getTeachers()
    {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers)
    {
        this.teachers = teachers;
    }

    public ShowList(Classmates[] classmates, Teacher[] teachers)
    {
        this.classmates = classmates;
        this.teachers = teachers;
    }
}
