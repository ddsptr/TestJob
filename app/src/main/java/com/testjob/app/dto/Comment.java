package com.testjob.app.dto;

/**
 * Created by dds on 01.07.15.
 */
public class Comment {
    private String userName;
    private String text;
    private String date;
    private String avatar;

    private Comment parent;

    public Comment(String userName, String text, String date, String avatar) {
        this.userName = userName;
        this.text = text;
        this.date = date;
        this.avatar = avatar;
    }

    public Comment(String userName, String text, String date, String avatar, Comment parent) {
        this(userName, text, date, avatar);
        this.parent = parent;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getAvatar() {
        return avatar;
    }
}
