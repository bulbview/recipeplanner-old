package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;

public class RecipeFormFieldFactory extends DefaultFieldFactory {

    private final Logger logger;

    public RecipeFormFieldFactory() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Field createField(final Item item,
                             final Object propertyId,
                             final Component uiContext) {
        final String propertyIdString = (String) propertyId;
        if( propertyIdString.equals("ingredients") ) {
            final BeanItem<Recipe> beanItem = (BeanItem<Recipe>) item;
            final Recipe recipe = beanItem.getBean();
            logger.debug("Recipe found: {}", recipe.getName());
            final Table table = createIngredientsTable(propertyIdString, recipe);
            return table;
        }
        return super.createField(item, propertyId, uiContext);
    }

    public Table createIngredientsTable(final String propertyIdString,
                                        final Recipe r) {
        final Table table = new Table();
        table.addContainerProperty(propertyIdString, String.class, "");
        table.setEditable(true);
        r.addIngredient(new Ingredient());
        table.setContainerDataSource(new BeanItemContainer<Ingredient>(r.getIngredients()));
        return table;
    }
}
