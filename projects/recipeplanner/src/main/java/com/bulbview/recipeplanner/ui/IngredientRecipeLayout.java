package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.vaadin.ui.HorizontalLayout;

public class IngredientRecipeLayout extends HorizontalLayout {

    private final Logger logger;

    @Inject
    public IngredientRecipeLayout(final MasterRecipeList masterRecipeList,
                                  final IngredientsAccordion ingredientsAccordion) {
        logger = LoggerFactory.getLogger(getClass());
        logger.info("...{} created", getClass().getName());
        setMargin(true);
        addComponent(ingredientsAccordion);
        addComponent(masterRecipeList);
        setExpandRatio(masterRecipeList, 1);
        // setWidth("400px");
        setSizeFull();
    }

}
