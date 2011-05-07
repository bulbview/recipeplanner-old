package com.bulbview.recipeplanner.ui.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.ViewField;

public class RecipeEditorPresenter {

    private static final String        selectCategoryNotification = "Select category for new ingredient: %s";
    private final Logger               logger;
    private final RecipeEditorFormView recipeFormView;

    public RecipeEditorPresenter(final RecipeEditorFormView recipeEditorFormView) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.recipeFormView = recipeEditorFormView;
    }

    public void deactivateCategoryField(final ViewField categoryField) {
        categoryField.setEnabled(false);
    }

    public boolean isExistingIngredient(final Object value) {
        return value instanceof Ingredient;
    }

    public void onCreateNewRecipe() {
        // editRecipe(helper.createRecipe());
    }

    public void onEditRecipe(final Recipe recipe) {
        editRecipe(recipe);
    }

    public void onNewOrExistingIngredientSelected(final ViewField categoryField,
                                                  final ViewField ingredientField) {
        final Object value = ingredientField.getValue();
        if( isExistingIngredient(value) ) {
            deactivateCategoryField(categoryField);
            setCategoryForIngredient(categoryField, value);
        } else {
            categoryField.setEnabled(true);
            categoryField.focus();
            // windowView.showNotification(String.format(selectCategoryNotification,
            // ingredient));
        }
    }

    public void onSaveRecipe(final Recipe recipe) {
        logger.info("Saving - {}...", recipe);
        // windowView.hideRecipeEditor();
    }

    public void setCategoryForIngredient(final ViewField categoryField,
                                         final Object value) {
        categoryField.setValue(getCategory(value));
    }

    private void editRecipe(final Recipe recipe) {
        recipeFormView.setRecipe(recipe);
        // windowView.showRecipeEditor();
    }

    private ItemCategory getCategory(final Object value) {
        return ( (Ingredient) value ).getCategory();
    }

}
