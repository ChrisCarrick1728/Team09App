package com.example.team09app.team09app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/** This class handles the exporting of data from the database into a pdf or excel file.
 * @author Team 09
 * @version 1.0
 */
public class Export extends AppCompatActivity implements MainMenuButtonFunction {
    private static final String TAG = "Export";

    String excelFilePath = "items.csv";
    Context context = this;
    ImageButton shareButton;
    //ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        // Save database to excel file
        findViewById(R.id.export_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File csvFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), excelFilePath);
                Log.d(TAG, "New File created " + csvFile);

                // save database to csv file
                try {
                    saveExcel(csvFile);

                } catch (IOException e) {
                    Log.d(TAG, "save to Excel failed");
                    e.printStackTrace();
                }

                // pop up created to export
                createPopup(csvFile);
            }
        });
    }

    private void createPopup(File file) {

        // inflate the layout of the the popup window
        LayoutInflater liExport = LayoutInflater.from(context);
        View exportView = liExport.inflate(R.layout.activity_send_file, null);

        AlertDialog.Builder alertDB = new AlertDialog.Builder(
                context, R.style.AlertDialogTheme);
        alertDB.setView(exportView);

        shareButton = (ImageButton) exportView.findViewById(R.id.action_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri outputUri = FileProvider.getUriForFile(
                        context,"com.example.android.fileprovider", file);
                if (outputUri != null) {
                    Intent shareIntent = ShareCompat.IntentBuilder.from((Activity) context)
                            .setType("application/csv")
                            .setStream(outputUri)
                            .setEmailTo(new String[]{""})
                            .getIntent();
                    shareIntent.setDataAndType(outputUri, "application/csv");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(shareIntent, "Share File"));
                }
            }
        });

        alertDB
                .setCancelable(true)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDB.create();

        alert.show();
        //alert.getWindow().setLayout(600, 400);
    }

    private void saveExcel(File csvFile) throws IOException {
        Log.d(TAG, "Opening FileWriter");

        // open writer
        FileWriter fwExcel = new FileWriter(csvFile, false);
        Log.d(TAG, "New File Writer created");
        BufferedWriter bwExcel = new BufferedWriter(fwExcel);
        Log.d(TAG, "New Buffered Writer created");
        PrintWriter pwExcel = new PrintWriter(bwExcel);
        Log.d(TAG, "New Print Writer created");

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
                Log.d(TAG, "Got number of items: " + numItems);

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
                int numItemsSaved = 0;
                for (int i = 0; i < itemList.size(); i++) {
                    pwExcel.println(itemList.get(i).getMName() + ", "
                            + itemList.get(i).getMRoom() + ", "
                            + itemList.get(i).getMCategory() + ", "
                            + itemList.get(i).getMDate() + ", "
                            + itemList.get(i).getMPrice());
                    numItemsSaved++;
                }

                // close file writer
                Log.d(TAG, "Saved all items to file: " + numItemsSaved);
                pwExcel.flush();
                pwExcel.close();

                return null;
            }
        }
        SaveExcel se = new SaveExcel();
        se.execute();

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
