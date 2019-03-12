package com.example.hrapp.models;

public class Language {

    private int mId;

    private String mTitle;

    public Language(int id, String title) {
        mId = id;
        mTitle = title;
    }

    public Language() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
