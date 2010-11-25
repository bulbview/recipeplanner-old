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
    private int                     lastIngredientItemIndex = -1;
    private final Logger            logger;
    private final UiHelper          uiHelper;

    @Inject
    public IngredientsTable(final RecipeFieldFactory recipeFieldFactory, final UiHelper uiHelper) {
        this.recipeFieldFactory = recipeFieldFactory;
        this.uiHelper = uiHelper;
        recipeFieldFactory.set(getContainerDataSource());
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

    public void setCategories(final Collection<Category> categories) {
        recipeFieldFactory.setCategoryOptions(categories);
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
        addRow();
        final Item item = getItem(lastIngredientItemIndex);
        final ComboBox ingredientField = uiHelper.getComboBox(item, IngredientPropertyId);
        ingredientField.setValue(ingredient);
        final ComboBox categoryField = uiHelper.getComboBox(item, CategoryPropertyId);
        categoryField.setValue(ingredient.getCategory());
    }

    private int getNextTableItemIndex() {
        return ++lastIngredientItemIndex;
    }

}
