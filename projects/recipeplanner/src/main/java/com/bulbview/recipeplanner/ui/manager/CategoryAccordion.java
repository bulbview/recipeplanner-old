package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class CategoryAccordion extends Accordion {
    
    @Autowired
    private ObjectFactory<CategorisedItemView> categorisedItemListFactory;
    
    public void addCategoryTab(final String categoryName) {
        final CategorisedItemView categorisedItemList = categorisedItemListFactory.getObject();
        categorisedItemList.setTopLevelPanel(new Panel());
        categorisedItemList.setCategoryName(categoryName);
        categorisedItemList.init();
        addTab(categorisedItemList.getTopLevelPanel(), categoryName, null);
    }
    
    public void init() {
        setImmediate(true);
        setStyleName("opaque borderless");
    }
    
}
