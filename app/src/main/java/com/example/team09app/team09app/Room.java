package com.example.team09app.team09app;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    private String name;

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    protected Room(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Room> CREATOR
            = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };


}
