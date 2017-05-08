package com.weily.alumnibook.classs;

public class User {
    private int number;
    private Classmates[] classmates;

    public User(int number, Classmates[] classmates) {
        this.number = number;
        this.classmates = classmates;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Classmates[] getClassmates() {
        return classmates;
    }

    public void setClassmates(Classmates[] classmates) {
        this.classmates = classmates;
    }
}