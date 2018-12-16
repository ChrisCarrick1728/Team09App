package com.example.team09app.team09app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/** this class contains the code for the list items by room display
 * @author team 09
 * @version 1.0
 */
public class ItemsByRoom extends AppCompatActivity implements MainMenuButtonFunction {

    private ImageButton addItemButton;
    private RecyclerView recyclerView;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_by_room);
        Intent intent = getIntent();
        String room = intent.getStringExtra("room");
        // Set the Title
        title = findViewById(R.id.single_room_title_id);
        title.setText(room);

        recyclerView = findViewById(R.id.Item_Viewer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        addItemButton = findViewById(R.id.add_new_item_btn_id);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemsByRoom.this, AddNewItem.class);
                startActivity(intent);
            }
        });

        getTasks(room);
    }

    // call getAll() from ItemDao to get all items stored in database
    private void getTasks(String room) {
        class GetTasks extends AsyncTask<Void, Void, List<Item>> {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> itemList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getItemRoomDatabase()
                    .itemDao()
                    .getOneRoom(room);
                return itemList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);

                SingleRoomAdapter adapter = new SingleRoomAdapter(ItemsByRoom.this, items);
                recyclerView.setAdapter(adapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override // MainMenuButtonFunction
    public void hamburgerMenu(View view) {

        ConstraintLayout mainMenuOverlay = (ConstraintLayout) findViewById(R.id.menu_overlay_id);
        ImageView hamburgerButton = (ImageView) findViewById(R.id.hamburger_menu_id);

        if (mainMenuOverlay.getVisibility() == view.GONE) {
            mainMenuOverlay.setVisibility(view.VISIBLE);
            hamburgerButton.setImageResource(R.drawable.hamburger_close_btnxhdpi);
        } else {
            mainMenuOverlay.setVisibility(view.GONE);
            hamburgerButton.setImageResource(R.drawable.hamburger_btnxhdpi);
        }
    }

    @Override // MainMenuButtonFunction
    public void closeMenu() {
        ConstraintLayout mainMenuOverlay = (ConstraintLayout) findViewById(R.id.menu_overlay_id);
        ImageView hamburgerButton = (ImageView) findViewById(R.id.hamburger_menu_id);

        mainMenuOverlay.setVisibility(View.GONE);
        hamburgerButton.setImageResource(R.drawable.hamburger_btnxhdpi);
    }
}
