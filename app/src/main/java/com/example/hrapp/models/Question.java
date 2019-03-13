package com.example.hrapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

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

    protected Question(Parcel in) {
        mId = in.readInt();
        mQuestion = in.readString();
        mAnswer = in.readString();
        mLevel = in.readString();
        mPosition = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mQuestion);
        dest.writeString(mAnswer);
        dest.writeString(mLevel);
        dest.writeString(mPosition);
    }
}
