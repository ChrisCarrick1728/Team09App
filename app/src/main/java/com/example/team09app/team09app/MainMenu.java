package com.example.team09app.team09app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    private static final String TAG = "Main_Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
    
    public void addNewItem(View view) {

        Log.d(TAG, "addNewItem: button clicked");
        Intent mainMenuIntent = new Intent(this, AddNewItem.class);
        startActivity(mainMenuIntent);
    }
    
    public void browseByRoom(View view) {

        Log.d(TAG, "browseByRoom: button clicked");
        Intent mainMenuIntent = new Intent(this, BrowseByRoom.class);
        startActivity(mainMenuIntent);
    }
    
    public void browseByCategory(View view) {

        Log.d(TAG, "browseByCategory: button clicked");
        Intent mainMenuIntent = new Intent(this, BrowseByCategory.class);
        startActivity(mainMenuIntent);
    }
    
    public void viewAllItems(View view) {

        Log.d(TAG, "viewAllItems: button clicked");
        Intent mainMenuIntent = new Intent(this, ViewAllItems.class);
        startActivity(mainMenuIntent);
    }
    
    public void export(View view) {

        Log.d(TAG, "export: button clicked");
        Intent mainMenuIntent = new Intent(this, Export.class);
        startActivity(mainMenuIntent);
    }
}
