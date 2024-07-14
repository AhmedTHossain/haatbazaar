package com.apptechbd.haatbazaar.adapters;

public class Sale {
    private String category;
    private int price;
    private String seller;

    public Sale(String category, int price, String seller) {
        this.category = category;
        this.price = price;
        this.seller = seller;
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
}
