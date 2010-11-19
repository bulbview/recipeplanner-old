package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.dao.IngredientDao;
import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;

public class RecipeplannerPersistenceService {

    private final IngredientDao ingredientDao;
    private final RecipeDao     recipeDao;

    @Inject
    public RecipeplannerPersistenceService(final IngredientDao ingredientDao, final RecipeDao recipeDao) {
        this.ingredientDao = ingredientDao;
        this.recipeDao = recipeDao;
    }

    public Collection<Ingredient> getIngredients() {
        return ingredientDao.getAll();
    }

    public void saveRecipe(final Recipe recipe) {
        recipeDao.saveRecipe(recipe);

    }

}
