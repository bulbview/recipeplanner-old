package com.bulbview.recipeplanner.ui;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter.Category;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractSelect;
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
            recipePlannerEventBus.ingredientSelected(categoryViewField, (String) ingredientComboBox.getValue());

        }

        private ViewField createViewField(final ComboBox categoryComboBox) {
            return new VaadinPropertyAdapter(categoryComboBox);
        }
    }

    private class NewIngredientHandler implements AbstractSelect.NewItemHandler {

        @Override
        public void addNewItem(final String newIngredientName) {
            if( isNewIngredient(newIngredientName) ) {
                recipePlannerEventBus.newIngredientSelected(newIngredientName);
            }
        }

        public boolean isNewIngredient(final String newIngredientName) {
            return !ingredientNames.contains(newIngredientName);
        }
    }

    private static final String         CategoryPropertyId   = "Category";

    private static final String         IngredientPropertyId = "Ingredient";

    private Collection<String>          ingredientNames;
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
        categoriesField.setImmediate(true);
        categoriesField.setValue("Hello");
        categoriesField.setNewItemsAllowed(false);
        // categoriesField.setEnabled(false);
        return categoriesField;
    }

    public ComboBox getComboBox(final Item item,
                                final String propertyId) {
        return (ComboBox) item.getItemProperty(propertyId).getValue();
    }

    public void set(final Collection<Ingredient> ingredients) {
        this.ingredientNames = getIngredientNames(ingredients);
    }

    public void setCategories(final Collection<Category> categories) {
        this.categoryNames = categories;
    }

    private ComboBox createIngredientComboBox() {
        final ComboBox ingredientsField = new ComboBox(IngredientPropertyId, ingredientNames);
        ingredientsField.setNewItemHandler(new NewIngredientHandler());
        ingredientsField.addListener(new IngredientValueChangeListener());
        ingredientsField.setNewItemsAllowed(true);
        ingredientsField.setImmediate(true);
        return ingredientsField;
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

    private Collection<String> getCategoryNames() {
        return Arrays.asList("Bakery", "Fruit & Veg", "Dairy & Eggs");
    }

    private Collection<String> getIngredientNames(final Collection<Ingredient> ingredients) {
        final Collection<String> ingredientNames = Lists.newArrayList();
        for ( final Ingredient ingredient : ingredients ) {
            ingredientNames.add(ingredient.getName());
        }
        return ingredientNames;
    }

    private Integer getNextTableItemIndex() {
        return new Integer(ingredientItemIndex++);
    }

}
