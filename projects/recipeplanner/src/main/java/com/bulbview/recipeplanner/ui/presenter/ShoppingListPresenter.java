package com.bulbview.recipeplanner.ui.presenter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.datamodel.ScheduledItem;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.ShoppingList;
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategory;
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategoryFactory;
import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.google.appengine.repackaged.com.google.common.collect.Maps;
import com.vaadin.ui.Window;

@Component
public class ShoppingListPresenter extends Presenter<ShoppingList> implements SessionPresenter {
    
    @Autowired
    private EntityDao<ItemCategory>           categoryDao;
    @Autowired
    private EntityDao<Item>                   itemDao;
    @Autowired
    private Window                            rootWindow;
    private Map<String, ShoppingListCategory> shoppingListCategories;
    private ShoppingListCategoryFactory       shoppingListCategoryFactory;
    @Autowired
    private Window                            shoppingListWindow;
    
    public ShoppingListPresenter() {
        this.shoppingListCategories = Maps.newHashMap();
    }
    
    public void addItem(final ScheduledItem scheduledItem) {
        if (scheduledItem instanceof Item) {
            final Item item = (Item) scheduledItem;
            final ItemCategory category = getCategoryFromPersistence(item);
            Preconditions.checkNotNull(category, "category not defined for: " + item);
            final ShoppingListCategory shoppingListCategory = resolveOrCreateShoppingListCategory(category.getName());
            addToView(shoppingListCategory);
            addItemToShoppingListCategory(shoppingListCategory, item);
        }
        else if (scheduledItem instanceof Recipe) {
            final Recipe recipe = (Recipe) scheduledItem;
            for (final Ingredient ingredient : recipe.getIngredients()) {
                addItem(itemDao.get(ingredient.getItemKey()));
            }
        }
    }
    
    public void displayShoppingList() {
        rootWindow.addWindow(shoppingListWindow);
    }
    
    @Override
    public void init() {
        getView().init();
        shoppingListWindow.setWidth("75%");
        shoppingListWindow.setHeight("75%");
    }
    
    public void setShoppingListCategories(final Map<String, ShoppingListCategory> shoppingListCategories) {
        this.shoppingListCategories = shoppingListCategories;
    }
    
    @Autowired
    public void setShoppingListCategoryFactory(final ShoppingListCategoryFactory shoppingListCategoryFactory) {
        this.shoppingListCategoryFactory = shoppingListCategoryFactory;
    }
    
    @Override
    @Autowired
    public void setView(final ShoppingList shoppingList) {
        super.setView(shoppingList);
    }
    
    private void addItemToShoppingListCategory(final ShoppingListCategory shoppingListCategory, final Item item) {
        shoppingListCategory.addListItem(item);
        
    }
    
    private void addToView(final ShoppingListCategory shoppingListCategory) {
        getView().addCategory(shoppingListCategory);
    }
    
    private ShoppingListCategory createShoppingListCategory(final String categoryName) {
        return shoppingListCategoryFactory.create(categoryName);
    }
    
    private ItemCategory getCategoryFromPersistence(final Item item) {
        return categoryDao.get(item.getCategory());
    }
    
    private ShoppingListCategory resolveOrCreateShoppingListCategory(final String categoryName) {
        ShoppingListCategory shoppingListCategory = shoppingListCategories.get(categoryName);
        if (shoppingListCategory == null) {
            shoppingListCategory = createShoppingListCategory(categoryName);
            shoppingListCategory.init();
            shoppingListCategories.put(categoryName, shoppingListCategory);
        }
        return shoppingListCategory;
    }
}
