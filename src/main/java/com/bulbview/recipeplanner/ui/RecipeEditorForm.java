package com.bulbview.recipeplanner.ui;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.presenter.RecipeEditorPresenter.Category;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class RecipeEditorForm extends Form implements RecipeEditorFormView {

    private final Logger                 logger;
    private Recipe                       recipe;
    private final Provider<Button>       buttonProvider;
    private final RecipePlannerEventBus  recipePlannerEventBus;
    private final RecipeFormFieldFactory recipeFormFieldFactory;

    @Inject
    public RecipeEditorForm(final HorizontalLayout buttonContainer,
                            final RecipeFormFieldFactory recipeFormFieldFactory,
                            final RecipePlannerEventBus recipePlannerEventBus,
                            final Provider<Button> buttonProvider) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.buttonProvider = buttonProvider;
        this.recipePlannerEventBus = recipePlannerEventBus;
        this.recipeFormFieldFactory = recipeFormFieldFactory;
        setFormFieldFactory(recipeFormFieldFactory);
        setWriteThrough(false);
        createButtons(buttonContainer);
    }

    @Override
    public void activate(final Recipe recipe,
                         final Collection<Ingredient> ingredients,
                         final Collection<Category> categories) {
        setRecipe(recipe);
        recipeFormFieldFactory.set(ingredients);
        recipeFormFieldFactory.setCategories(categories);
        activateModalDialog();

    }

    public void activateModalDialog() {
        getWindow().setVisible(true);
    }

    public Button createButton(final String caption,
                               final ClickListener clickListener) {
        final Button button = buttonProvider.get();
        button.setCaption(caption);
        button.addListener(clickListener);
        return button;
    }

    public Button createSaveRecipeButton() {
        return createButton("Save", new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                commit();
                recipePlannerEventBus.saveRecipe(recipe);
            }
        });
    }

    private Button createAddIngredientButton() {
        return createButton("Add Ingredient", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                logger.debug("Adding new ingredient row to form...");
                recipeFormFieldFactory.createIngredientRowInEditor();
            }
        });

    }

    private void createButtons(final HorizontalLayout buttonContainer) {
        buttonContainer.setSpacing(true);
        buttonContainer.addComponent(createSaveRecipeButton());
        buttonContainer.addComponent(createAddIngredientButton());
        getFooter().addComponent(buttonContainer);
    }

    private void setRecipe(final Recipe recipe) {
        this.recipe = recipe;
        logger.info("Recipe backing bean: " + recipe);
        setItemDataSource(new BeanItem<Recipe>(recipe, Arrays.asList("name", "ingredients")));
    }
}
