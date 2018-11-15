package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public interface MainMenuButtonFunction {


    public void hamburgerMenu(View view);

    public void closeMenu();

    default void addNewItem(View view) {
        closeMenu();

        Context context = view.getContext();

        Log.d("test", "addNewItem: button clicked");
        Log.d("test", context.toString());
        Intent mainMenuIntent = new Intent(context, AddNewItem.class);
        context.startActivity(mainMenuIntent);
    }

    default void browseByRoom(View view) {
        closeMenu();
        //Log.d(TAG, "browseByRoom: button clicked");
        // Need BrowseByRoom class
    }

    default void browseByCategory(View view) {
        closeMenu();
        //Log.d(TAG, "browseByCategory: button clicked");
        // Need BrowseByCategory class
    }

    default void viewAllItems(View view) {
        closeMenu();

        Context context = view.getContext();

        //Log.d(TAG, "viewAllItems: button clicked");
        Intent mainMenuIntent = new Intent(context, ViewAllItems.class);
        context.startActivity(mainMenuIntent);
    }

    default void export(View view) {
        closeMenu();
        Context context = view.getContext();

        Log.d("test", "export: button clicked");
        Log.d("test", context.toString());
        Intent mainMenuIntent = new Intent(context, Export.class);
        context.startActivity(mainMenuIntent);
    }



}
