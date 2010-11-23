package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.Category;
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
    private final Logger                     logger;
    private Recipe                           recipe;

    private final Provider<IngredientsTable> ingredientsTableProvider;
    private IngredientsTable                 ingredientsTable;
    private Collection<Ingredient>           ingredientsOptions;

    @Inject
    public RecipeEditorFormFieldFactory(final Collection<String> ingredientNames,
                                        final Provider<IngredientsTable> ingredientsTableProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.ingredientsTableProvider = ingredientsTableProvider;
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
        Field field;
        final BeanItem<Recipe> beanItem = (BeanItem<Recipe>) item;
        this.recipe = beanItem.getBean();
        logger.debug("Editing Recipe: {}", recipe.getName());
        field = createIngredientsTable();
        return field;
    }

    public void createIngredientRowInEditor() {
        ingredientsTable.addRow();
    }

    public Field createIngredientsTable() {
        this.ingredientsTable = ingredientsTableProvider.get();
        ingredientsTable.setIngredientOptions(ingredientsOptions);
        ingredientsTable.setRecipeIngredients(recipe.getIngredients());
        return ingredientsTable;
    }

    public void setCategories(final Collection<Category> categories) {
        ingredientsTable.setCategories(categories);
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredients) {
        this.ingredientsOptions = ingredients;
    }

}
