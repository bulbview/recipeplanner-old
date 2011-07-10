package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.TabSheet.Tab;

/**
 * Decorator to add functionality to concrete view component - required due to
 * vaadin component creation within the vaadin view designer
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoriesAccordionDecorator {
    
    private Accordion internalAccordion;
    
    public Tab addCategoryTab(final String categoryName, final CategorisedItemList categorisedItemList) {
        return internalAccordion.addTab(categorisedItemList.getTopLevelPanel(), categoryName, null);
    }
    
    public void init() {
        
    }
    
    public void setAccordion(final Accordion accordion) {
        this.internalAccordion = accordion;
        internalAccordion.setStyleName("opaque borderless");
    }
    
}
