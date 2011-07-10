package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoriesAccordionDecorator {
    
    @Autowired
    private ObjectFactory<CategorisedItemList> categorisedItemListFactory;
    private Accordion                          internalAccordion;
    
    public void addCategoryTab(final String categoryName) {
        final CategorisedItemList categorisedItemList = categorisedItemListFactory.getObject();
        categorisedItemList.setTopLevelPanel(new Panel());
        categorisedItemList.setCategoryName(categoryName);
        categorisedItemList.init();
        internalAccordion.addTab(categorisedItemList.getTopLevelPanel(), categoryName, null);
    }
    
    public void init() {
        
    }
    
    public void setAccordion(Accordion accordion) {
        this.internalAccordion = accordion;
        internalAccordion.setStyleName("opaque borderless");
        // internalAccordion.setImmediate(true);
        // internalAccordion.setSizeFull();
    }
    
}
