package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;

@Component
public class ItemObjectifyDao implements EntityDao<Item> {

    @Autowired
    private ObjectifyDao<Item> dao;

    @Override
    public Collection<Item> getAll() {
        return dao.getAll();
    }

    public Collection<Item> getAllFor(final ItemCategory category) {
        return dao.get("category", category);
    }

    @Override
    public Item getByName(final String name) {
        return dao.getByName(name);
    }

    @Override
    public Item save(final Item item) throws DaoException {
        return dao.save(item);
    }
}
