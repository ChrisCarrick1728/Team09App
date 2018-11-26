package com.example.team09app.team09app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateItemActivity extends AppCompatActivity {

    private EditText editTextName, editTextRoom, editTextCategory, editTextPrice, editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        editTextName = findViewById(R.id.editNameText);
        editTextRoom = findViewById(R.id.editRoom);
        editTextCategory = findViewById(R.id.editCategory);
        editTextPrice = findViewById(R.id.editPriceText);
        editTextDate = findViewById(R.id.editPurchaseDate);

        final Item item = (Item) getIntent().getSerializableExtra("item");

        loadItem(item);


        // update item button
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateItem(item);
            }
        });

        // delete item button
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateItemActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteItem(item);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadItem(Item item) {
        editTextName.setText(item.getMName());
        editTextRoom.setText(item.getMRoom());
        editTextCategory.setText(item.getMCategory());
        editTextPrice.setText(item.getMPrice());
        editTextDate.setText(item.getMPrice());
    }

    private void updateItem(final Item item) {
        final String sName = editTextName.getText().toString().trim();
        final String sRoom = editTextRoom.getText().toString().trim();
        final String sCategory = editTextCategory.getText().toString().trim();
        final String sPrice = editTextPrice.getText().toString().trim();
        final String sDate = editTextDate.getText().toString().trim();

        // ToDo: All fields required right now. Change?

        if(sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }
        if(sRoom.isEmpty()) {
            editTextRoom.setError("Room required");
            editTextRoom.requestFocus();
            return;
        }
        if(sCategory.isEmpty()) {
            editTextCategory.setError("Category required");
            editTextCategory.requestFocus();
            return;
        }
        if(sPrice.isEmpty()) {
            editTextPrice.setError("Price required");
            editTextPrice.requestFocus();
            return;
        }
        if(sDate.isEmpty()) {
            editTextDate.setError("Date required");
            editTextDate.requestFocus();
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
                DatabaseClient.getInstance(getApplicationContext()).getItemRoomDatabase()
                        .itemDao()
                        .update(item);
                return null;
            }

            // ToDo: I think this sends user to ViewAllItems page once item is saved. Verify
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

            // ToDo: I think this sends user to ViewAllItems page once item is saved. Verify
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
}
