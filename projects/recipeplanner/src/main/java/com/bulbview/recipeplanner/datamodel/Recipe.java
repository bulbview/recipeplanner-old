package com.bulbview.recipeplanner.datamodel;

import java.util.Map;

import com.google.common.collect.Maps;

public class Recipe {

    private String                  name;

    private Map<Ingredient, String> ingredients;

    public Recipe() {
        this.ingredients = Maps.newHashMap();
        this.name = "<Enter Name>";
    }

    public void addIngredient(final Ingredient ingredient,
                              final String amount) {
        ingredients.put(ingredient, amount);
    }

    /**
     * @return the ingredients
     */
    public Map<Ingredient, String> getIngredients() {
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
    public void setIngredients(final Map<Ingredient, String> ingredients) {
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