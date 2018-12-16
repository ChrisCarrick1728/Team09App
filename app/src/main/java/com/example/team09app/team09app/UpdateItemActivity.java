package com.example.team09app.team09app;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.example.team09app.team09app.AddNewItem.REQUEST_IMAGE_CAPTURE;

/** This class lets the user update the info for an item.
 * @author team 09
 * @version 1.0
 */
public class UpdateItemActivity extends AppCompatActivity implements MainMenuButtonFunction, AdapterView.OnItemSelectedListener {

    private EditText editTextName, editTextRoom, editTextCategory, editTextPrice;
    private TextView editTextDate;
    private ImageButton itemImage;
    private DatePickerDialog.OnDateSetListener mEditDateSetListener;
    Uri mainURI;
    File photoFile;
    Bitmap imageBitmap;
    private static final String TAG = "UpdateItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        editTextName = findViewById(R.id.editNameText);
        editTextRoom = findViewById(R.id.editRoom);
        editTextCategory = findViewById(R.id.editCategory);
        editTextPrice = findViewById(R.id.editPriceText);
        editTextDate = findViewById(R.id.editPurchaseDate);
        itemImage = findViewById(R.id.item_image_id_update);

        final Item item = (Item) getIntent().getSerializableExtra("item");
        Log.d(TAG, "Item: " + getIntent().getSerializableExtra("item"));
        loadItem(item);

        // Calendar pop up to fill in date field
        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogDate = new DatePickerDialog(
                    UpdateItemActivity.this,
                    android.R.style.Theme_DeviceDefault,
                    mEditDateSetListener,
                    year, month, day);
                dialogDate.show();
            }
        });

        // Set chosen date as a string
        mEditDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // January == 1 ... December == 11
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                editTextDate.setText(date);
            }
        };

        // update item button
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem(item);
            }
        });

        //call for room dropdown
        getRoom();

        //call for category dropdown
        getCategory();
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

                // Sort through the items and place each item into the stringList
                for (int i = 0; i < items.size(); i++)
                {
                    stringList.add(items.get(i).getMRoom().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateItemActivity.this,
                    android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(UpdateItemActivity.this);
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
                Log.d("Test TAG", "doInBackground/AddNewItem all categories: "
                    + itemList.size());

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateItemActivity.this,
                    android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(UpdateItemActivity.this);
            }
        }
        GetCategory gc = new GetCategory();
        gc.execute();
    }
    //End of dropdown spinner code















    private void loadItem(Item item) {
        editTextName.setText(item.getMName());
        editTextRoom.setText(item.getMRoom());
        editTextCategory.setText(item.getMCategory());
        editTextPrice.setText(item.getMPrice());
        editTextDate.setText(item.getMDate());
        photoFile = new File(item.getMPicturePath());

        // Load Image
        ContentResolver cr = this.getContentResolver();
        try {
            Uri imageURI = Uri.parse(item.getMPicture());

            Log.d(TAG, "LoadURI: " + item.getMPicture());
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(cr, imageURI);
                //mImageButton.setVisibility(View.INVISIBLE);
                itemImage.setImageBitmap(imageBitmap);
                mainURI = Uri.parse(item.getMPicture());
            } catch (IOException e) {
                Log.d(TAG, "ERROR: " + e);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: Image doesn't exist " + e);
        }

    }

    private void updateItem(final Item item) {
        final String sName = editTextName.getText().toString().trim();
        final String sRoom = editTextRoom.getText().toString().trim();
        final String sCategory = editTextCategory.getText().toString().trim();
        final String sPrice =  editTextPrice.getText().toString().trim();
        final String sDate = editTextDate.getText().toString().trim();
        final String sImage = mainURI.toString().trim();


        try {

            if (sName.isEmpty()) {
                editTextName.setError("Name required");
                editTextName.requestFocus();
                return;
            }
            if (sRoom.isEmpty()) {
                editTextRoom.setError("Room required");
                editTextRoom.requestFocus();
                return;
            }
            if (sCategory.isEmpty()) {
                editTextCategory.setError("Category required");
                editTextCategory.requestFocus();
                return;
            }
            if (sPrice.isEmpty()) {
                editTextPrice.setError("Price required");
                editTextPrice.requestFocus();
                return;
            }
            if (sDate.isEmpty()) {
                editTextDate.setError("Date required");
                editTextDate.requestFocus();
                return;
            }
            if (sImage == null) {
                return;
            }

            class UpdateItem extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    item.setMName(sName);
                    item.setMRoom(sRoom);
                    item.setMCategory(sCategory);
                    item.setMPrice(sPrice);
                    item.setMDate(sDate);
                    item.setMPicture(sImage);
                    DatabaseClient.getInstance(getApplicationContext()).getItemRoomDatabase()
                        .itemDao()
                        .update(item);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(UpdateItemActivity.this, ViewAllItems.class));
                }
            }

            UpdateItem uItem = new UpdateItem();
            uItem.execute();
        } catch (Exception e) {
            Log.d(TAG, "Error: " + e);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

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
        ImageView mImageView = (ImageView) findViewById(R.id.item_image_id_update);
        Log.d(TAG, "I'm here");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            this.getContentResolver().notifyChange(mainURI, null);
            ContentResolver cr = this.getContentResolver();

            Log.d(TAG, "LoadURI: " + mainURI);
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(cr, mainURI);
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
