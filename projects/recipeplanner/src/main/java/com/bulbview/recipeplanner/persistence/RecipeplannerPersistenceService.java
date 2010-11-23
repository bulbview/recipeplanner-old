package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.dao.IngredientDao;
import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;

public class RecipeplannerPersistenceService {

    private final IngredientDao ingredientDao;
    private final RecipeDao     recipeDao;
    private final Logger        logger;

    @Inject
    public RecipeplannerPersistenceService(final IngredientDao ingredientDao, final RecipeDao recipeDao) {
        this.ingredientDao = ingredientDao;
        this.recipeDao = recipeDao;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Collection<Ingredient> getIngredients() {
        final Collection<Ingredient> ingredients = ingredientDao.getAll();
        logger.debug("retrievied ingredients: " + ingredients);
        return ingredients;
    }

    public void saveRecipe(final Recipe recipe) {
        recipeDao.saveRecipe(recipe);

    }

}
