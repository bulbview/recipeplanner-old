package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.spring.RecipePlannerApplication;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;

@Presenter(view = RecipePlannerWindow.class)
public class RecipePlannerPresenter extends BasePresenter<WindowView, RecipePlannerEventBus> {

    // private final WindowView window;
    // private final DailyRecipeListsContainerView dailyRecipeListView;
    private final Logger             logger;
    private RecipePlannerApplication recipePlannerApplication;

    // private final MasterRecipeListView masterRecipeListView;
    // private final RecipeDao recipeDao;
    // private static int numberOfdays = 7;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void createNewRecipe() {
        recipePlannerApplication.displayRecipeWindow(new Recipe());
    }

    public void setRecipePlannerApplication(final RecipePlannerApplication recipePlannerApplication) {
        this.recipePlannerApplication = recipePlannerApplication;
    }

    private void setMasterRecipeListAsDropSource(final MasterRecipeListView masterRecipeListView,
                                                 final DailyRecipeListsContainerView dailyRecipeListView) {
        final Collection<DailyRecipeList> dailyLists = dailyRecipeListView.getDailyLists();
        for ( final DailyRecipeList dailyRecipeList : dailyLists ) {
            dailyRecipeList.setDropSource(masterRecipeListView);
        }
    }
}
