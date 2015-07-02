package com.testjob.app.dto;

/**
 * Created by dds on 01.07.15.
 */
public class SubArticle {
    private String title;
    private String description;
    private String picture;

    public SubArticle(String title, String description, String picture) {
        this.title = title;
        this.description = description;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }
}
