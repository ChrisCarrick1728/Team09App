package com.example.team09app.team09app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.Toast;

/** This class is used for adding a new item to a database
 * @version 1.0
 * @author Team 09
 */
public class AddNewItem extends AppCompatActivity implements MainMenuButtonFunction, AdapterView.OnItemSelectedListener {

    private EditText editNameText, editRoom, editCategory, editPriceText;
    private ImageView itemImage;
    private TextView editPurchaseDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private static final String TAG = "Add_New_Item";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final Context context = this;
    private EditText result;
    Uri mainURI;
    File photoFile;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        editNameText = findViewById(R.id.editNameText);
        editRoom = findViewById(R.id.editRoom);
        editCategory = findViewById(R.id.editCategory);
        editPriceText = findViewById(R.id.editPriceText);
        editPurchaseDate = findViewById(R.id.editPurchaseDate);
        itemImage = findViewById(R.id.item_image_id);

        // Calendar pop up to fill in date field
        editPurchaseDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogDate = new DatePickerDialog(
                        AddNewItem.this,
                        android.R.style.Theme_DeviceDefault,
                        mDateSetListener,
                        year, month, day);
                dialogDate.show();
            }
        });

        // Set chosen date as a string
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // January == 0 ... December == 11
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                editPurchaseDate.setText(date);
            }
        };

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
        
        //call for room dropdown
        getRoom();

        //call for category dropdown
        getCategory();

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

    // call getAllRooms() from ItemDao to get all items stored in database
    private void getRoom() {
        class GetRoom extends AsyncTask<Void, Void, List<Item>> {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> itemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAllRooms();
                Log.d("Test TAG", "doInBackground/AddNewItem all rooms: " + itemList.size());
                return itemList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);

                //Creating the spinner dropdown option
                Spinner spinner = findViewById(R.id.roomSpinner);

                //Need to get the size of how many items there are
                List<String> stringList = new ArrayList<String>(items.size());

                //sort through the items and place each item into the stringList
                for (int i = 0; i < items.size(); i++)
                {
                    stringList.add(items.get(i).getMRoom().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewItem.this, android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(AddNewItem.this);
            }
        }
        GetRoom gr = new GetRoom();
        gr.execute();
    }

    // call getAllCategories() from ItemDao to get all items stored in database
    private void getCategory() {
        class GetCategory extends AsyncTask<Void, Void, List<Item>> {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                List<Item> itemList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getItemRoomDatabase()
                        .itemDao()
                        .getAllCategories();
                Log.d("Test TAG", "doInBackground/AddNewItem all categories: " + itemList.size());

                return itemList;
            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);

                //Creating the spinner dropdown option
                Spinner spinner = findViewById(R.id.categorySpinner);

                //Need to get the size of how many items there are
                List<String> stringList = new ArrayList<String>(items.size());

                //sort through the items and place each item into the stringList
                for (int i = 0; i < items.size(); i++)
                {
                    stringList.add(items.get(i).getMCategory().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewItem.this, android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(AddNewItem.this);
            }
        }
        GetCategory gc = new GetCategory();
        gc.execute();
    }
    //End of dropdown spinner code

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

            photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "dispatchTakePictureIntent:" + ex);
            }
            Uri photoURI;
            if (photoFile != null) {
                if (mainURI == null) {
                    photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                    mainURI = photoURI;
                } else {
                    photoURI = mainURI;
                }
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


    private void saveTask() {
        final String sName = editNameText.getText().toString().trim();
        final String sRoom = editRoom.getText().toString().trim();
        final String sCategory = editCategory.getText().toString().trim();
        final String sPrice = editPriceText.getText().toString().trim();
        final String sDate = editPurchaseDate.getText().toString().trim();
        final String sImage = mainURI.toString().trim();
        final String sImagePath = photoFile.toString().trim();

        if(sName.isEmpty()) {
            editNameText.setError("Name required");
            editNameText.requestFocus();
            return;
        }
        if(sRoom.isEmpty()) {
            editRoom.setError("Room required");
            editRoom.requestFocus();
            return;
        }
        if(sCategory.isEmpty()) {
            editCategory.setError("Category required");
            editCategory.requestFocus();
            return;
        }
        if(sPrice.isEmpty()) {
            editPriceText.setError("Price required");
            editPriceText.requestFocus();
            return;
        }
        if(sDate.isEmpty()) {
            editPurchaseDate.setError("Date required");
            editPurchaseDate.requestFocus();
            return;
        }
        if(sImage.isEmpty()) {
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // creating an Item
                Item item = new Item();
                item.setMName(sName);
                item.setMRoom(sRoom);
                item.setMCategory(sCategory);
                item.setMPrice(sPrice);
                item.setMDate(sDate);
                item.setMPicture(sImage);
                item.setMPicturePath(sImagePath);

                // adding to database
                DatabaseClient.getInstance(getApplicationContext()).getItemRoomDatabase()
                        .itemDao()
                        .insert(item);
                return null;
            }

            // ToDo: I think this sends user to ViewAllItems page once item is saved. Verify
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), ViewAllItems.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

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
        input = (TextView)findViewById(R.id.editRoom);

        parent.getItemAtPosition(position).toString();

        Spinner spinner = (Spinner) findViewById(R.id.roomSpinner);
        spinner.setOnItemSelectedListener(this);

        String str = spinner.getSelectedItem().toString();
        input.setText(str);
        //end of code for room dropdown

        //code for category dropdown
        TextView input2;
        input2 = (TextView)findViewById(R.id.editCategory);

        parent.getItemAtPosition(position).toString();

        Spinner spinner2 = (Spinner) findViewById(R.id.categorySpinner);
        spinner2.setOnItemSelectedListener(this);

        String str2 = spinner2.getSelectedItem().toString();
        input2.setText(str2);
        //end of code for categories dropdown
    }

    //do not delete this empty function
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //This can stay empty for spinner dropdown
    }
}


