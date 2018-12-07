package com.example.team09app.team09app;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Export extends AppCompatActivity implements MainMenuButtonFunction {
    private static final String TAG = "Export";

    String excelFilePath = "items.cvs";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        // Save database to excel file
        findViewById(R.id.export_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "button clicked");
                    saveExcel();
                } catch (IOException e) {
                    Log.d(TAG, "save to Excel failed");
                    e.printStackTrace();
                }
            }
        });


        // ToDo: Export pdf

        // ToDo: Add backup button to this page

        // ToDo: Save to online backup

    }

    private void saveExcel() throws IOException {

        // create new cvs file
        Log.d(TAG, "attempting to create new file");
        File cvsFile = new File(context.getFilesDir(), excelFilePath);
        Log.d(TAG, "New file created");

        // open writer
        FileWriter fwExcel = new FileWriter(cvsFile, false);
        BufferedWriter bwExcel = new BufferedWriter(fwExcel);
        PrintWriter pwExcel = new PrintWriter(bwExcel);

        // start file with column headings
        pwExcel.println("Item Name, Room, Category, Purchase Date, Purchase Price");
        Log.d(TAG, "First line printed");

        class SaveExcel extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                // get number of items from database
                int numItems = 0;
                numItems = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getNumAllItems();
                Log.d(TAG, "Got number of items");

                // if no items in database, don't do anything
                if(numItems == 0) {
                    Log.d(TAG, "Zero items returned");
                    return null;
                }

                // get all items
                List<Item> itemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAll();
                Log.d(TAG, "Got all items");

                // if no items in database, don't do anything
                if(itemList == null){
                    Log.d(TAG, "No items in List");
                    return null;
                }

                // save each item
                for (int i = 0; i < numItems; i++) {
                    pwExcel.println(itemList.get(i).getMName() + ", "
                            + itemList.get(i).getMRoom() + ", "
                            + itemList.get(i).getMCategory() + ", "
                            + itemList.get(i).getMDate() + ", "
                            + itemList.get(i).getMPrice());
                }

                // close file writer
                Log.d(TAG, "Saved all items to file");
                pwExcel.flush();
                pwExcel.close();

                return null;
            }
        }
        SaveExcel se = new SaveExcel();
        se.execute();

        // ToDo: What to do with csv file? email, save online such as Google Drive? Do we give option here?
        // send csv file by email

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
