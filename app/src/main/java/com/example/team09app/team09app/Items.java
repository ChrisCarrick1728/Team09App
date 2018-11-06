package com.example.team09app.team09app;

//import org.javamoney.moneta.Money;

public class Items {
    Double price;
    String date;

    Items() {
        price = 0.1234567;
    }

    Items(Double price) {
        this.price = price;
        date = "";
    }

    Items(String date) {
        price = 0.1234567;
        this.date = date;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
