package com.example.team09app.team09app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/** This class lets the user browse her stored items sorted by category.
 * @author team 09
 * @version 1.0
 */
public class BrowseByCategory extends AppCompatActivity implements SaveCurrentActivity, MainMenuButtonFunction {

    private ImageButton addCategoryButton;
    private RecyclerView recyclerView;

    private static final String CURRENT_ACTIVITY = "BrowseByCategory";
    private static final String TAG = "BrowseByCategogy";
    final Context context = this;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_category);
        saveCurrent(CURRENT_ACTIVITY);

        recyclerView = findViewById(R.id.view_all_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addCategoryButton = findViewById(R.id.addNewCategory_btn_id);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_new_category_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context,R.style.alertDialog);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editText2);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Save Category",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Category newCategory = new Category();
                                        newCategory.setName(String.valueOf(userInput.getText()));
                                        //categoryList.add(newCategory);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        getTasks();
    }

    // call functions from ItemDao()
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Item>> {

            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> categoryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAllCategories();

                Log.d(TAG, "doInBackground: completed");
                return categoryList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                CategoryListAdapter adapter = new CategoryListAdapter(BrowseByCategory.this, items);
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

    // ToDo: I don't think we used this so can we delete?
    @Override
    public void saveCurrent(String currentActivity) {

    }
}
