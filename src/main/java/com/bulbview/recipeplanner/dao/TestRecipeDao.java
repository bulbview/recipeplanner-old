package com.bulbview.recipeplanner.dao;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestRecipeDao implements RecipeDao {

    private final Provider<Recipe>   recipeProvider;
    private final Collection<Recipe> recipes;

    @Inject
    public TestRecipeDao(final Provider<Recipe> recipeProvider, final Collection<Recipe> recipes) {
        this.recipeProvider = recipeProvider;
        this.recipes = recipes;
        initialiseRecipes();
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipes;
    }

    public Collection<Recipe> initialiseRecipes() {
        recipes.add(createRecipe("Courgette and herb risotto"));
        recipes.add(createRecipe("Simple Goan chicken curry"));
        recipes.add(createRecipe("Macaroni cheese"));
        recipes.add(createRecipe("Spicy chicken thighs with cucumber"));
        recipes.add(createRecipe("Paneer-stuffed peppers"));
        recipes.add(createRecipe("Chorizo)); pepper and couscous stuffed marrow and rosemary roasted marrow"));
        recipes.add(createRecipe("Collops of venison with blackberries"));
        recipes.add(createRecipe("Stir-fried crab with green Kampot pepper"));
        recipes.add(createRecipe("Simple sweet and sour chicken"));
        recipes.add(createRecipe("Loin of venison with a blackberry and sloe gin glaze served with clapshot rosti and parsnip crisps"));
        recipes.add(createRecipe("Vietnamese-style chicken salad"));
        recipes.add(createRecipe("Pumpkin ravioli with sage butter"));
        recipes.add(createRecipe("Chargrilled squid with a garlicy rocket salad"));
        recipes.add(createRecipe("Spicy lamb with wild and long grain rice and toasted almonds"));
        return recipes;
    }

    private Recipe createRecipe(final String string) {
        final Recipe recipe = recipeProvider.get();
        recipe.setName(string);
        return recipe;
    }
}
