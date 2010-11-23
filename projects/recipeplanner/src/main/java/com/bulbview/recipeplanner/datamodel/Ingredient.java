package com.bulbview.recipeplanner.datamodel;

import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.common.base.Objects;

public class Ingredient {

    private String   name;

    private Category category;

    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = false;
        if( obj instanceof Ingredient ) {
            final Ingredient obj2 = (Ingredient) obj;
            isEqual = Objects.equal(category, obj2.getCategory()) && Objects.equal(name, obj2.getName());
        }
        return isEqual;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(category, name);
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
