package com.example.task41;

public class Task {
    private int id;
    private String title;
    private String description;
    private String time;
    private String dueDate;

    //with ID
    public Task(int id, String title, String description, String time, String dueDate)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.dueDate = dueDate;
    }

    //Without ID
    public Task(String title, String description, String time, String dueDate)
    {
        this.title = title;
        this.description = description;
        this.time = time;
        this.dueDate = dueDate;
    }


    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}