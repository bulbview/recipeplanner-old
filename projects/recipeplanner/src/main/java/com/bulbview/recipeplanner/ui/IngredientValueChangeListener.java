package com.bulbview.recipeplanner.ui;

import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.CategoryPropertyId;
import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.IngredientPropertyId;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

@SuppressWarnings("serial")
public class IngredientValueChangeListener implements ValueChangeListener {

    private final RecipePlannerEventBus recipePlannerEventBus;
    private Container                   ingredientsTableContainer;

    @Inject
    public IngredientValueChangeListener(final RecipePlannerEventBus recipePlannerEventBus) {
        this.recipePlannerEventBus = recipePlannerEventBus;
    }

    public void setIngredientsTableContainer(final Container ingredientsTableContainer) {
        this.ingredientsTableContainer = ingredientsTableContainer;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent event) {
        final ComboBox ingredientComboBox = (ComboBox) event.getProperty();
        final ComboBox categoryComboBox = getCategoryComboBoxFor(ingredientComboBox);
        final ViewField categoryViewField = createViewField(categoryComboBox);
        final ViewField ingredientViewField = createViewField(ingredientComboBox);
        recipePlannerEventBus.newOrExistingIngredientSelected(categoryViewField, ingredientViewField);
    }

    private ViewField createViewField(final ComboBox categoryComboBox) {
        return new VaadinPropertyAdapter(categoryComboBox);
    }

    private ComboBox getCategoryComboBoxFor(final ComboBox ingredientField) {
        for ( final Object id : ingredientsTableContainer.getItemIds() ) {
            final Item item = ingredientsTableContainer.getItem(id);
            final ComboBox ingredientComboBox = getComboBox(item, IngredientPropertyId);
            if( ingredientComboBox.equals(ingredientField) ) {
                final ComboBox comboBox = getComboBox(item, CategoryPropertyId);
                return comboBox;
            }
        }
        return null;
    }

    private ComboBox getComboBox(final Item item,
                                 final String propertyId) {
        return (ComboBox) item.getItemProperty(propertyId).getValue();
    }

}