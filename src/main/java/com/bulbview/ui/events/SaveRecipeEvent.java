package com.bulbview.ui.events;

import com.bulbview.recipeplanner.datamodel.Recipe;

public class SaveRecipeEvent {

    private final Recipe recipe;

    public SaveRecipeEvent(final Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

}
