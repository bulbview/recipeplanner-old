package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemView;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryListPresenter extends Presenter implements SessionPresenter {
    
    private CategorisedItemView     categorisedItemList;
    private ItemCategory            category;
    
    @Autowired
    private EntityDao<ItemCategory> categoryDao;
    @Autowired
    private ItemObjectifyDao        itemDao;
    
    public void addItem(final String itemName) {
        Item savedItem;
        try {
            savedItem = itemDao.save(createItem(itemName));
            categorisedItemList.addListItem(savedItem);
        }
        catch (final DaoException e) {
            categorisedItemList.showErrorMessage(e.getMessage());
        }
        
    }
    
    @Override
    public void init() {
        
    }
    
    public void setCategory(final String categoryName) {
        this.category = categoryDao.getByName(categoryName);
        addItemsToView(retrievePersistedItems());
    }
    
    public void setView(final CategorisedItemView categorisedItemList) {
        this.categorisedItemList = categorisedItemList;
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
        final Collection<Item> categoryItems = itemDao.getAllFor(category);
        logger.debug("{} items retrieved: {}", category, categoryItems.size());
        return categoryItems;
    }
    
}
