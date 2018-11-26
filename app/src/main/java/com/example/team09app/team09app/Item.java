package com.example.team09app.team09app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "items_table")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Long mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "room")
    private String mRoom;

    @ColumnInfo(name = "category")
    private String mCategory;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "price")
    private String mPrice;

    @ColumnInfo(name = "picture")
    private String mPicture;

    // Getters and Setters
    public Long getMId() { return mId; }
    public void setMId(Long id) {
        this.mId = id;
    }

    public String getMName() {
        return mName;
    }
    public void setMName(String name) { this.mName = name; }

    public String getMRoom() {
        return mRoom;
    }
    public void setMRoom(String room) {
        this.mRoom = room;
    }

    public String getMCategory() {
        return mCategory;
    }
    public void setMCategory(String category) {
        this.mCategory = category;
    }

    public String getMDate() {
        return mDate;
    }
    public void setMDate(String date) {
        this.mDate = date;
    }

    public String getMPrice() {
        return mPrice;
    }
    public void setMPrice(String price) {
        this.mPrice = price;
    }

    public String getMPicture() {
        return mPicture;
    }
    public void setMPicture(String picture) {
        this.mPicture = picture;
    }
}