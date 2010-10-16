package com.bulbview.recipeplanner.ui;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.google.inject.Inject;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class RecipeEditorForm extends Form implements RecipeEditorFormView {

    private final Logger logger;
    private Recipe       recipe;

    @Inject
    public RecipeEditorForm(final HorizontalLayout buttonContainer,
                            final RecipeFormFieldFactory recipeFormFieldFactory,
                            final RecipePlannerEventBus recipePlannerEventBus,
                            final Button closeButton,
                            final Button saveButton) {
        this.logger = LoggerFactory.getLogger(getClass());
        setFormFieldFactory(recipeFormFieldFactory);
        setWriteThrough(false);
        buttonContainer.setSpacing(true);
        closeButton.setCaption("Close");
        closeButton.addListener(new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                discard();
                recipePlannerEventBus.closeRecipeEditor();
            }
        });
        buttonContainer.addComponent(closeButton);
        saveButton.setCaption("Save");
        saveButton.addListener(new ClickListener() {

            public void buttonClick(final ClickEvent event) {
                commit();
                recipePlannerEventBus.saveRecipe(recipe);
            }
        });
        buttonContainer.addComponent(saveButton);
        getFooter().addComponent(buttonContainer);
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
}
