package com.example.team09app.team09app;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM items_table")
    void deleteAll();

    @Query("SELECT * from items_table ORDER BY name ASC")
    LiveData<List<Item>> getAllItems();
}
