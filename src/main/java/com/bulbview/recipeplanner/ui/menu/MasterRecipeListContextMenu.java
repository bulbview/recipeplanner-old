package com.bulbview.recipeplanner.ui.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.ui.handlers.ActionFactory;
import com.google.inject.Inject;
import com.vaadin.event.Action;

@SuppressWarnings("serial")
public class MasterRecipeListContextMenu implements Action.Handler {

    private final Action                newRecipeAction;
    private final Logger                logger;
    private final RecipePlannerEventBus recipePlannerEventBus;
    private final Action                editRecipeAction;
    private static final String         newRecipeActionLabel  = "New Recipe...";
    private static final String         editRecipeActionLabel = "Edit Recipe...";

    @Inject
    public MasterRecipeListContextMenu(final ActionFactory actionFactory,
                                       final RecipePlannerEventBus recipePlannerEventBus) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipePlannerEventBus = recipePlannerEventBus;
        newRecipeAction = actionFactory.create(newRecipeActionLabel);
        editRecipeAction = actionFactory.create(editRecipeActionLabel);
    }

    public void findCommandFor(final Action action,
                               final Recipe recipe) {
        // wrap in command pattern
        if( action.equals(newRecipeAction) ) {
            logger.debug("Calling create recipe event...");
            recipePlannerEventBus.createNewRecipe();
        } else if( action.equals(editRecipeAction) ) {
            recipePlannerEventBus.editRecipe(recipe);

        }
    }

    public Action[] getActions(final Object target,
                               final Object sender) {
        return new Action[] { newRecipeAction, editRecipeAction };
    }

    public void handleAction(final Action action,
                             final Object sender,
                             final Object target) {
        findCommandFor(action, (Recipe) target);

    }
}
