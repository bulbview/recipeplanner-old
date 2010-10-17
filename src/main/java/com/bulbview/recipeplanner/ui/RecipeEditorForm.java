package com.bulbview.recipeplanner.ui;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
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

    public Button createButton(final String caption,
                               final ClickListener clickListener) {
        final Button closeButton = buttonProvider.get();
        closeButton.setCaption(caption);
        closeButton.addListener(clickListener);
        return closeButton;
    }

    public void createButtons(final HorizontalLayout buttonContainer) {
        buttonContainer.setSpacing(true);
        buttonContainer.addComponent(createCloseRecipeEditorButton());
        buttonContainer.addComponent(createSaveRecipeButton());
        buttonContainer.addComponent(createAddIngredientButton());
        getFooter().addComponent(buttonContainer);
    }

    public Button createCloseRecipeEditorButton() {
        return createButton("Close", new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                discard();
                recipePlannerEventBus.closeRecipeEditor();
            }
        });

    }

    public Button createSaveRecipeButton() {
        return createButton("Save", new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                commit();
                recipePlannerEventBus.saveRecipe(recipe);
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bulbview.ui.CreateRecipeFormView#setRecipe(com.bulbview.recipeplanner
     * .datamodel.Recipe)
     */
    public void setRecipe(final Recipe recipe) {
        this.recipe = recipe;
        logger.info("Recipe backing bean: " + recipe);
        setItemDataSource(new BeanItem<Recipe>(recipe, Arrays.asList("name", "ingredients")));
    }

    private Button createAddIngredientButton() {
        return createButton("Add Ingredient", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                logger.debug("Adding new ingredient...");
                recipeFormFieldFactory.createIngredientForRecipe();
                recipeFormFieldFactory.updateIngredientsTable();

            }
        });

    }
}
