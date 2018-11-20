package com.example.team09app.team09app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "items_table")
public class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "room")
    private String mRoom;

    @ColumnInfo(name = "category")
    private String mCategory;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "price")
    private Double mPrice;

    // @ColumnInfo(name = "picture")
    // variable with picture info

    // contructor
    public Item(String name) {this.mName = name;}

    // getters & setters
    public String getName(){return this.mName;}
    public void setName(String name) {this.mName = name;}

    public String getRoom () {return this.mRoom;}
    public void setRoom(String room) {this.mRoom = room;}

    public String getCategory () {return this.mCategory;}
    public void setCategory (String category) {this.mCategory = category;}

    public String getDate () {return this.mDate;}
    public void setDate(String date) {this.mDate = date;}

    public Double getPrice () {return this.mPrice;}
    public void setPrice(Double price) {this.mPrice = price;}
}