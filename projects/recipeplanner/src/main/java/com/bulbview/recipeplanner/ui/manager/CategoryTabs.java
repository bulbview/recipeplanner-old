package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@Component
public class CategoryTabs extends ViewManager<CategoryTabsPresenter> {

    private Accordion                          categoriesAccordion;
    @Autowired
    private ObjectFactory<CategorisedItemView> categorisedItemListFactory;

    public void addCategoryTab(final String categoryName) {
        final CategorisedItemView categorisedItemList = categorisedItemListFactory.getObject();
        categorisedItemList.setTopLevelPanel(new Panel());
        categorisedItemList.setCategoryName(categoryName);
        categorisedItemList.init();
        categoriesAccordion.addTab(categorisedItemList.getTopLevelPanel(), categoryName, null);
    }

    @Override
    public void init() {
        categoriesAccordion.setImmediate(true);
        categoriesAccordion.setStyleName("opaque borderless");
    }

    @Autowired
    public void setComponent(final MainWindow mainWindow) {
        this.categoriesAccordion = mainWindow.getCategoriesAccordion();
    }
}
