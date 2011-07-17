package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.service.ItemService;

@Component
public class CategoryListPresenterFactory {
    
    @Autowired
    // @Qualifier(value = "underlyingCategoryListPresenter")
    private ObjectFactory<CategoryListPresenterFilter> factory;
    
    @Autowired
    private ItemService                                itemService;
    
    private ICategoryListPresenter                     presenterChainEnd;
    
    public ICategoryListPresenter create() {
        final ICategoryListPresenter newPresenter = factory.getObject();
        final ItemHandlerCategoryListPresenter presenterHandler = new ItemHandlerCategoryListPresenter(newPresenter);
        itemService.addObserver(presenterHandler);
        return presenterHandler;
    }
    
}
