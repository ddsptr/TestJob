package com.testjob.app.dto;

/**
 * Created by dds on 01.07.15.
 */
public class Comment {
    private int id;
    private int parentId;
    private String userName;
    private String text;
    private String date;
    private String avatar;

    public Comment(int id, String userName, String text, String date, String avatar) {
        this.id = id;
        this.userName = userName;
        this.text = text;
        this.date = date;
        this.avatar = avatar;
    }

    public Comment(int id, int parentId, String userName, String text, String date, String avatar) {
        this(id, userName, text, date, avatar);
        this.parentId = parentId;
    }

    public int getId() {
        return id;
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

    public int getParentId() {
        return parentId;
    }
}
