package com.example.team09app.team09app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.provider.ContactsContract;

/** This class contains code required for the room database to function.
 * @author team 09.
 * @version 1.0
 */
public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    // our app database object
    private ItemRoomDatabase itemRoomDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        // creating the database with Room database builder
        // HouseItems is the name of the database
        itemRoomDatabase = Room.databaseBuilder(mCtx, ItemRoomDatabase.class, "HouseItems").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if(mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public ItemRoomDatabase getItemRoomDatabase() {
        return itemRoomDatabase;
    }

}

