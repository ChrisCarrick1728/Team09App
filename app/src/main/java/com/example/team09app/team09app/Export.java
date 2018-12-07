package com.example.team09app.team09app;

import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chilkatsoft.CkCsv;

import java.io.File;
import java.util.List;

public class Export extends AppCompatActivity implements MainMenuButtonFunction {

    private static final String TAG = "Export";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        // ToDo: Export excel
        findViewById(R.id.export_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExcel();
            }
        });


        // ToDo: Export pdf

        // ToDo: Add backup button to this page

        // ToDo: Save to online backup

    }

    private void saveExcel() {

        // create a new csv file
        // https://www.example-code.com/android/csv_create.asp
        CkCsv csv = new CkCsv();

        class SaveExcel extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                // set up excel columns
                csv.SetColumnName(0, "Name");
                csv.SetColumnName(1, "Room");
                csv.SetColumnName(2, "Category");
                csv.SetColumnName(3, "Purchase Date");
                csv.SetColumnName(4, "Purchase Price");

                // get number of items from database
                int numItems = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getNumItems();

                // get all items
                List<Item> itemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAll();

                // save each item
                for (int i = 0; i < numItems; i++) {
                    csv.SetCell(i, 0, itemList.get(i).getMName());
                    csv.SetCell(i, 1, itemList.get(i).getMRoom());
                    csv.SetCell(i, 2, itemList.get(i).getMCategory());
                    csv.SetCell(i, 3, itemList.get(i).getMDate());
                    csv.SetCell(i, 4, itemList.get(i).getMPrice());
                }
                return null;
            }
        }
        SaveExcel se = new SaveExcel();
        se.execute();

        // ToDo: What to do with csv file? email, save to phone, save online such as Google Drive? Do we give option here?
        // send csv file by email

        // save csv file to phone memory

        // save online

    }

    @Override
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

    @Override
    public void closeMenu() {
        ConstraintLayout mainMenuOverlay = (ConstraintLayout) findViewById(R.id.menu_overlay_id);
        ImageView hamburgerButton = (ImageView) findViewById(R.id.hamburger_menu_id);

        mainMenuOverlay.setVisibility(View.GONE);
        hamburgerButton.setImageResource(R.drawable.hamburger_btnxhdpi);
    }
}
