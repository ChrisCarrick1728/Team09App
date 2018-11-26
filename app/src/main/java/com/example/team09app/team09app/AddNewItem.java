package com.example.team09app.team09app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewItem extends AppCompatActivity implements MainMenuButtonFunction, AdapterView.OnItemSelectedListener {

    private EditText editNameText, editRoom, editCategory, editPriceText, editPurchaseDate;


    private static final String TAG = "Add_New_Item";
    final Context context = this;
    //private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        editNameText = findViewById(R.id.editNameText);
        editRoom = findViewById(R.id.editRoom);
        editCategory = findViewById(R.id.editCategory);
        editPriceText = findViewById(R.id.editPriceText);
        editPurchaseDate = findViewById(R.id.editPurchaseDate);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });


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

    private void saveTask() {
        final String sName = editNameText.getText().toString().trim();
        final String sRoom = editRoom.getText().toString().trim();
        final String sCategory = editCategory.getText().toString().trim();
        final String sPrice = editPriceText.getText().toString().trim();
        final String sDate = editPurchaseDate.getText().toString().trim();

        // ToDo: All fields required right now. Change?

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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //This can stay empty for spinner dropdown
    }

    //Todo: Add Dropdown menu for Room/Category
    //Todo: Add Popup to add new Room/Category
}
