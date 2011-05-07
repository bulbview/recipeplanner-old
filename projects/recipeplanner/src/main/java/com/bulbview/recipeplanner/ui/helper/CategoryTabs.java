package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@Component
public class CategoryTabs extends UiManager {

    private Accordion accordion;

    @Override
    public void init() {

    }

    public void setCategories(final Collection<ItemCategory> categories) {
        for ( final ItemCategory category : categories ) {
            final Panel categoryPanel = new Panel();
            accordion.addTab(categoryPanel, category.getName(), null);
        }
    }

    public void setCategoriesComponent(final Accordion accordion) {
        this.accordion = accordion;
    }
}
