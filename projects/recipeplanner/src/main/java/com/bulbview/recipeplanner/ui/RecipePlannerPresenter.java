package com.bulbview.recipeplanner.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.dao.RecipeDao;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;

@Presenter(view = RecipePlannerWindow.class)
public class RecipePlannerPresenter extends BasePresenter<WindowView, RecipePlannerEventBus> {

    @BindingAnnotation
    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NumberOfDailyRecipeLists {}

    private final WindowView                    window;
    private final DailyRecipeListsContainerView dailyRecipeListView;
    private final Logger                        logger;
    private final MasterRecipeListView          masterRecipeListView;
    private final RecipeDao                     recipeDao;
    private static int                          numberOfdays = 7;

    @Inject
    public RecipePlannerPresenter(final WindowView window,
                                  final RecipeDao recipeDao,
                                  final MasterRecipeListView masterRecipeListView,
                                  final DailyRecipeListsContainerView dailyRecipeListView) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipeDao = recipeDao;
        this.masterRecipeListView = masterRecipeListView;
        this.dailyRecipeListView = dailyRecipeListView;
        this.window = window;
    }

    public void onCloseRecipeEditor() {
        window.hideRecipeEditor();
    }

    public void onCreateNewRecipe() {
        window.showRecipeEditor();
    }

    public void onInitialise() {
        createDailyLists(numberOfdays);
        updateRecipeListView();
        setMasterRecipeListAsDropSource(masterRecipeListView, dailyRecipeListView);
    }

    public void onSaveRecipe(final Recipe recipe) {
        updateRecipeListView();
        window.hideRecipeEditor();
    }

    public void onSetStartDate(final Date startDate) {
        dailyRecipeListView.updateDateHeaders(startDate);
    }

    public void updateRecipeListView() {
        masterRecipeListView.setRecipes(recipeDao.getAll());
    }

    private void createDailyLists(final int numberOfDays) {
        logger.debug("Creating daily lists...");
        for ( int i = 0; i < numberOfDays; i++ ) {
            dailyRecipeListView.createDailyList();
        }
    }

    private void setMasterRecipeListAsDropSource(final MasterRecipeListView masterRecipeListView,
                                                 final DailyRecipeListsContainerView dailyRecipeListView) {
        final Collection<DailyRecipeList> dailyLists = dailyRecipeListView.getDailyLists();
        for ( final DailyRecipeList dailyRecipeList : dailyLists ) {
            dailyRecipeList.setDropSource(masterRecipeListView);
        }
    }
}
