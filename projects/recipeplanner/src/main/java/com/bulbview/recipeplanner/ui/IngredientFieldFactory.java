package com.bulbview.recipeplanner.ui;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.common.base.Preconditions;
import com.google.inject.internal.Lists;
import com.google.inject.internal.Maps;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;

public class IngredientFieldFactory implements TableFieldFactory {

    enum IngredientProperty {
        name, category
    }

    private final Map<IngredientProperty, BeanItemContainer<?>> options;
    private final Logger                                        logger;

    public IngredientFieldFactory() {
        this.logger = LoggerFactory.getLogger(getClass());
        this.options = Maps.newHashMap();
    }

    @Override
    public Field createField(final Container container,
                             final Object itemId,
                             final Object propertyId,
                             final Component uiContext) {
        final IngredientProperty propertyIdAsEnum = IngredientProperty.valueOf((String) propertyId);
        Preconditions.checkArgument(propertyIdAsEnum != null, "Invalid property for ingredient: " + propertyId);
        final ComboBox comboBox = new ComboBox(propertyIdAsEnum.toString(), getOptions(propertyIdAsEnum));
        final Property itemProperty = container.getItem(itemId).getItemProperty(propertyId);
        logger.debug("property {}", itemProperty);
        comboBox.setValue(itemProperty);
        return comboBox;
    }

    public void setCategoryOptions(final Collection<Category> categories) {
        options.put(IngredientProperty.category, new BeanItemContainer<Category>(categories));
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredientOptions) {
        final Collection<String> ingredientNames = Lists.newArrayList();
        for ( final Ingredient ingredient : ingredientOptions ) {
            ingredientNames.add(ingredient.getName());
        }
        options.put(IngredientProperty.name, new BeanItemContainer<String>(ingredientNames));
    }

    private BeanItemContainer<?> getOptions(final IngredientProperty propertyId) {
        return options.get(propertyId);
    }
}
