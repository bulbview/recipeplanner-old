package com.bulbview.recipeplanner.ui;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

@SuppressWarnings("serial")
public class IngredientValueChangeListener implements ValueChangeListener {

    private Container      ingredientsTableContainer;
    private final UiHelper uiHelper;

    public IngredientValueChangeListener(final UiHelper uiHelper) {
        this.uiHelper = uiHelper;
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
    }

    private ViewField createViewField(final ComboBox categoryComboBox) {
        return new VaadinPropertyAdapter(categoryComboBox);
    }

    private ComboBox getCategoryComboBoxFor(final ComboBox ingredientField) {
        final ComboBox comboBox = null;
        for ( final Object id : ingredientsTableContainer.getItemIds() ) {
            final Item item = ingredientsTableContainer.getItem(id);
            // final ComboBox ingredientComboBox = uiHelper.getComboBox(item,
            // IngredientNamePropertyId);
            // if( ingredientComboBox.equals(ingredientField) ) {
            // comboBox = uiHelper.getComboBox(item, CategoryPropertyId);
            // }
        }
        return comboBox;
    }

}