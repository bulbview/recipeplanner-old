package com.bulbview.recipeplanner.service;

import java.util.Collection;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao;

@Service
public class ItemService extends Observable {
    
    @Autowired
    private ItemObjectifyDao itemDao;
    
    public Collection<Item> getAllFor(final ItemCategory category) {
        return itemDao.getAllFor(category);
    }
    
    public Item save(final Item item) {
        final Item savedItem = itemDao.save(item);
        setChanged();
        notifyObservers(savedItem);
        return savedItem;
    }
    
}
