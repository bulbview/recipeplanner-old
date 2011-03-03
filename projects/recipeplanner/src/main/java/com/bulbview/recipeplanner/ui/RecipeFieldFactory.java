package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Container;

public class RecipeFieldFactory {

    private final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider;
    private Container                                     ingredientsTablecontainer;
    protected static final String                         CategoryPropertyId       = "Amount";
    protected static final String                         IngredientNamePropertyId = "Ingredient";

    @Inject
    public RecipeFieldFactory(final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider) {
        this.ingredientValueChangeListenerProvider = ingredientValueChangeListenerProvider;
    }

    public IngredientValueChangeListener createIngredientValueChangeListener() {
        final IngredientValueChangeListener ingredientValueChangeListener = ingredientValueChangeListenerProvider.get();
        ingredientValueChangeListener.setIngredientsTableContainer(ingredientsTablecontainer);
        return ingredientValueChangeListener;
    }

    public void set(final Container ingredientsTableContainer) {
        this.ingredientsTablecontainer = ingredientsTableContainer;

    }

}
