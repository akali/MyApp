package com.example.hrapp.models;

public class Favorite {

    private int mId;

    private int mQuestionId;

    private String mUserEmail;

    public Favorite(int id, int questionId, String userEmail) {
        mId = id;
        mQuestionId = questionId;
        mUserEmail = userEmail;
    }

    public Favorite() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail = userEmail;
    }
}
