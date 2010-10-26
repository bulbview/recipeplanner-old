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

    private Collection<String>           categories;

    @Inject
    public RecipeEditorPresenter(final Collection<Ingredient> transientIngredients,
                                 final Provider<Ingredient> ingredientProvider,
                                 final IngredientDao ingredientDao,
                                 final RecipeDao recipeDao,
                                 final RecipeEditorFormView recipeEditorFormView,
                                 final Provider<Recipe> recipeProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.transientIngredients = transientIngredients;
        this.ingredientProvider = ingredientProvider;
        this.ingredientDao = ingredientDao;
        this.recipeDao = recipeDao;
        this.createRecipeFormView = recipeEditorFormView;
        this.recipeProvider = recipeProvider;
    }

    public void addIngredient(final Category category,
                              final String name) {
        logger.debug("Transient ingredient {}, {}...", category, name);
        final Ingredient ingredient = ingredientProvider.get();
        ingredient.setCategory(category);
        ingredient.setName(name);
        transientIngredients.add(ingredient);
    }

    public void onCreateNewRecipe() {
        this.cachedIngredients = ingredientDao.getAll();
        createRecipeFormView.activate(recipeProvider.get(), cachedIngredients, Arrays.asList(Category.values()));

    }

    public void onIngredientSelected(final ViewField categoryField,
                                     final String ingredientName) {
        final Category category = getCategoryName(ingredientName);
        categoryField.setValue(category);
    }

    public void onNewIngredientSelected(final String newIngredientName) {
        final Category category = null;
        addIngredient(category, newIngredientName);
        logger.debug("...added transient Ingredient: {}, {}", newIngredientName, category);
    }

    public void onSaveRecipe(final Recipe recipe) {
        saveIngredientTransients();
        logger.info("Saving recipe {}...", recipe);
        recipeDao.saveRecipe(recipe);
    }

    private Category getCategoryName(final String ingredientName) {
        for ( final Ingredient ingredient : cachedIngredients ) {
            if( ingredient.getName().equals(ingredientName) ) {
                return ingredient.getCategory();
            }
        }
        throw new RuntimeException("No Category Found");
    }

    private void saveIngredientTransients() {
        ingredientDao.save(transientIngredients);
    }

}
