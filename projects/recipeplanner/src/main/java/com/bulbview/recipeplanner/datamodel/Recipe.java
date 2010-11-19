package com.bulbview.recipeplanner.datamodel;

import java.util.Collection;

import com.google.inject.Inject;

public class Recipe {

    private String                 name;

    private Collection<Ingredient> ingredients;

    @Inject
    public Recipe(final IngredientsCollectionFactory ingredientsCollectionFactory) {
        this.ingredients = ingredientsCollectionFactory.create();
        this.name = "<Enter Name>";
    }

    public void addIngredient(final Ingredient ingredient) {
        ingredients.add(ingredient);

    }

    /**
     * @return the ingredients
     */
    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param ingredients the ingredients to set
     */
    public void setIngredients(final Collection<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Recipe [name=%s, ingredients=%s]", name, ingredients);
    }
}