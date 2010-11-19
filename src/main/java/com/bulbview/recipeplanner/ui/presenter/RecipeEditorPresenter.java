package com.bulbview.recipeplanner.ui.presenter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.RecipeplannerPersistenceService;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.ViewField;
import com.bulbview.recipeplanner.ui.WindowView;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;

@Presenter(view = RecipeEditorFormView.class)
public class RecipeEditorPresenter extends BasePresenter<RecipeEditorFormView, RecipePlannerEventBus> {

    private final RecipeEditorFormView            recipeFormView;
    private final Logger                          logger;
    private Collection<Ingredient>                cachedIngredients;
    private final WindowView                      windowView;
    private final RecipeplannerPersistenceService persistenceService;
    private final RecipeEditorPresenterHelper     helper;
    private static final String                   selectCategoryNotification = "Select category for new ingredient: %s";

    @Inject
    public RecipeEditorPresenter(final RecipeplannerPersistenceService persistenceService,
                                 final RecipeEditorFormView recipeEditorFormView,
                                 final RecipeEditorPresenterHelper helper,
                                 final WindowView windowView) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipeFormView = recipeEditorFormView;
        this.windowView = windowView;
        this.persistenceService = persistenceService;
        this.helper = helper;
    }

    public Ingredient createIngredient(final ViewField ingredientField) {
        final String ingredientName = (String) ingredientField.getValue();
        final Ingredient ingredient = helper.createIngredient();
        ingredient.setName(ingredientName);
        return ingredient;
    }

    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }

    public boolean isExistingIngredient(final Object value) {
        return value instanceof Ingredient;
    }

    public void onCreateNewRecipe() {
        retrievePersistedIngredients();
        displayRecipeEditor(helper.createRecipe());
    }

    public void onEditRecipe(final Recipe recipe) {
        retrievePersistedIngredients();
        displayRecipeEditor(recipe);
    }

    public void retrievePersistedIngredients() {
        this.cachedIngredients = persistenceService.getIngredients();
    }

    public void onNewOrExistingIngredientSelected(final ViewField categoryField,
                                                  final ViewField ingredientField) {
        final Object value = ingredientField.getValue();
        if( isExistingIngredient(value) ) {
            categoryField.setEnabled(false);
            categoryField.setValue(getCategory(value));
        } else {
            categoryField.setEnabled(true);
            categoryField.focus();
            final Ingredient ingredient = createIngredient(ingredientField);
            windowView.showNotification(String.format(selectCategoryNotification, ingredient));
        }
    }

    public void onSaveRecipe(final Recipe recipe) {
        logger.info("Saving recipe {}...", recipe);
        persistenceService.saveRecipe(recipe);
    }

    private void displayRecipeEditor(final Recipe recipe) {
        recipeFormView.activate(recipe, cachedIngredients, getCategories());
    }

    private Category getCategory(final Object value) {
        return ( (Ingredient) value ).getCategory();
    }

}