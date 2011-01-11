package com.bulbview.recipeplanner.ui;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.Category;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public final class RecipeEditorForm extends Form implements RecipeEditorFormView {

    private final Logger                logger;
    private Recipe                      recipe;
    private final Provider<Button>      buttonProvider;
    private final RecipePlannerEventBus recipePlannerEventBus;
    private final IngredientsTable      ingredientsTable;
    private Collection<Category>        categoryOptions;
    private Collection<Ingredient>      ingredientOptions;

    @Inject
    public RecipeEditorForm(final HorizontalLayout buttonContainer,
                            final IngredientsTable ingredientsTable,
                            final RecipeEditorFormFieldFactory recipeFormFieldFactory,
                            final RecipePlannerEventBus recipePlannerEventBus,
                            final Provider<Button> buttonProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.buttonProvider = buttonProvider;
        this.recipePlannerEventBus = recipePlannerEventBus;
        this.ingredientsTable = ingredientsTable;
        recipeFormFieldFactory.setIngredientsTable(ingredientsTable);
        setFormFieldFactory(recipeFormFieldFactory);
        setWriteThrough(false);
        createButtons(buttonContainer);
    }

    public Button createSaveRecipeButton() {
        return createButton("Save", new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                commit();
                recipePlannerEventBus.saveRecipe(recipe);
            }
        });
    }

    @Override
    public void displayDialog() {
        getWindow().setVisible(true);
    }

    @Override
    public void setCategoryOptions(final Collection<Category> categories) {
        this.categoryOptions = categories;
    }

    @Override
    public void setIngredientOptions(final Collection<Ingredient> ingredientOptions) {
        this.ingredientOptions = ingredientOptions;
    }

    @Override
    public void setRecipe(final Recipe recipe) {
        this.recipe = recipe;
        logger.info("Recipe backing bean: " + recipe);
        initialiseIngredientsTable(recipe, ingredientOptions, categoryOptions);
        setItemDataSource(new BeanItem<Recipe>(recipe, Arrays.asList("name", "ingredients")));
    }

    private Button createAddIngredientButton() {
        return createButton("Add Ingredient", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                logger.debug("Adding new ingredient to form...");
                ingredientsTable.addNewIngredient();
            }
        });

    }

    private Button createButton(final String caption,
                                final ClickListener clickListener) {
        final Button button = buttonProvider.get();
        button.setCaption(caption);
        button.addListener(clickListener);
        return button;
    }

    private void createButtons(final HorizontalLayout buttonContainer) {
        buttonContainer.setSpacing(true);
        buttonContainer.addComponent(createSaveRecipeButton());
        buttonContainer.addComponent(createAddIngredientButton());
        getFooter().addComponent(buttonContainer);
    }

    private void initialiseIngredientsTable(final Recipe recipe,
                                            final Collection<Ingredient> ingredientsOptions,
                                            final Collection<Category> categoryOptions) {
        ingredientsTable.setIngredientOptions(ingredientsOptions);
        ingredientsTable.setCategories(categoryOptions);
        ingredientsTable.setRecipe(recipe);
    }
}
