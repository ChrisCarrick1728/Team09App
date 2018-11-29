package com.example.team09app.team09app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Defines Item Class that will be stored in Database
 *
 * @author Team09
 */

@Entity(tableName = "items_table")
public class Item implements Serializable {

    /**  mID: Will hold an automatically generated id number for each item in database*/
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private Long mId;

    /** mName: Stores the name of an item */
    @ColumnInfo(name = "name")
    private String mName;

    /** mRoom: Stores the room that the item is saved in */
    @ColumnInfo(name = "room")
    private String mRoom;

    /** mCategory: Stores the category that the item is saved in */
    @ColumnInfo(name = "category")
    private String mCategory;

    /** mDate: Stores the date */
    @ColumnInfo(name = "date")
    private String mDate;

    /** mPrice: Stores the price of the item */
    @ColumnInfo(name = "price")
    private String mPrice;

    /** mPicture: Stores the URI of the picture */
    @ColumnInfo(name = "picture")
    private String mPicture;

    // Getters and Setters
    // Room DB will only recognize a getter with the exact variable name except starting with a capital letter

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