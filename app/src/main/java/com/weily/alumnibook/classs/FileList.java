package com.weily.alumnibook.classs;

public class FileList
{
    private int number;
    private String[] files;

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String[] getFiles()
    {
        return files;
    }

    public void setFiles(String[] files)
    {
        this.files = files;
    }

    public FileList()
    {

    }

    public FileList(int number, String[] files)
    {

        this.number = number;
        this.files = files;
    }
}
