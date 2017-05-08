package com.weily.alumnibook.classs;

/**
 * Created by myste on 2017/5/9.
 */

public class Teachers
{
    private int number;
    private Teacher[] teachers;

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Teacher[] getTeachers()
    {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers)
    {
        this.teachers = teachers;
    }

    public Teachers()
    {

    }

    public Teachers(int number, Teacher[] teachers)
    {

        this.number = number;
        this.teachers = teachers;
    }
}
