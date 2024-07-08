package com.apptechbd.haatbazaar.models;

import java.util.List;

public class AllCategories {
    private String admin;
    private List<String> categories;

    public AllCategories(String admin, List<String> categories) {
        this.admin = admin;
        this.categories = categories;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdminId(String admin) {
        this.admin = admin;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
