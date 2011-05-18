package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.persistence.JdoDao;
import com.bulbview.recipeplanner.ui.Presenter;
import com.bulbview.recipeplanner.ui.helper.CategorisedItemList;

@Component
public class CategoryListPresenter extends Presenter {

    private CategorisedItemList categorisedItemList;

    @Autowired
    private JdoDao<Item>        itemDao;

    public void addItem(final String itemName,
                        final String categoryName) {
        logger.debug("Adding item: {}, category: {}", itemName, categoryName);
        final Item item = new Item();
        item.setName(itemName);
        categorisedItemList.addListItem(itemDao.save(item));
    }

    public void setView(final CategorisedItemList categorisedItemList) {
        this.categorisedItemList = categorisedItemList;

    }

}
