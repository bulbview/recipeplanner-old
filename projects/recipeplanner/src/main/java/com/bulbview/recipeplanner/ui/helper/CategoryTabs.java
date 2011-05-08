package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@Component
public class CategoryTabs extends UiManager {

    private Accordion categoriesAccordion;

    @Override
    public void init() {
        categoriesAccordion.setImmediate(true);
        categoriesAccordion.setStyleName("opaque borderless");
    }

    public void setCategories(final Collection<ItemCategory> categories) {
        logger.info("Initialising categories: {}...", categories);
        for ( final ItemCategory category : categories ) {
            final Panel categoryPanel = new Panel();
            categoriesAccordion.addTab(categoryPanel, category.getName(), null);
        }
    }

    public void setComponent(final Accordion categoriesAccordion) {
        this.categoriesAccordion = categoriesAccordion;

    }
}
