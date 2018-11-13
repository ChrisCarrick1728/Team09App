package com.example.team09app.team09app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // This is our team project
        // Hello from Amy.
        // This is Chris William Carrick 7/6

        // This is Amy Cook

        // This is Ian Stubbs 01/16

        //This is Austin 
    }

    public void clickContinueButton(View view) {

        Log.d("Main_Activity", "Button Clicked");
        Intent mainMenuIntent = new Intent(this, MainMenu.class);
        startActivity(mainMenuIntent);
    }

}
