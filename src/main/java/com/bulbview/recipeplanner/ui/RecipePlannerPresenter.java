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

    private WindowView                    window;
    private DailyRecipeListsContainerView dailyRecipeListView;
    private Provider<Recipe>              recipeProvider;
    private RecipeEditorFormView          createRecipeFormView;
    private final Logger                  logger;
    private MasterRecipeListView          masterRecipeListView;
    private RecipeDao                     recipeDao;
    private static int                    numberOfdays = 7;

    @Inject
    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.debug("...created Presenter");
    }

    public void onCloseRecipeEditor() {
        window.hideRecipeEditorModalWindow();
    };

    public void onCreateNewRecipe() {
        window.displayRecipeEditorModalWindow();
        logger.debug("+++++++++++{}", createRecipeFormView);
        logger.debug("+++++++++++{}", recipeProvider);
        createRecipeFormView.setRecipe(recipeProvider.get());
    }

    public void onInitialise() {
        createDailyLists();
        masterRecipeListView.setRecipes(recipeDao.getAll());
    }

    public void onSaveRecipe(final Recipe recipe) {
        logger.info("Saving recipe {}", recipe);
        window.hideRecipeEditorModalWindow();
    }

    public void onSetStartDate(final Date startDate) {
        dailyRecipeListView.updateDateHeaders(startDate);
    }

    @Inject
    public void setCreateRecipeFormView(final RecipeEditorFormView createRecipeFormView) {
        this.createRecipeFormView = createRecipeFormView;
    }

    @Inject
    public void setDailyRecipeListView(final DailyRecipeListsContainerView dailyRecipeListView) {
        this.dailyRecipeListView = dailyRecipeListView;
    }

    @Inject
    public void setMasterRecipeListView(final MasterRecipeListView masterRecipeListView) {
        this.masterRecipeListView = masterRecipeListView;
    }

    @Inject
    public void setRecipeDao(final RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public void setRecipeProvider(final Provider<Recipe> recipeProvider) {
        this.recipeProvider = recipeProvider;
    }

    @Inject
    public void setWindow(final WindowView window) {
        this.window = window;
    }

    private void createDailyLists() {
        logger.debug("Creating daily lists...");
        for ( int i = 0; i < numberOfdays; i++ ) {
            dailyRecipeListView.createDailyList();
        }
    }
}
