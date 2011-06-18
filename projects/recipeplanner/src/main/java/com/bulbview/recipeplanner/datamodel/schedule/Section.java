package com.bulbview.recipeplanner.datamodel.schedule;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Item;
import com.google.appengine.repackaged.com.google.common.collect.Sets;
import com.googlecode.objectify.Key;

public abstract class Section {

    private final Collection<Key<Item>> items;

    public Section() {
        this.items = Sets.newHashSet();
    }

    public void addItem(final Item item) {
        items.add(new Key<Item>(Item.class, item.getName()));
    }

    public Collection<Key<Item>> getItems() {
        return items;
    }

}
