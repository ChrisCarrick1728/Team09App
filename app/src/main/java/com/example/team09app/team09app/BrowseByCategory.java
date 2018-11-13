package com.example.team09app.team09app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class BrowseByCategory extends AppCompatActivity {

    ListView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_category);

        ImageButton addNewCategory = (ImageButton)findViewById(R.id.addNewCategory_btn_id);

        categoryList = (ListView)findViewById(R.id.categoryList);

        addNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
