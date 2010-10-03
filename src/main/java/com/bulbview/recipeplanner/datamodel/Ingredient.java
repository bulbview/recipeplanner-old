package com.bulbview.recipeplanner.datamodel;

public class Ingredient {

    private String name;
    private String category;

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
