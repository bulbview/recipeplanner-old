package com.bulbview.recipeplanner.ui.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.ui.handlers.ActionFactory;
import com.google.inject.Inject;
import com.vaadin.event.Action;

@SuppressWarnings("serial")
public class MasterRecipeListContextMenu implements Action.Handler {

    private final Action                newRecipeAction;
    private final Logger                logger;
    private final RecipePlannerEventBus recipePlannerEventBus;
    private static final String         newRecipeActionLabel = "New Recipe...";

    @Inject
    public MasterRecipeListContextMenu(final ActionFactory actionFactory,
                                       final RecipePlannerEventBus recipePlannerEventBus) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipePlannerEventBus = recipePlannerEventBus;
        newRecipeAction = actionFactory.create(newRecipeActionLabel);
    }

    public Action[] getActions(final Object target,
                               final Object sender) {
        return new Action[] { newRecipeAction };
    }

    public void handleAction(final Action action,
                             final Object sender,
                             final Object target) {
        if( action.equals(newRecipeAction) ) {
            logger.debug("Calling create recipe event...");
            recipePlannerEventBus.createNewRecipe();
        }

    }
}
