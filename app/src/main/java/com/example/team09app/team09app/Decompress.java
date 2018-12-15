package com.example.team09app.team09app;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/** This class lets the app decompress data that has been stored in a compressed file.
 * @author team 09.
 * @version 1.0
 */
public class Decompress {
    public static String decompress(byte[] b) throws IOException {

        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(b));
        InputStreamReader reader = new InputStreamReader(gzip, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        StringBuilder s = new StringBuilder();

        String line;
        while ((line = in.readLine()) != null) {
            s.append(line);
        }
        in.close();
        reader.close();
        gzip.close();
        System.out.println(s.toString());
        return s.toString();
    }
}