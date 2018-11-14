package com.example.team09app.team09app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Export extends AppCompatActivity implements saveCurrentActivity {

    private static final String CURRENT_ACTIVITY = "Export";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        saveCurrent(CURRENT_ACTIVITY);

    }

    @Override
    public void saveCurrent(String currentActivity) {
        /* Context context = getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.CURRENT_STATE), currentActivity);
        editor.commit(); */
    }
}
