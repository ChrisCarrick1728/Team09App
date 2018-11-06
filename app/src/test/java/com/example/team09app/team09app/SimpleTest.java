package com.example.team09app.team09app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleTest {
    @Test
    public void additionIsCorrect() { assertEquals(4, 2 + 2); }

    @Test
    public void verifyPriceIsGreaterThanZero() {
        Items ItemUnderTest = new Items(4.56);
        assertTrue(ItemUnderTest.getPrice() > 0);
    }

    @Test
    public void testItemsDefaultConstructor() {
        Items ItemsUnderTest = new Items();
        Double price = ItemsUnderTest.getPrice();
        Double test = new Double(0.1234567);
        assertEquals(test, price);
    }

    @Test
    public void testItemsNonDefaultConstructor() {
        String Date = new String("Oct 31, 2018");
        Items ItemsUnderTest = new Items(Date);
        Double price = ItemsUnderTest.getPrice();
        Double test = new Double(0.1234567);
        assertEquals(test, price);

        String testDate = new String("Oct 31, 2018");
        assertEquals(Date, testDate);
    }
}

