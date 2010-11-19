package com.bulbview.recipeplanner.ui;

import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.CategoryPropertyId;
import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.IngredientPropertyId;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class IngredientsTable extends Table {

    public final RecipeFieldFactory recipeFieldFactory;

    private int                     ingredientItemIndex;

    private final Logger            logger;

    @Inject
    public IngredientsTable(final RecipeFieldFactory recipeFieldFactory) {
        this.recipeFieldFactory = recipeFieldFactory;
        this.logger = LoggerFactory.getLogger(getClass());
        setEditable(true);
        setImmediate(true);
        setNullSelectionAllowed(true);
        addContainerProperty(CategoryPropertyId, ComboBox.class, null);
        addContainerProperty(IngredientPropertyId, ComboBox.class, null);
    }

    public void addRow() {
        logger.debug("Adding ingredient row to recipeEditor...");
        addItem(new ComboBox[] { recipeFieldFactory.createCategoryComboBox(),
                                recipeFieldFactory.createIngredientComboBox() },
                getNextTableItemIndex());
    }

    public ComboBox getComboBox(final Item item,
                                final String propertyId) {
        return (ComboBox) item.getItemProperty(propertyId).getValue();
    }

    public void setCategories(final Collection<Category> categories) {
        recipeFieldFactory.setCategoryNames(categories);
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredients) {
        recipeFieldFactory.setIngredientOptions(ingredients);
    }

    public void setRecipeIngredients(final Collection<Ingredient> ingredients) {
        logger.debug("{} ingredient(s) found", ingredients.size());
        for ( final Ingredient ingredient : ingredients ) {
            addRow(ingredient);
        }
    }

    private void addRow(final Ingredient ingredient) {
        logger.debug("Adding ingredient row to recipeEditor...");
        addItem(new ComboBox[] { recipeFieldFactory.createCategoriesCombobox(ingredient.getCategory()),
                        recipeFieldFactory.createIngredientsCombobox(ingredient) }, getNextTableItemIndex());
    }

    private ComboBox getCategoryComboBoxFor(final ComboBox ingredientField) {
        for ( final Object id : getItemIds() ) {
            final Item item = getItem(id);
            final ComboBox ingredientComboBox = getComboBox(item, IngredientPropertyId);
            if( ingredientComboBox.equals(ingredientField) ) {
                final ComboBox comboBox = getComboBox(item, CategoryPropertyId);
                return comboBox;
            }
        }
        return null;
    }

    private int getNextTableItemIndex() {
        return ingredientItemIndex++;
    }

}
