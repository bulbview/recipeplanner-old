package com.bulbview.recipeplanner.ui.presenter;

import java.util.Observable;
import java.util.Observer;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

public class ItemHandlerCategoryListPresenter extends PresenterDecorator<ICategoryListPresenter, CategorisedItemList>
        implements ICategoryListPresenter, Observer {
    
    public ItemHandlerCategoryListPresenter(final ICategoryListPresenter presenter) {
        super(presenter);
    }
    
    @Override
    public void addItem(final Item savedItem) {
        decoratedPresenter.addItem(savedItem);
        
    }
    
    @Override
    public void addItemByName(final String name) {
        decoratedPresenter.addItemByName(name);
    }
    
    @Override
    public ItemCategory getCategory() {
        return decoratedPresenter.getCategory();
    }
    
    @Override
    public void setCategory(final String categoryName) {
        decoratedPresenter.setCategory(categoryName);
    }
    
    @Override
    public void update(final Observable service, final Object savedItem) {
        logger.debug("{[Event received] Saving item: {} to category...", savedItem, getCategory());
        logger.debug("{} category received notification of new item: {}", getCategory().getName(), savedItem);
        decoratedPresenter.addItem((Item) savedItem);
    }
}
