package com.bulbview.recipeplanner.ui.presenter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.ObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.ShoppingList;
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategory;
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategoryFactory;
import com.google.appengine.repackaged.com.google.common.collect.Maps;
import com.vaadin.ui.Window;

@Component
public class ShoppingListPresenter extends Presenter {

    @Autowired
    private ObjectifyDao<ItemCategory>              categoryDao;
    @Autowired
    private Window                                  rootWindow;
    private ShoppingList                            shoppingList;
    private Map<ItemCategory, ShoppingListCategory> shoppingListCategories;

    private ShoppingListCategoryFactory             shoppingListCategoryFactory;

    @Autowired
    private Window                                  shoppingListWindow;

    public ShoppingListPresenter() {
        this.shoppingListCategories = Maps.newHashMap();
    }

    public void addItem(final Item item) {
        final ItemCategory category = getCategoryFromPersistence(item);
        final ShoppingListCategory shoppingListCategory = resolveOrCreateShoppingListCategory(category.getName());
        addToShoppingList(shoppingListCategory);
        addItemToShoppingListCategory(shoppingListCategory, item);
    }

    public void displayShoppingList() {
        rootWindow.addWindow(shoppingListWindow);
    }

    @Override
    public void init() {
        shoppingList.init();
    }

    @Autowired
    public void setShoppingList(final ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void setShoppingListCategories(final Map<ItemCategory, ShoppingListCategory> shoppingListCategories) {
        this.shoppingListCategories = shoppingListCategories;
    }

    @Autowired
    public void setShoppingListCategoryFactory(final ShoppingListCategoryFactory shoppingListCategoryFactory) {
        this.shoppingListCategoryFactory = shoppingListCategoryFactory;
    }

    private void addItemToShoppingListCategory(final ShoppingListCategory shoppingListCategory,
                                               final Item item) {
        shoppingListCategory.addListItem(item);

    }

    private void addToShoppingList(final ShoppingListCategory shoppingListCategory) {
        shoppingList.addCategory(shoppingListCategory);
    }

    private ShoppingListCategory createShoppingListCategory(final String categoryName) {
        return shoppingListCategoryFactory.create(categoryName);
    }

    private ItemCategory getCategoryFromPersistence(final Item savedItem) {
        return categoryDao.get(savedItem.getCategory());
    }

    private ShoppingListCategory resolveOrCreateShoppingListCategory(final String categoryName) {
        ShoppingListCategory shoppingListCategory = shoppingListCategories.get(categoryName);
        if( shoppingListCategory == null ) {
            shoppingListCategory = createShoppingListCategory(categoryName);
        }
        return shoppingListCategory;
    }
}
