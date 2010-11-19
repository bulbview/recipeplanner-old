package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.Category;

public interface RecipeEditorFormView {

    public abstract void activate(Recipe recipe,
                                  Collection<Ingredient> allIngredients,
                                  Collection<Category> list);

}