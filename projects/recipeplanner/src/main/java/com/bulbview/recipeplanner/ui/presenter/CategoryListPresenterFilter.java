package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryListPresenterFilter extends PresenterDecorator<ICategoryListPresenter, CategorisedItemList>
        implements ICategoryListPresenter {
    
    @Autowired
    private EntityDao<ItemCategory> categoryDao;
    
    @Autowired
    public CategoryListPresenterFilter(@Qualifier("underlyingCategoryListPresenter") final ICategoryListPresenter presenter) {
        super(presenter);
    }
    
    @Override
    public void addItem(final Item item) {
        if (matchCategory(item)) {
            decoratedPresenter.addItem(item);
        }
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
    public void init() {
        decoratedPresenter.init();
    }
    
    @Override
    public void setCategory(final String categoryName) {
        decoratedPresenter.setCategory(categoryName);
    }
    
    @Override
    public void setView(final CategorisedItemList viewManager) {
        decoratedPresenter.setView(viewManager);
    }
    
    private boolean matchCategory(final Item item) {
        return getCategory().equals(categoryDao.get(item.getCategory()));
    }
    
}