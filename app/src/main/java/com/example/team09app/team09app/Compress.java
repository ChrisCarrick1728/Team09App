package com.example.team09app.team09app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

/** This class handles the compressing of data so it can be stored effectively.
 * @author team09.
 * @version 1.0
 */
public class Compress {

    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return new byte[] {};
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(str.length());
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes(StandardCharsets.UTF_8));
        gzip.close();
        byte[] compressedData = out.toByteArray();

        return compressedData;
    }
}
