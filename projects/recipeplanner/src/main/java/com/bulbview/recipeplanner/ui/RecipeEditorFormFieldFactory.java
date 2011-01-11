package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

@SuppressWarnings("serial")
public class RecipeEditorFormFieldFactory extends DefaultFieldFactory {

    private static final String INGREDIENTS = "ingredients";
    private final Logger        logger;

    private IngredientsTable    ingredientsTable;

    @Inject
    public RecipeEditorFormFieldFactory(final Provider<IngredientsTable> ingredientsTableProvider) {
        this.logger = LoggerFactory.getLogger(getClass());

    }

    @Override
    public Field createField(final Item item,
                             final Object propertyId,
                             final Component uiContext) {
        final String propertyIdString = (String) propertyId;
        Field field;
        if( propertyIdString.equals(INGREDIENTS) ) {
            field = createIngredientsTableField(item);
        } else {
            field = super.createField(item, propertyId, uiContext);
        }
        return field;
    }

    public Field createIngredientsTableField(final Item item) {
        final BeanItem<Recipe> beanItem = (BeanItem<Recipe>) item;
        final Recipe recipe = beanItem.getBean();
        logger.debug("Editing Recipe: {}", recipe.getName());
        ingredientsTable.setRecipe(recipe);
        return ingredientsTable;
    }

    public void setIngredientsTable(final IngredientsTable ingredientsTable) {
        this.ingredientsTable = ingredientsTable;
    }

}
