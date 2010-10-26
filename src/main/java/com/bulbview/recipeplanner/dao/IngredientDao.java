package com.bulbview.recipeplanner.dao;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Ingredient;

public interface IngredientDao {

    public Collection<Ingredient> getAll();

    public void save(final Collection<Ingredient> transientIngredients);
}
