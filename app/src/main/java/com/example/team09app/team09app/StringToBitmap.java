package com.example.team09app.team09app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class StringToBitmap {
    public Bitmap convert (String str) {
        Log.d("IMAGE", "String: " + str);
        byte [] encodeByte = Base64.decode(str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
