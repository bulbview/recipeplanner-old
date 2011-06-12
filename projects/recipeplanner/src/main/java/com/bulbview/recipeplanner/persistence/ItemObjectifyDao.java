package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.googlecode.objectify.Key;

@Component
public class ItemObjectifyDao implements EntityDao<Item> {

    @Autowired
    private EntityDao<Item> itemDao;

    @Override
    public Item get(final Key<Item> category) {
        return itemDao.get(category);
    }

    @Override
    public Collection<Item> get(final String filter,
                                final Object entity) {
        return itemDao.get(filter, entity);
    }

    @Override
    public Collection<Item> getAll() {
        return itemDao.getAll();
    }

    public Collection<Item> getAllFor(final ItemCategory category) {
        return itemDao.get("category", category);
    }

    @Override
    public Item getByName(final String name) {
        return itemDao.getByName(name);
    }

    @Override
    public Item save(final Item item) throws DaoException {
        return itemDao.save(item);
    }
}
