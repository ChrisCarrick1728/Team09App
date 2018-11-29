package com.example.team09app.team09app;

import android.os.Parcel;
import android.os.Parcelable;

public class HouseRoom implements Parcelable {
    private String name;

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    protected HouseRoom(Parcel in) {
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

    public static final Parcelable.Creator<HouseRoom> CREATOR
            = new Parcelable.Creator<HouseRoom>() {
        @Override
        public HouseRoom createFromParcel(Parcel in) {
            return new HouseRoom(in);
        }

        public HouseRoom[] newArray(int size) {
            return new HouseRoom[size];
        }
    };


}
