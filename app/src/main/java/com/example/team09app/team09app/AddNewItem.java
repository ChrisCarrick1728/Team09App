package com.example.team09app.team09app;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNewItem extends AppCompatActivity implements MainMenuButtonFunction, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Add_New_Item";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final Context context = this;
    private EditText result;
    Uri mainURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);


        //For the dropdown selection for Room
        Spinner spinner = findViewById(R.id.roomSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.RoomList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //Start of the code for the add new category pop-up to open up

        ImageButton addNewCategoryPlus = (ImageButton)findViewById(R.id.addNewCategoryPlus_btn_id);

        //categoryList = (ListView)findViewById(R.id.categoryList);

        addNewCategoryPlus.setOnClickListener(new View.OnClickListener() {

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
        //End of add new category pop-up code

        //Start of code for the add new room pop-up

        ImageButton addNewRoomPlus = (ImageButton)findViewById(R.id.addNewRoomPlus_btn_id);

        //roomList = (ListView)findViewById(R.id.roomList);

        addNewRoomPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_new_room_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context,R.style.alertDialog);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editText2);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Save Room",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //Room newRoom = new Room();
                                        //newRoom.setName(String.valueOf(userInput.getText()));
                                        //roomList.add(newRoom);
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

        //End of code for add new room pop-up
    }

    String mCurrentPhotoPath;

    /** Creates a new timestamped filename for the image */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,   /* prefix */
                ".jpg",   /* suffix */
                storageDir       /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "dispatchTakePictureIntent:" + ex);
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                mainURI = photoURI;
                Log.d(TAG, "FileName: " + photoFile);
                Log.d(TAG, "FileURI:  " + photoURI);
                takePictureIntent.putExtra("PHOTOURI", photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImageView = (ImageView) findViewById(R.id.item_image_id);
        ImageButton mImageButton = (ImageButton) findViewById(R.id.camera_btn_id_add);
        Log.d(TAG, "I'm here");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.getContentResolver().notifyChange(mainURI, null);
            ContentResolver cr = this.getContentResolver();

            Log.d(TAG, "LoadURI: " + mainURI);
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(cr, mainURI);
                mImageButton.setVisibility(View.INVISIBLE);
                mImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                Log.d(TAG, "ERROR: " + e);
            }

        }
    }

    public void takePicture(View view) {
        dispatchTakePictureIntent();
        Log.d(TAG, "takePicture: " + getExternalFilesDir(null));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //So that the room can be prefilled once selected from drop down
        TextView input;
        input = (TextView)findViewById(R.id.nameText2);

        parent.getItemAtPosition(position).toString();

        Spinner spinner = (Spinner) findViewById(R.id.roomSpinner);
        spinner.setOnItemSelectedListener(this);

        String str = spinner.getSelectedItem().toString();
        input.setText(str);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //This can stay empty for spinner dropdown
    }

    //Todo: Add Dropdown menu for Room/Category
    //Todo: Add Popup to add new Room/Category
}
