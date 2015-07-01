package com.testjob.app.dto;

import android.media.Image;

/**
 * Created by dds on 01.07.15.
 */
public class SubArticle {
    private String title;
    private String description;
    private Image picture;

    public SubArticle(String title, String description, Image picture) {
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

    public Image getPicture() {
        return picture;
    }
}
