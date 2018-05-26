package com.example.sergeyvankovich.letsdoit.DB;

public class Task {
    private String name;
    private int year;
    private int month;
    private int day;
    private int absoluteID;
    private String important;


    public Task(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Task(String name, int year, int month, int day, int absoluteID) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.absoluteID = absoluteID;
        this.important = important;
    }

    public int getAbsoluteID() {
        return absoluteID;
    }

    public void setAbsoluteID(int absoluteID) {
        this.absoluteID = absoluteID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }
}
