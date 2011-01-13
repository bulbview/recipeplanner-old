package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.Category;

public interface RecipeEditorFormView {

    public abstract void setCategoryOptions(final Collection<Category> categories);

    public abstract void setIngredientOptions(final Collection<Ingredient> ingredientOptions);

    public abstract void setRecipe(final Recipe recipe);

}