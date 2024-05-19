package com.apptechbd.haatbazaar.models;

public class Category {
    private String categoryName;
    private boolean isSelected;

    public Category(String categoryName, boolean isSelected) {
        this.categoryName = categoryName;
        this.isSelected = isSelected;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
