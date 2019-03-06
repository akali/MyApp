package com.example.hrapp.models;

public class Question {

    private int mId;

    private String mQuestion;

    private String mAnswer;

    private String mLevel;

    private String mPosition;

    public Question(int id, String question, String answer, String level, String position) {
        mId = id;
        mQuestion = question;
        mAnswer = answer;
        mLevel = level;
        mPosition = position;
    }

    public Question() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }
}
