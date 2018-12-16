package com.example.team09app.team09app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/** This class lets the user browse her items sorted by what room they are located in.
 * @author team 09.
 * @version 1.0
 */
public class BrowseByRoom extends AppCompatActivity implements MainMenuButtonFunction {

    private RecyclerView recyclerView;

    private static final String TAG = "BrowseByRoom";
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_room);

        recyclerView = findViewById(R.id.view_all_rooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTasks();
    }

    // call functions from ItemDao()
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, RoomObject> {

            @Override
            protected RoomObject doInBackground(Void... voids) {
                RoomObject r = new RoomObject();
                List<Item> roomList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getItemRoomDatabase()
                    .itemDao()
                    .getAllRooms();
                r.setItem(roomList);
                List<Integer> numRooms = new ArrayList<>();
                for (int i = 0; i < roomList.size(); i++) {
                    Integer num = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getItemRoomDatabase()
                            .itemDao()
                            .getNumRoom(roomList.get(i).getMRoom());
                    numRooms.add(num);

                    Log.d("Test_Num", roomList.get(i).getMRoom() + " " + numRooms.get(i).toString());
                }

                r.setNumItems(numRooms);
                Log.d(TAG, "doInBackground: completed");
                return r;
            }

            @Override
            protected void onPostExecute(RoomObject r) {
                super.onPostExecute(r);
                RoomListAdapter adapter = new RoomListAdapter(BrowseByRoom.this, r);
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "onPostExecute: completed");
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