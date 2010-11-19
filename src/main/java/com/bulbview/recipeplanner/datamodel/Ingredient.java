package com.bulbview.recipeplanner.datamodel;

import com.bulbview.recipeplanner.ui.presenter.Category;

public class Ingredient {

    private String   name;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
