package com.bulbview.recipeplanner.datamodel;

import com.googlecode.objectify.Key;

public class Item extends Entity implements ScheduledItem {

    private Key<ItemCategory> category;

    public Key<ItemCategory> getCategory() {
        return category;
    }

    public void setCategory(final ItemCategory category) {
        this.category = new Key<ItemCategory>(ItemCategory.class, category.getId());
    }

}
