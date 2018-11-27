package com.example.team09app.team09app;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items_table")
    List<Item> getAll();

    @Query("SELECT * FROM items_table WHERE ID=:id")
    List<Item> getOne(Long id);

    // ToDo: Use these 2 for BrowseByRoom and BrowseByCategory pages
    @Query("SELECT room FROM items_table")
    List<Item> getAllRooms();

    @Query("SELECT category FROM items_table")
    List<Item> getAllCategories();

    @Insert
    void insert(Item item);

    // ToDo: Use these 2 for adding new rooms and categories?
    @Insert
    void insertRoom(String room);

    @Insert
    void insertCategory(String category);

    @Delete
    void delete(Item item);

    @Update
    void update(Item item);
}
