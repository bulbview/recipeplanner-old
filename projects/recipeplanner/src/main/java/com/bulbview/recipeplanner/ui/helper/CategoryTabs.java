package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@Component
public class CategoryTabs extends UiManager {

    private Accordion categoriesAccordion;

    public void addCategory(final ItemCategory itemCategory) {
        addCategoryTab(itemCategory);
    }

    @Override
    public void init() {
        categoriesAccordion.setImmediate(true);
        categoriesAccordion.setStyleName("opaque borderless");
    }

    public void setCategories(final Collection<ItemCategory> categories) {
        logger.info("Initialising categories: {}...", categories);
        for ( final ItemCategory category : categories ) {
            addCategoryTab(category);
        }
    }

    public void setComponent(final Accordion categoriesAccordion) {
        this.categoriesAccordion = categoriesAccordion;

    }

    private void addCategoryTab(final ItemCategory category) {
        final Panel categoryPanel = new Panel();
        categoriesAccordion.addTab(categoryPanel, category.getName(), null);
    }
}
