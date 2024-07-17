package com.apptechbd.haatbazaar.adapters;

public class Sale {
    private String category, seller;
    private int price, quantity;

    public Sale(String category, int price, String seller, int quantity) {
        this.category = category;
        this.price = price;
        this.seller = seller;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
