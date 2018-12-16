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

/** This class lets the user browse her stored items sorted by category.
 * @author team 09
 * @version 1.0
 */
public class BrowseByCategory extends AppCompatActivity implements MainMenuButtonFunction {

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

        recyclerView = findViewById(R.id.view_all_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTasks();
    }

    // call functions from ItemDao()
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, CategoryObject> {

            @Override
            protected CategoryObject doInBackground(Void... voids) {
                CategoryObject c = new CategoryObject();
                List<Item> categoryList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAllCategories();
                c.setItem(categoryList);

                List<Integer> numCategory = new ArrayList<>();
                for (int i = 0; i < categoryList.size(); i++) {
                    Integer num = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getItemRoomDatabase()
                            .itemDao()
                            .getNumCategory(categoryList.get(i).getMCategory());
                    numCategory.add(num);

                    Log.d("Test_Num", categoryList.get(i).getMCategory() + " " + numCategory.get(i).toString());
                }
                c.setNumItems(numCategory);
                Log.d(TAG, "doInBackground: completed");
                return c;
            }

            @Override
            protected void onPostExecute(CategoryObject c) {
                super.onPostExecute(c);
                CategoryListAdapter adapter = new CategoryListAdapter(BrowseByCategory.this, c);
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
