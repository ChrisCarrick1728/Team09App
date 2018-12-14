package com.example.team09app.team09app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkCurrentActivity();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickContinueButton(View view) {

        Log.d("Main_Activity", "Button Clicked");
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }

    // ToDo: I don't think we used this so can we delete?
    public void checkCurrentActivity() {

    }

}
