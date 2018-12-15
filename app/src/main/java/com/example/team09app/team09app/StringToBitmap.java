package com.example.team09app.team09app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

/** This class handles the code to let the app convert a string into a bitmap.
 * @author team 09
 * @version 1.0
 */
public class StringToBitmap {
    public Bitmap convert (String str) {
        Log.d("IMAGE", "String: " + str);
        byte [] encodeByte = Base64.decode(str, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
