package com.example.team09app.team09app;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/** This class contains the code for the main menu of the app.
 * @author team 09
 * @version 1.0
 */
public class MainMenu extends AppCompatActivity implements MainMenuButtonFunction {
    private static final String TAG = "MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d(TAG, "Main Menu");
    }

    // Main Menu Button Functions interface methods
    @Override //MainMenuButtonFunction
    public void hamburgerMenu(View view) { } // Not used

    @Override
    public void closeMenu() { } // not used





}
