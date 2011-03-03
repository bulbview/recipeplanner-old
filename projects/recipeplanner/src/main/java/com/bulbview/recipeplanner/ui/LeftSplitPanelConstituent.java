package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.henrik.drawer.Drawer;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;

public class LeftSplitPanelConstituent extends VerticalLayout {

    private final Logger logger;

    @Inject
    public LeftSplitPanelConstituent(final MasterRecipeList masterRecipeList,
                                     final ApplicationNameLabel applicationNameLabel,
                                     final IngredientsAccordion ingredientsAccordion) {
        logger = LoggerFactory.getLogger(getClass());
        logger.info("...{} created", getClass().getName());
        addComponent(applicationNameLabel);
        // addComponent(ingredientsAccordion);
        addComponent(new Drawer("Ingredients", ingredientsAccordion));
        addComponent(masterRecipeList);
        setExpandRatio(masterRecipeList, 1);
        setSizeFull();
        // recipeList.setSizeFull();
    }

}
