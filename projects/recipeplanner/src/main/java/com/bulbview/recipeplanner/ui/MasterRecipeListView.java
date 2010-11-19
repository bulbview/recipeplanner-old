package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface MasterRecipeListView {

    public abstract void setRecipes(final Collection<Recipe> recipes);

}