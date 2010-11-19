package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class IngredientsTable extends Table {

    public class IngredientValueChangeListener implements ValueChangeListener {

        @Override
        public void valueChange(final Property.ValueChangeEvent event) {
            final ComboBox ingredientComboBox = (ComboBox) event.getProperty();
            final ComboBox categoryComboBox = getCategoryComboBoxFor(ingredientComboBox);
            final ViewField categoryViewField = createViewField(categoryComboBox);
            final ViewField ingredientViewField = createViewField(ingredientComboBox);
            recipePlannerEventBus.newOrExistingIngredientSelected(categoryViewField, ingredientViewField);
        }

    }

    private static final String         CategoryPropertyId   = "Category";

    private static final String         IngredientPropertyId = "Ingredient";

    private Collection<Ingredient>      ingredientOptions;

    private int                         ingredientItemIndex;

    private final Logger                logger;
    private final RecipePlannerEventBus recipePlannerEventBus;
    private Collection<Category>        categoryNames;

    @Inject
    public IngredientsTable(final RecipePlannerEventBus recipePlannerEventBus) {
        this.recipePlannerEventBus = recipePlannerEventBus;
        this.logger = LoggerFactory.getLogger(getClass());
        setEditable(true);
        setImmediate(true);
        setNullSelectionAllowed(true);
        addContainerProperty(CategoryPropertyId, ComboBox.class, null);
        addContainerProperty(IngredientPropertyId, ComboBox.class, null);
    }

    public void addRow() {
        logger.debug("Adding ingredient row to recipeEditor...");
        final ComboBox categoriesField = createCategoryComboBox();
        final ComboBox ingredientsField = createIngredientComboBox();
        addItem(new ComboBox[] { categoriesField, ingredientsField }, getNextTableItemIndex());
    }

    public ComboBox createCategoryComboBox() {
        final ComboBox categoriesField = new ComboBox(CategoryPropertyId, categoryNames);
        categoriesField.setInputPrompt("Select category");
        categoriesField.setImmediate(true);
        categoriesField.setNewItemsAllowed(false);
        categoriesField.setEnabled(false);
        return categoriesField;
    }

    public ComboBox getComboBox(final Item item,
                                final String propertyId) {
        return (ComboBox) item.getItemProperty(propertyId).getValue();
    }

    public void setCategories(final Collection<Category> categories) {
        this.categoryNames = categories;
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredients) {
        this.ingredientOptions = ingredients;
    }

    public void setRecipeIngredients(final Collection<Ingredient> ingredients) {
        logger.debug("{} ingredient(s) found", ingredients.size());
        for ( final Ingredient ingredient : ingredients ) {
            addRow(ingredient);
        }
    }

    private void addRow(final Ingredient ingredient) {
        logger.debug("Adding ingredient row to recipeEditor...");
        final ComboBox categoriesField = createCategoryComboBox();
        categoriesField.setValue(ingredient.getCategory());
        final ComboBox ingredientsField = createIngredientComboBox();
        ingredientsField.setValue(ingredient);
        addItem(new ComboBox[] { categoriesField, ingredientsField }, getNextTableItemIndex());
    }

    private ComboBox createIngredientComboBox() {
        final ComboBox ingredientsField = new ComboBox(IngredientPropertyId, ingredientOptions);
        ingredientsField.setInputPrompt("Select or Enter");
        ingredientsField.addListener(new IngredientValueChangeListener());
        ingredientsField.setNewItemsAllowed(true);
        ingredientsField.setImmediate(true);
        return ingredientsField;
    }

    private ViewField createViewField(final ComboBox categoryComboBox) {
        return new VaadinPropertyAdapter(categoryComboBox);
    }

    private ComboBox getCategoryComboBoxFor(final ComboBox ingredientField) {
        for ( final Object id : getItemIds() ) {
            final Item item = getItem(id);
            final ComboBox ingredientComboBox = getComboBox(item, IngredientPropertyId);
            if( ingredientComboBox.equals(ingredientField) ) {
                return getComboBox(item, CategoryPropertyId);
            }
        }
        return null;
    }

    private int getNextTableItemIndex() {
        return ingredientItemIndex++;
    }

    private boolean isNewIngredient(final String newIngredientName) {
        return !ingredientOptions.contains(newIngredientName);
    }

}
