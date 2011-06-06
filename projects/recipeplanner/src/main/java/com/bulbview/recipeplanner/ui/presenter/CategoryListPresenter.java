package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao;
import com.bulbview.recipeplanner.persistence.ObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryListPresenter extends Presenter {

    private CategorisedItemList        categorisedItemList;
    private ItemCategory               category;

    @Autowired
    private ObjectifyDao<ItemCategory> categoryDao;
    @Autowired
    private ItemObjectifyDao           itemDao;

    public void addItem(final String itemName) {
        final Item savedItem = itemDao.save(createItem(itemName));
        categorisedItemList.addListItem(savedItem);
    }

    @Override
    public void init() {
        final Collection<Item> categoryItems = itemDao.getAllFor(category);
        for ( final Item item : categoryItems ) {
            categorisedItemList.addListItem(item);
        }

    }

    public void setCategory(final String categoryName) {
        this.category = categoryDao.get(categoryName);
    }

    public void setView(final CategorisedItemList categorisedItemList) {
        this.categorisedItemList = categorisedItemList;
    }

    private Item createItem(final String itemName) {
        logger.debug("Adding item: {}, category: {}", itemName, category.getName());
        final Item item = new Item();
        item.setName(itemName);
        item.setCategory(category);
        return item;
    }

}
