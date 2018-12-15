package com.example.team09app.team09app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewSingleActivity extends AppCompatActivity implements MainMenuButtonFunction {

    private RecyclerView recylcerView;
    private ImageButton editButton;
    private ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single);

        final Item item = (Item) getIntent().getSerializableExtra("item");

        recylcerView = findViewById(R.id.singleItemViewer);
        recylcerView.setLayoutManager(new LinearLayoutManager(this));

        editButton = findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSingleActivity.this, UpdateItemActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });

        // delete item button
        deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSingleActivity.this,
                    R.style.AlertDialogTheme);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteItem(item);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        getTasks(item);
    }

    private void getTasks(Item item) {
        class GetTasks extends AsyncTask<Void, Void, List<Item>> {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> itemList = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getItemRoomDatabase()
                    .itemDao()
                    .getOne(item.getMId());
                return itemList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                SingleItemAdapter adapter = new SingleItemAdapter(ViewSingleActivity.this, items);
                recylcerView.setAdapter(adapter);
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void deleteItem(final Item item) {
        class DeleteItem extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getItemRoomDatabase()
                    .itemDao()
                    .delete(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ViewSingleActivity.this, ViewAllItems.class));
            }
        }

        DeleteItem deleteItem = new DeleteItem();
        deleteItem.execute();
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
