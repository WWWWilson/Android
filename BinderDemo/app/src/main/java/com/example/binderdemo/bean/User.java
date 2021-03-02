package com.example.binderdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public int userId;
    public String userName;
    public boolean toBind;

    public User(int userId, String userName, boolean toBind) {
        this.userId = userId;
        this.userName = userName;
        this.toBind = toBind;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        toBind = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (toBind ? 1 : 0));
    }
}
