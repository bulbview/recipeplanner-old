package com.bulbview.recipeplanner.ui;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

@SuppressWarnings("serial")
public class IngredientValueChangeListener implements ValueChangeListener {

    private final IngredientsTable ingredientsTable;
    private RecipePlannerEventBus  recipePlannerEventBus;

    IngredientValueChangeListener(final IngredientsTable ingredientsTable) {
        this.ingredientsTable = ingredientsTable;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent event) {
        final ComboBox ingredientComboBox = (ComboBox) event.getProperty();
        final ComboBox categoryComboBox = ingredientsTable.getCategoryComboBoxFor(ingredientComboBox);
        final ViewField categoryViewField = createViewField(categoryComboBox);
        final ViewField ingredientViewField = createViewField(ingredientComboBox);
        recipePlannerEventBus.newOrExistingIngredientSelected(categoryViewField, ingredientViewField);
    }

    private ViewField createViewField(final ComboBox categoryComboBox) {
        return new VaadinPropertyAdapter(categoryComboBox);
    }

}