package com.bulbview.recipeplanner.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RecipePlannerPresenter implements Presenter {

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
    public RecipePlannerPresenter(final WindowView window,
                                  final DailyRecipeListsContainerView dailyRecipeListView,
                                  final RecipeEditorFormView createRecipeFormView,
                                  final MasterRecipeListView masterRecipeListView,
                                  final Provider<Recipe> recipeProvider,
                                  final RecipeDao recipeDao,
                                  final RecipePlannerPresenterEventHandler presenterEventHandler) {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.debug("Creating Presenter...");
        this.window = window;
        this.dailyRecipeListView = dailyRecipeListView;
        this.createRecipeFormView = createRecipeFormView;
        this.recipeProvider = recipeProvider;
        this.masterRecipeListView = masterRecipeListView;
        this.recipeDao = recipeDao;
    }

    public void closeRecipeEditor() {
        window.hideRecipeEditorModalWindow();

    }

    public void createNewRecipe() {
        window.displayRecipeEditorModalWindow();
        createRecipeFormView.setRecipe(recipeProvider.get());
    }

    public void initialise() {
        createDailyLists();
        masterRecipeListView.setRecipes(recipeDao.getAll());
    }

    public void saveRecipe(final Recipe recipe) {
        logger.info("Saving recipe {}", recipe);
        window.hideRecipeEditorModalWindow();
    }

    @Override
    public void setStartDate(final Date date) {
        dailyRecipeListView.updateDateHeaders(date);
        window.showNotification("Starting date: " + date);
    }

    private void createDailyLists() {
        logger.debug("Creating daily lists...");
        for ( int i = 0; i < numberOfdays; i++ ) {
            dailyRecipeListView.createDailyList();
        }
    }
}
