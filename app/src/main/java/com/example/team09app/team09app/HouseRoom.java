package com.example.team09app.team09app;

import android.os.Parcel;
import android.os.Parcelable;

/** This class represents a room where items could be stored
 * @version 1.0
 * @author team 09
 */
public class HouseRoom implements Parcelable {
    private String name;

    /** Gets the name of the room.
     * @return A string representing the name of the room.
     */
    public String getName() {return name;}

    /** Sets the name of the room.
     * @param name A string containing the name of the room.
     */
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
