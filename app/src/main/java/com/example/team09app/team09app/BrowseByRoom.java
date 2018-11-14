package com.example.team09app.team09app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class BrowseByRoom extends AppCompatActivity implements saveCurrentActivity {

    ListView roomList;
    private static final String CURRENT_ACTIVITY = "BrowseByRoom";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_room);
        saveCurrent(CURRENT_ACTIVITY);

        ImageButton addNewRoom = (ImageButton)findViewById(R.id.addNewRoom_btn_id);

        roomList = (ListView)findViewById(R.id.roomList);

        addNewRoom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void saveCurrent(String currentActivity) {
        Context context = getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(getString(R.string.PREFERENCE_FILE_KEY), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.CURRENT_STATE), currentActivity);
        editor.commit();
    }
}