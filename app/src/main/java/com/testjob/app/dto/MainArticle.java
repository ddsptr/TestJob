package com.testjob.app.dto;

/**
 * Created by dds on 01.07.15.
 */
public class MainArticle {
    private String title;
    private String description;

    public MainArticle(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
