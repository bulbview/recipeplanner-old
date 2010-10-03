package com.bulbview.recipeplanner.dao;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface RecipeDao {

    Collection<Recipe> getAll();

}
