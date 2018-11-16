package com.example.team09app.team09app;

//import org.javamoney.moneta.Money;

public class Items {
    String name;
    String room;
    String category;
    String date;
    Double price;

    //Todo: Do we need a variable for picture info with getters and setters?

    Items() {}

    // Todo: what if a user doesn't fill in all fields? Or do we need to require that?
    Items(String name, String room, String category, String date, Double price)
    {
        this.name = name;
        this.room = room;
        this.category = category;
        this.date = date;
        this.price = price;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setRoom(String room) { this.room = room; }

    public String getRoom() { return room; }

    public void setCategory(String category) { this.category = category; }

    public String getCategory() { return category; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }


}
