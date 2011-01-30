package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface MasterRecipeListView {

    public void setRecipes(final Collection<Recipe> recipes);

}