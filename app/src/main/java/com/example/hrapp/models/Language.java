package com.example.hrapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {

    private int mId;

    private String mTitle;

    public Language(int id, String title) {
        mId = id;
        mTitle = title;
    }

    public Language() {
    }

    protected Language(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
    }

    public static final Creator<Language> CREATOR = new Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
    }
}
