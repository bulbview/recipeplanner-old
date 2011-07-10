package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.manager.CategoriesAccordionDecorator;
import com.vaadin.ui.Accordion;

@Component
public class CategoriesViewFactory {
    
    @Autowired
    private ObjectFactory<CategoriesAccordionDecorator> factory;
    
    public CategoriesAccordionDecorator createCategoriesView(Accordion categoryAccordion) {
        CategoriesAccordionDecorator categoriesAccordionDecorator = factory.getObject();
        categoriesAccordionDecorator.setAccordion(categoryAccordion);
        return categoriesAccordionDecorator;
    }
    
}
