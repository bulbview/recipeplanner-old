package com.bulbview.recipeplanner.datamodel;

import com.googlecode.objectify.Key;

public class Item extends Entity {

    private Key<ItemCategory> category;

    public void setCategory(final ItemCategory category) {
        this.category = new Key<ItemCategory>(ItemCategory.class, category.getName());
    }

}
