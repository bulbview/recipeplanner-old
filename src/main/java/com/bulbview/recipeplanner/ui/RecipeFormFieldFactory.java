package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;

public class RecipeFormFieldFactory extends DefaultFieldFactory {

    private final Logger               logger;
    private final Provider<Ingredient> ingredientProvider;
    private Recipe                     recipe;
    private final Table                ingredientsTable;

    @Inject
    public RecipeFormFieldFactory(final Provider<Ingredient> ingredientProvider, final Table table) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.ingredientProvider = ingredientProvider;
        this.ingredientsTable = table;
        ingredientsTable.addContainerProperty("ingredients", String.class, "");
        ingredientsTable.setEditable(true);
        ingredientsTable.setImmediate(true);
    }

    @Override
    public Field createField(final Item item,
                             final Object propertyId,
                             final Component uiContext) {
        final String propertyIdString = (String) propertyId;
        if( propertyIdString.equals("ingredients") ) {
            final BeanItem<Recipe> beanItem = (BeanItem<Recipe>) item;
            this.recipe = beanItem.getBean();
            logger.debug("Editing Recipe: {}", recipe.getName());
            createIngredientForRecipe();
            return ingredientsTable;
        }
        return super.createField(item, propertyId, uiContext);
    }

    public void createIngredientForRecipe() {
        recipe.addIngredient(ingredientProvider.get());
        updateIngredientsTable();
    }

    public void updateIngredientsTable() {
        ingredientsTable.setContainerDataSource(new BeanItemContainer<Ingredient>(recipe.getIngredients()));
    }
}
