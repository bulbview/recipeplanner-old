package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;

@Component
public class ItemObjectifyDao {

    @Autowired
    private ObjectifyDao<Item> dao;

    public ItemObjectifyDao() {}

    public Item get(final String name) {
        return dao.get(name);
    }

    public Collection<Item> getAll() {
        return dao.getAll();
    }

    public Collection<Item> getAllFor(final ItemCategory category) {
        return dao.get("category", category);
    }

    public Item save(final Item item) {
        return dao.save(item);
    }
}
