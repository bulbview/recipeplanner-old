package com.bulbview.recipeplanner.ui;

import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.CategoryPropertyId;
import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.IngredientNamePropertyId;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class IngredientsTable extends Table {

    public final RecipeFieldFactory       recipeFieldFactory;
    private final int                     lastIngredientItemIndex = -1;
    private final Logger                  logger;
    private final UiHelper                uiHelper;
    private final IngredientFieldFactory  ingredientFieldFactory;
    private BeanItemContainer<Ingredient> ingredientsContainer;

    @Inject
    public IngredientsTable(final RecipeFieldFactory recipeFieldFactory,
                            final IngredientFieldFactory ingredientFieldFactory,
                            final UiHelper uiHelper) {
        this.recipeFieldFactory = recipeFieldFactory;
        this.uiHelper = uiHelper;
        this.ingredientFieldFactory = ingredientFieldFactory;
        recipeFieldFactory.set(getContainerDataSource());
        this.logger = LoggerFactory.getLogger(getClass());
        setEditable(true);
        setImmediate(true);
        setNullSelectionAllowed(true);
        setWriteThrough(false);
        createProperties();
        setTableFieldFactory(ingredientFieldFactory);
    }

    public void addNewIngredient() {
        final Object lastItemId = lastItemId();
        final Ingredient bean = new Ingredient();
        bean.setCategory(Category.Meat_Fish_Poultry);
        bean.setName("Chicken");
        // datasource for updating display only?
        // final BeanItem<Ingredient> addBean =
        ingredientsContainer.addBean(bean);
        final HashSet<Ingredient> value2 = (HashSet<Ingredient>) getValue();
        value2.add(bean);
        logger.debug("...ingredient item added {}", lastItemId);
    }

    @Override
    public void commit() throws SourceException, InvalidValueException {
        super.commit();
    }

    public void setCategories(final Collection<Category> categories) {
        ingredientFieldFactory.setCategoryOptions(categories);
    }

    public void setIngredientOptions(final Collection<Ingredient> ingredients) {
        ingredientFieldFactory.setIngredientOptions(ingredients);
    }

    public void setRecipe(final Recipe recipe) {
        ingredientsContainer = new BeanItemContainer<Ingredient>(recipe.getIngredients());
        setContainerDataSource(ingredientsContainer);
    }

    private void createProperties() {
        addContainerProperty(CategoryPropertyId, ComboBox.class, null);
        addContainerProperty(IngredientNamePropertyId, ComboBox.class, null);
    }

}
