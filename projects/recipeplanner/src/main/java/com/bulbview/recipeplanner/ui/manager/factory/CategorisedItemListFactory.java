package com.bulbview.recipeplanner.ui.manager.factory;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

@Component
public class CategorisedItemListFactory {
    
    @Autowired
    private ObjectFactory<CategorisedItemList> internalFactory;
    
    public CategorisedItemList createList(final String categoryName) {
        final CategorisedItemList categorisedItemList = internalFactory.getObject();
        categorisedItemList.setCategoryName(categoryName);
        categorisedItemList.init();
        return categorisedItemList;
    }
    
}
