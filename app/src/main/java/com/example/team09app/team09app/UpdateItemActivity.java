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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;
import static com.example.team09app.team09app.AddNewItem.REQUEST_IMAGE_CAPTURE;

/** This class lets the user update the info for an item.
 * @author team 09
 * @version 1.0
 */
public class UpdateItemActivity extends AppCompatActivity implements MainMenuButtonFunction {

    private EditText editTextName, editTextRoom, editTextCategory, editTextPrice;
    private TextView editTextDate;
    private ImageButton itemImage;
    private DatePickerDialog.OnDateSetListener mEditDateSetListener;
    Uri mainURI;
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
    }

    private void loadItem(Item item) {
        editTextName.setText(item.getMName());
        editTextRoom.setText(item.getMRoom());
        editTextCategory.setText(item.getMCategory());
        editTextPrice.setText(item.getMPrice());
        editTextDate.setText(item.getMDate());

        StringToBitmap s = new StringToBitmap();
        Decompress d = new Decompress();
        imageBitmap = s.convert(item.getMPicture());
        itemImage.setImageBitmap(imageBitmap);
    }

    private void updateItem(final Item item) {
        final String sName = editTextName.getText().toString().trim();
        final String sRoom = editTextRoom.getText().toString().trim();
        final String sCategory = editTextCategory.getText().toString().trim();

        // ToDo: Adds an extra dollar sign each time page loads
        final String dollarSign = "$ ";

        final String sPrice = dollarSign + editTextPrice.getText().toString().trim();
        final String sDate = editTextDate.getText().toString().trim();


        try {
            Compress c = new Compress();
            BitmapToString b = new BitmapToString();

            final String sImage = (b.convert(imageBitmap));

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
            if (sImage.isEmpty()) {
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
                startActivity(new Intent(UpdateItemActivity.this, ViewAllItems.class));
            }
        }

        DeleteItem deleteItem = new DeleteItem();
        deleteItem.execute();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = null;
            try {
                photoFile = File.createTempFile("tempPhoto", ".jpg", storageDir);
            } catch (IOException ex) {
                Log.d(TAG, "Error: " + ex);
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
}
