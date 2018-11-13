package com.example.team09app.team09app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class BrowseByRoom extends AppCompatActivity {

    ListView roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_room);

        ImageButton addNewRoom = (ImageButton)findViewById(R.id.addNewRoom_btn_id);

        roomList = (ListView)findViewById(R.id.roomList);

        addNewRoom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}