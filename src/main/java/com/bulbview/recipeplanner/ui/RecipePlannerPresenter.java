package com.bulbview.recipeplanner.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Presenter(view = RecipePlannerWindow.class)
public class RecipePlannerPresenter extends BasePresenter<WindowView, RecipePlannerEventBus> {

    @BindingAnnotation
    @Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NumberOfDailyRecipeLists {}

    private final WindowView                    window;
    private final DailyRecipeListsContainerView dailyRecipeListView;
    private final Provider<Recipe>              recipeProvider;
    private final RecipeEditorFormView          createRecipeFormView;
    private final Logger                        logger;
    private final MasterRecipeListView          masterRecipeListView;
    private final RecipeDao                     recipeDao;
    private static int                          numberOfdays = 7;

    @Inject
    public RecipePlannerPresenter(final Provider<Recipe> recipeProvider,
                                  final RecipeEditorFormView createRecipeFormView,
                                  final WindowView window,
                                  final RecipeDao recipeDao,
                                  final MasterRecipeListView masterRecipeListView,
                                  final DailyRecipeListsContainerView dailyRecipeListView) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipeProvider = recipeProvider;
        this.recipeDao = recipeDao;
        this.createRecipeFormView = createRecipeFormView;
        this.masterRecipeListView = masterRecipeListView;
        this.dailyRecipeListView = dailyRecipeListView;
        this.window = window;
        logger.debug("...created Presenter with recipeProvider {}", recipeProvider);
    }

    public void onCloseRecipeEditor() {
        window.hideRecipeEditorModalWindow();
    };

    public void onCreateNewRecipe() {
        window.displayRecipeEditorModalWindow();
        createRecipeFormView.setRecipe(recipeProvider.get());
    }

    public void onInitialise() {
        createDailyLists(numberOfdays);
        updateRecipeListView();
    }

    public void onSaveRecipe(final Recipe recipe) {
        logger.info("Saving recipe {}", recipe);
        recipeDao.saveRecipe(recipe);
        updateRecipeListView();
        window.hideRecipeEditorModalWindow();
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
}
