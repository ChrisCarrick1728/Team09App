package com.example.team09app.team09app;

import android.text.BoringLayout;
import android.util.Log;

import java.io.File;

public class DeleteImage {

    DeleteImage(String path) {
        delete(path);
    }

    public Boolean delete(String path) {
        File fDelete = new File(path);

        if (fDelete.exists()) {
            Log.d("Delete_Item", "DeleteItem: File Exists");
            fDelete.delete();
            if (fDelete.exists()) {
                Log.d("Delete_Item", "DeleteItem: File not deleted");
                return false;
            } else {

                Log.d("Delete_Item", "DeleteItem: File Deleted");
                return true;
            }
        } else {
            Log.d("Delete_Item", "DeleteItem: Not Found");
            return false;
        }
    }
}
