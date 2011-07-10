package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.service.ItemService;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

/**
 * Created per view category.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryListPresenter extends Presenter implements Observer {
    
    private CategorisedItemList     categorisedItemList;
    private ItemCategory            category;
    
    @Autowired
    private EntityDao<ItemCategory> categoryDao;
    @Autowired
    private ItemService             itemService;
    
    public void addItem(final String itemName) {
        Item savedItem;
        try {
            savedItem = itemService.save(createItem(itemName));
            categorisedItemList.addListItem(savedItem);
        }
        catch (final DaoException e) {
            categorisedItemList.showErrorMessage(e.getMessage());
        }
        
    }
    
    @Override
    public void init() {
        logger.debug("Registering for new item events with itemService...");
        itemService.addObserver(this);
    }
    
    public void setCategory(final String categoryName) {
        this.category = categoryDao.getByName(categoryName);
        addItemsToView(retrievePersistedItems());
    }
    
    public void setView(final CategorisedItemList categorisedItemList) {
        this.categorisedItemList = categorisedItemList;
    }
    
    @Override
    public void update(final Observable service, final Object savedItem) {
        logger.debug("received notification of new item: {}", savedItem);
        categorisedItemList.addListItem((Item) savedItem);
    }
    
    private void addItemsToView(final Collection<Item> categoryItems) {
        for (final Item item : categoryItems) {
            categorisedItemList.addListItem(item);
        }
    }
    
    private Item createItem(final String itemName) {
        logger.debug("Adding item: {}, category: {}", itemName, category.getName());
        final Item item = new Item();
        item.setName(itemName);
        item.setCategory(category);
        return item;
    }
    
    private Collection<Item> retrievePersistedItems() {
        final Collection<Item> categoryItems = itemService.getAllFor(category);
        logger.debug("{} items retrieved: {}", category, categoryItems.size());
        return categoryItems;
    }
}
