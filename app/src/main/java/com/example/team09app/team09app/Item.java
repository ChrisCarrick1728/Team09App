package com.example.team09app.team09app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Defines Item Class that will be stored in Database
 * @version 1.0
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

    /** Gets the ID number for an item
     * @return A Long representing the items ID number.
     */
    public Long getMId() { return mId; }

    /** Sets an items ID number
     * @param id A long representing an items ID number
     */
    public void setMId(Long id) {
        this.mId = id;
    }

    /** Gets an items name
     * @return A string representing the name of an item.
     */
    public String getMName() {
        return mName;
    }

    /** Sets and items name
     * @param name A string containing the name of an item.
     */
    public void setMName(String name) { this.mName = name; }

    /** Gets the name of the room the item is stored in.
     * @return A string representing the name of the room.
     */
    public String getMRoom() {
        return mRoom;
    }

    /** Sets the name of the room the item is stored in.
     * @param room A string containing the name of the room.
     */
    public void setMRoom(String room) {
        this.mRoom = room;
    }

    /** Gets the name of the category the item is stored in.
     * @return A string representing the name of the category.
     */
    public String getMCategory() {
        return mCategory;
    }

    /** Sets the name of the category the item is stored in.
     * @param category A string containing the name of the category.
     */
    public void setMCategory(String category) {
        this.mCategory = category;
    }

    /** Gets the date the item was purchased
     * @return A string representing the date the item was purchased.
     */
    public String getMDate() {
        return mDate;
    }

    /** Sets the date an item was purchased.
     * @param date A string containing the date an item was purchased.
     */
    public void setMDate(String date) {
        this.mDate = date;
    }

    /** Gets the price in USD of the item.
     * @return A string representing the price in USD of the item.
     */
    public String getMPrice() {
        return mPrice;
    }

    /** Sets the price in USD of the item.
     * @param price A string containing the price in USD of the item.
     */
    public void setMPrice(String price) {
        this.mPrice = price;
    }

    /** Gets the picture of the item.
     * @return A string representing the URI of the picture of the item.
     */
    public String getMPicture() {
        return mPicture;
    }

    /** Sets the picture of the item
     * @param picture A string containing the URI of the picture of the item.
     */
    public void setMPicture(String picture) {
        this.mPicture = picture;
    }
}