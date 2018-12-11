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

    //get all items from a single room
    @Query("SELECT * FROM items_table WHERE ROOM=:room")
    List<Item> getOneRoom(String room);

    //get all items from a single category
    @Query("SELECT * FROM items_table WHERE CATEGORY=:category")
    List<Item> getOneCategory(String category);

    @Query("SELECT room FROM items_table")
    List<Item> getAllRooms();

    @Query("SELECT category FROM items_table")
    List<Item> getAllCategories();

    @Query("SELECT room FROM items_table GROUP BY room HAVING count(*) > 1")
    int getNumRoom();

    @Query("SELECT * FROM items_table GROUP BY ID HAVING count(*) = 1")
    int getNumAllItems();

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Update
    void update(Item item);
}
