package com.bulbview.recipeplanner.ui;

import java.util.Date;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface RecipePlannerEventBus extends EventBus {

    @Event(handlers = { RecipePlannerPresenter.class })
    public void closeRecipeEditor();

    @Event(handlers = { RecipePlannerPresenter.class })
    public void createNewRecipe();

    @Event(handlers = { RecipePlannerPresenter.class })
    public void initialise();

    @Event(handlers = { RecipePlannerPresenter.class })
    public void saveRecipe(Recipe recipe);

    @Event(handlers = { RecipePlannerPresenter.class })
    public void setStartDate(final Date date);

}
