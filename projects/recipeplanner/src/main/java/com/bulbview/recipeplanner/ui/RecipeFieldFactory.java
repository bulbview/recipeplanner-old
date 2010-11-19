package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Container;
import com.vaadin.ui.ComboBox;

public class RecipeFieldFactory {

    private Collection<Ingredient>                        ingredientOptions;
    private Collection<Category>                          categoryNames;
    private final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider;
    private final Logger                                  logger;
    private Container                                     ingredientsTablecontainer;
    protected static final String                         CategoryPropertyId   = "Category";
    protected static final String                         IngredientPropertyId = "Ingredient";

    @Inject
    public RecipeFieldFactory(final Provider<IngredientValueChangeListener> ingredientValueChangeListenerProvider) {
        this.ingredientValueChangeListenerProvider = ingredientValueChangeListenerProvider;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public ComboBox createCategoriesCombobox(final Category category) {
        final ComboBox categoriesField = createCategoryComboBox();
        categoriesField.setValue(category);
        logger.debug("Category set for ingredient {}", category);
        return categoriesField;
    }

    public ComboBox createCategoryComboBox() {
        final ComboBox categoriesField = new ComboBox(CategoryPropertyId, categoryNames);
        categoriesField.setInputPrompt("Select category");
        categoriesField.setImmediate(true);
        categoriesField.setNewItemsAllowed(false);
        categoriesField.setEnabled(false);
        return categoriesField;
    }

    public ComboBox createIngredientsCombobox(final Ingredient ingredient) {
        final ComboBox ingredientsField = createIngredientComboBox();
        ingredientsField.setValue(ingredient);
        return ingredientsField;
    }

    public IngredientValueChangeListener createIngredientValueChangeListener() {
        final IngredientValueChangeListener ingredientValueChangeListener = ingredientValueChangeListenerProvider.get();
        ingredientValueChangeListener.setIngredientsTableContainer(ingredientsTablecontainer);
        return ingredientValueChangeListener;
    }

    public void set(final Container ingredientsTableContainer) {
        this.ingredientsTablecontainer = ingredientsTableContainer;

    }

    public void setCategoryNames(final Collection<Category> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredientOptions) {
        this.ingredientOptions = ingredientOptions;
    }

    protected ComboBox createIngredientComboBox() {
        final ComboBox ingredientsField = new ComboBox(IngredientPropertyId, ingredientOptions);
        ingredientsField.setInputPrompt("Select or Enter");
        ingredientsField.addListener(createIngredientValueChangeListener());
        ingredientsField.setNewItemsAllowed(true);
        ingredientsField.setImmediate(true);
        return ingredientsField;
    }

}
