package com.example.team09app.team09app;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import android.util.Base64;
import android.util.Log;

public class BitmapToString {

    public String convert(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        //String temp = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("IMAGE", "String: " + temp);
        return temp;
    }
}
