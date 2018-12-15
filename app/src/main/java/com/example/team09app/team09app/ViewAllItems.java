package com.example.team09app.team09app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/** This class lets the user view all her items
 * @author team 09
 * @version 1.0
 */
public class ViewAllItems extends AppCompatActivity implements MainMenuButtonFunction {

    private ImageButton addItemButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_items);

        recyclerView = findViewById(R.id.Item_Viewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addItemButton = findViewById(R.id.add_new_item_btn_id);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllItems.this, AddNewItem.class);
                startActivity(intent);
            }
        });

        getTasks();
    }

    // call getAll() from ItemDao to get all items stored in database
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Item>> {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> itemList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getItemRoomDatabase()
                    .itemDao()
                    .getAll();
                Log.d("Test TAG", "doInBackground itemList size: " + itemList.size());
                return itemList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                Log.d("Test Tag", "onPostExecute started");
                Log.d("Test TAG", "onPostExecute itemList size: " + items.size());
                super.onPostExecute(items);
                ItemsListAdapter adapter = new ItemsListAdapter(ViewAllItems.this, items);
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
