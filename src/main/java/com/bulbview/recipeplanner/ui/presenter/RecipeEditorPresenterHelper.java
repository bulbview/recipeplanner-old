package com.bulbview.recipeplanner.ui.presenter;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RecipeEditorPresenterHelper {

    private final Provider<Recipe>     recipeProvider;
    private final Provider<Ingredient> ingredientProvider;

    @Inject
    public RecipeEditorPresenterHelper(final Provider<Ingredient> ingredientProvider,
                                       final Provider<Recipe> recipeProvider) {
        this.ingredientProvider = ingredientProvider;
        this.recipeProvider = recipeProvider;
    }

    public Ingredient createIngredient() {
        return ingredientProvider.get();
    }

    public Recipe createRecipe() {
        return recipeProvider.get();
    }
}
