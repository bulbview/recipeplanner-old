package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Container;
import com.vaadin.ui.ComboBox;

public class RecipeFieldFactory {

    private Collection<Ingredient>                        ingredientOptions;
    private Collection<Category>                          categoryOptions;
    private final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider;
    private Container                                     ingredientsTablecontainer;
    protected static final String                         CategoryPropertyId   = "Category";
    protected static final String                         IngredientNamePropertyId = "Ingredient";

    @Inject
    public RecipeFieldFactory(final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider) {
        this.ingredientValueChangeListenerProvider = ingredientValueChangeListenerProvider;
    }

    public ComboBox createCategoryComboBox() {
        final ComboBox categoriesField = new ComboBox(CategoryPropertyId, categoryOptions);
        categoriesField.setInputPrompt("Select category");
        categoriesField.setImmediate(true);
        categoriesField.setNewItemsAllowed(false);
        categoriesField.setEnabled(false);
        return categoriesField;
    }

    public IngredientValueChangeListener createIngredientValueChangeListener() {
        final IngredientValueChangeListener ingredientValueChangeListener = ingredientValueChangeListenerProvider.get();
        ingredientValueChangeListener.setIngredientsTableContainer(ingredientsTablecontainer);
        return ingredientValueChangeListener;
    }

    public void set(final Container ingredientsTableContainer) {
        this.ingredientsTablecontainer = ingredientsTableContainer;

    }

    protected ComboBox createIngredientComboBox() {
        final ComboBox ingredientsField = new ComboBox(IngredientNamePropertyId, ingredientOptions);
        ingredientsField.setInputPrompt("Select or Enter");
        ingredientsField.addListener(createIngredientValueChangeListener());
        ingredientsField.setNewItemsAllowed(true);
        ingredientsField.setImmediate(true);
        return ingredientsField;
    }

}
