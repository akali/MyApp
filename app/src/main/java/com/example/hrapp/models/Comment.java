package com.example.hrapp.models;

import android.widget.CompoundButton;

public class Comment {

    private int mId;

    private int mQuestionId;

    private String mUserEmail;

    private String mComment;

    public Comment(int id, int questionId, String userEmail, String comment) {
        mId = id;
        mQuestionId = questionId;
        mUserEmail = userEmail;
        mComment = comment;
    }

    public Comment() {
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

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}
