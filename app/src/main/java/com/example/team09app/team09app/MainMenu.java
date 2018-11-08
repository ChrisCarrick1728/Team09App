package com.example.team09app.team09app;

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
    }
    
    public void browseByRoom(View view) {
        Log.d(TAG, "browseByRoom: button clicked");
    }
    
    public void browseByCategory(View view) {
        Log.d(TAG, "browseByCategory: button clicked");
    }
    
    public void viewAllItems(View view) {
        Log.d(TAG, "viewAllItems: button clicked");
    }
    
    public void export(View view) {
        Log.d(TAG, "export: button clicked");
    }
}
