package com.example.team09app.team09app;

import java.util.List;

public class RoomObject {
    private List<Item> item;
    private List<Integer>  numItems;

    public List<Item> getItem() {
        return item;
    }

    public List<Integer> getNumItems() {
        return numItems;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public void setNumItems(List<Integer> numItems) {
        this.numItems = numItems;
    }
}
