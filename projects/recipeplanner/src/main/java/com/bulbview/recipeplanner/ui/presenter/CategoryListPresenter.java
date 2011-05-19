package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao;
import com.bulbview.recipeplanner.ui.Presenter;
import com.bulbview.recipeplanner.ui.helper.CategorisedItemList;

@Component
public class CategoryListPresenter extends Presenter {

    private CategorisedItemList categorisedItemList;

    @Autowired
    private ItemObjectifyDao             itemDao;

    public void addItem(final String itemName,
                        final String categoryName) {
        logger.debug("Adding item: {}, category: {}", itemName, categoryName);
        final Item item = new Item();
        item.setName(itemName);
        categorisedItemList.addListItem(itemDao.save(item));
    }

    @Override
    public void init() {
        // itemDao.getItemsFor(category)

    }

    public void setView(final CategorisedItemList categorisedItemList) {
        this.categorisedItemList = categorisedItemList;

    }

}
