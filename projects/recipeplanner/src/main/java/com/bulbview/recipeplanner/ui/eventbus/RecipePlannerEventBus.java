package com.bulbview.recipeplanner.ui.eventbus;

import java.util.Date;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.bulbview.recipeplanner.ui.ViewField;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter;

public interface RecipePlannerEventBus extends EventBus {

    @Event(handlers = { RecipePlannerPresenter.class })
    public void closeRecipeEditor();

    @Event(handlers = { RecipeEditorPresenter.class })
    public void createNewRecipe();

    @Event(handlers = { RecipeEditorPresenter.class })
    public void editRecipe(Recipe recipe);

    @Event(handlers = { RecipePlannerPresenter.class })
    public void initialise();

    @Event(handlers = { RecipeEditorPresenter.class })
    public void newOrExistingIngredientSelected(final ViewField categoryField,
                                                final ViewField ingredientField);

    @Event(handlers = { RecipePlannerPresenter.class, RecipeEditorPresenter.class })
    public void saveRecipe(Recipe recipe);

    @Event(handlers = { RecipePlannerPresenter.class })
    public void setStartDate(final Date date);

}
