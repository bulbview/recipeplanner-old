package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter.Category;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

@SuppressWarnings("serial")
public class RecipeFormFieldFactory extends DefaultFieldFactory {

    private final Logger                     logger;
    private Recipe                           recipe;

    private final Provider<IngredientsTable> ingredientsTableProvider;
    private IngredientsTable                 ingredientsTable;

    @Inject
    public RecipeFormFieldFactory(final Collection<String> ingredientNames,
                                  final Provider<IngredientsTable> ingredientsTableProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.ingredientsTableProvider = ingredientsTableProvider;
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
            this.ingredientsTable = ingredientsTableProvider.get();
            return ingredientsTable;
        }
        return super.createField(item, propertyId, uiContext);
    }

    public void createIngredientRowInEditor() {
        ingredientsTable.addRow();

    }

    public void set(final Collection<Ingredient> ingredients) {
        ingredientsTable.set(ingredients);
    }

    public void setCategories(final Collection<Category> categories) {
        ingredientsTable.setCategories(categories);
    }

}
