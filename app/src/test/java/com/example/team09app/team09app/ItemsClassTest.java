package com.example.team09app.team09app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemsClassTest {


    @Test
    public void testItemsNonDefaultConstructor() {
        String name = new String("computer");
        String room = new String ("office");
        String category = new String("electronics");
        String date = new String("Oct 31, 2018");
        Double price = new Double("250.99");
        Items ItemsUnderTest = new Items(name, room, category, date, price);

       assertEquals(name, ItemsUnderTest.getName());
       assertEquals(category, ItemsUnderTest.getCategory());
       assertEquals(room, ItemsUnderTest.getRoom());
       assertEquals(date, ItemsUnderTest.getDate());
       assertEquals(price, ItemsUnderTest.getPrice());
    }
}

