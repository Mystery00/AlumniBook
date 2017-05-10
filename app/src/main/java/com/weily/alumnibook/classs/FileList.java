package com.weily.alumnibook.classs;

public class FileList
{
    private int number;
    private MyFile[] files;

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public MyFile[] getFiles()
    {
        return files;
    }

    public void setFiles(MyFile[] files)
    {
        this.files = files;
    }

    public FileList()
    {

    }

    public FileList(int number, MyFile[] files)
    {
        this.number = number;
        this.files = files;
    }
}
