package com.bulbview.recipeplanner.ui.presenter;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.bulbview.recipeplanner.dao.IngredientDao;
import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.ViewField;
import com.bulbview.recipeplanner.ui.WindowView;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

@Presenter(view = RecipeEditorFormView.class)
public class RecipeEditorPresenter extends BasePresenter<RecipeEditorFormView, RecipePlannerEventBus> {

    public enum Category {
        Bakery("Bakery"), Fruit_Vegetables("Fruit & Vegetables"), Tins("Tins, Jars & Cooking"), Dairy_Eggs(
                        "Dairy & Eggs"), Meat_Fish_Poultry("Meat, Fish & Poultry");

        public String string;

        private Category(final String s) {
            this.string = s;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private final Collection<Ingredient> transientIngredients;
    private final Provider<Ingredient>   ingredientProvider;
    private final IngredientDao          ingredientDao;
    private final RecipeEditorFormView   createRecipeFormView;
    private final Provider<Recipe>       recipeProvider;
    private final Logger                 logger;
    private final RecipeDao              recipeDao;
    private Collection<Ingredient>       cachedIngredients;
    private final WindowView             windowView;
    private static final String          selectCategoryNotification = "Select category for new ingredient: %s";

    @Inject
    public RecipeEditorPresenter(final Collection<Ingredient> transientIngredients,
                                 final Provider<Ingredient> ingredientProvider,
                                 final IngredientDao ingredientDao,
                                 final RecipeDao recipeDao,
                                 final RecipeEditorFormView recipeEditorFormView,
                                 final WindowView windowView,
                                 final Provider<Recipe> recipeProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.transientIngredients = transientIngredients;
        this.ingredientProvider = ingredientProvider;
        this.ingredientDao = ingredientDao;
        this.recipeDao = recipeDao;
        this.createRecipeFormView = recipeEditorFormView;
        this.windowView = windowView;
        this.recipeProvider = recipeProvider;
    }

    public Ingredient createIngredient(final ViewField ingredientField) {
        final String ingredientName = (String) ingredientField.getValue();
        final Ingredient ingredient = ingredientProvider.get();
        ingredient.setName(ingredientName);
        return ingredient;
    }

    public boolean isExistingIngredient(final Object value) {
        return value instanceof Ingredient;
    }

    public void onCreateNewRecipe() {
        this.cachedIngredients = ingredientDao.getAll();
        createRecipeFormView.activate(recipeProvider.get(), cachedIngredients, Arrays.asList(Category.values()));

    }

    public void onNewOrExistingIngredientSelected(final ViewField categoryField,
                                                  final ViewField ingredientField) {
        final Object value = ingredientField.getValue();
        if( isExistingIngredient(value) ) {
            categoryField.setValue(getCategory(value));
        } else {
            categoryField.setEnabled(true);
            categoryField.focus();
            final Ingredient ingredient = createIngredient(ingredientField);
            windowView.showNotification(String.format(selectCategoryNotification, ingredient));
            // logger.debug("Adding new ingredient to combobox: {}",
            // ingredient);
            // ingredientField.setValue(ingredient);
        }
    }

    public void onSaveRecipe(final Recipe recipe) {
        saveIngredientTransients();
        logger.info("Saving recipe {}...", recipe);
        recipeDao.saveRecipe(recipe);
    }

    private Category getCategory(final Object value) {
        return ( (Ingredient) value ).getCategory();
    }

    private Category getCategoryName(final Ingredient ingredient) {
        for ( final Ingredient cachedIngredient : cachedIngredients ) {
            if( cachedIngredient.equals(ingredient) ) {
                return ingredient.getCategory();
            }
        }
        throw new RuntimeException("No Category Found for ingredient: " + ingredient);
    }

    private void saveIngredientTransients() {
        ingredientDao.save(transientIngredients);
    }

}
