package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public interface MainMenuButtonFunction {

    static final String TAG = "MainMenuButtonFunction";

    public void hamburgerMenu(View view);

    public void closeMenu();

    default void addNewItem(View view) {
        closeMenu();

        Context context = view.getContext();

        Log.d(TAG, "addNewItem: button clicked");
        Log.d(TAG, context.toString());
        Intent mainMenuIntent = new Intent(context, AddNewItem.class);
        context.startActivity(mainMenuIntent);
    }

    default void browseByRoom(View view) {
        closeMenu();
        Context context = view.getContext();

        Log.d(TAG, "browseByRoom: button clicked");
        Log.d(TAG, context.toString());
        Intent mainMenuIntent = new Intent(context, BrowseByRoom.class);
        context.startActivity(mainMenuIntent);
    }

    default void browseByCategory(View view) {
        closeMenu();
        Context context = view.getContext();

        Log.d(TAG, "browseByCategory: button clicked");
        Log.d(TAG, context.toString());
        Intent mainMenuIntent = new Intent(context, BrowseByCategory.class);
        context.startActivity(mainMenuIntent);
    }

    default void viewAllItems(View view) {
        closeMenu();

        Context context = view.getContext();

        Log.d(TAG, "viewAllItems: button clicked");
        Intent mainMenuIntent = new Intent(context, ViewAllItems.class);
        context.startActivity(mainMenuIntent);
    }

    default void export(View view) {
        closeMenu();
        Context context = view.getContext();

        Log.d(TAG, "export: button clicked");
        Log.d(TAG, context.toString());
        Intent mainMenuIntent = new Intent(context, Export.class);
        context.startActivity(mainMenuIntent);
    }



}
