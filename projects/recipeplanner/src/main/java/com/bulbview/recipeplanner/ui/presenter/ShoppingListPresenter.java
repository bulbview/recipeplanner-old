package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.ObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.ShoppingList;
import com.vaadin.ui.Window;

@Component
public class ShoppingListPresenter extends Presenter {

    @Autowired
    private ObjectifyDao<ItemCategory> categoryDao;
    @Autowired
    private Window                     rootWindow;

    private ShoppingList               shoppingList;

    @Autowired
    private Window                     shoppingListWindow;

    public void displayShoppingList() {
        rootWindow.addWindow(shoppingListWindow);
    }

    @Override
    public void init() {
        shoppingList.init();
        final Collection<ItemCategory> itemCategories = categoryDao.getAll();
        logger.debug("categories retrieved: {}", itemCategories.size());

        for ( final ItemCategory itemCategory : itemCategories ) {
            final ShoppingListPresenter shoppingListPresenter = new ShoppingListPresenter();
            shoppingList.addCategory(itemCategory.getName());
        }
    }

    @Autowired
    public void setShoppingList(final ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
