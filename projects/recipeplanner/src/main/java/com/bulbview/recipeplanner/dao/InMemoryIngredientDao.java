package com.bulbview.recipeplanner.dao;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class InMemoryIngredientDao implements IngredientDao {

    private final Collection<Ingredient> ingredients;
    private final Logger                 logger;

    @Inject
    public InMemoryIngredientDao(final Collection<Ingredient> ingredients) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.ingredients = ingredients;
        addTestIngredients();
    }

    public final Ingredient createIngredient(final Category category,
                                             final String name) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setCategory(category);
        ingredient.setName(name);
        return ingredient;
    }

    @Override
    public Collection<Ingredient> getAll() {
        return ingredients;
    }

    @Override
    public void save(final Collection<Ingredient> ingredients) {
        logger.info("saving {} ingredient(s)", ingredients.size());
        ingredients.addAll(ingredients);
    }

    private final void addTestIngredients() {
        ingredients.add(createIngredient(Category.Meat_Fish_Poultry, "Chicken"));
        ingredients.add(createIngredient(Category.Tins, "Vegetable Oil"));
        ingredients.add(createIngredient(Category.Fruit_Vegetables, "Onion"));
        ingredients.add(createIngredient(Category.Fruit_Vegetables, "Courgette"));

    }

}
