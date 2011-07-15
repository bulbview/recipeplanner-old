package com.bulbview.recipeplanner.ui.manager.factory;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenterFactory;
import com.bulbview.recipeplanner.ui.presenter.ICategoryListPresenter;

@Component
public class CategorisedItemListFactory {
    
    @Autowired
    private CategoryListPresenterFactory       categoryListPresenterFactory;
    
    @Autowired
    private ObjectFactory<CategorisedItemList> internalFactory;
    
    public CategorisedItemList createList(final String categoryName) {
        final CategorisedItemList categorisedItemList = internalFactory.getObject();
        categorisedItemList.setCategoryName(categoryName);
        final ICategoryListPresenter presenter = categoryListPresenterFactory.create();
        categorisedItemList.setPresenter(presenter);
        presenter.setView(categorisedItemList);
        categorisedItemList.init();
        return categorisedItemList;
    }
    
}
