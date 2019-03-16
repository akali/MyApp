package com.example.hrapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Position implements Parcelable {

    private int mId;

    private String mName;

    public Position(int id, String name) {
        mId = id;
        mName = name;
    }

    public Position() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
    }
}
