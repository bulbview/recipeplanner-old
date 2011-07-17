package com.bulbview.recipeplanner.ui.presenter;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

public interface ICategoryListPresenter extends IPresenter<CategorisedItemList> {
    
    public void addItem(Item item);
    
    public void addItemByName(String name);
    
    public ItemCategory getCategory();
    
    public void setCategory(String categoryName);
    
}