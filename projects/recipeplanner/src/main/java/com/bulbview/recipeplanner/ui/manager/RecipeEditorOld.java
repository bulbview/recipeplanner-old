package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@org.springframework.stereotype.Component
public class RecipeEditorOld extends ViewManager<RecipePresenter> {

    private final Logger logger;
    private Recipe       recipe;
    @Autowired
    private Form         recipeForm;
    private TextField    recipeNameField;
    @Autowired
    private Window       recipeWindow;

    public RecipeEditorOld() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Window getComponent() {
        return recipeWindow;
    }

    public Component getRecipeForm() {
        return recipeForm;
    }

    @Override
    public void init() {
        initRecipeFormComponents();
        initRecipeWindowComponents();
    }

    public void set(final Recipe recipe) {
        logger.debug("adding recipe to form: " + recipe);
        recipeNameField.setValue(recipe.getName());
        this.recipe = recipe;
    }

    @Override
    public void setPresenter(final RecipePresenter presenter) {
        this.presenter = presenter;
    }

    public void showErrorMessage(final String message) {
        // TODO Auto-generated method stub

    }

    private ClickListener createSaveRecipeListener() {
        return new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(final ClickEvent event) {
                saveRecipe();
            }

        };
    }

    private void initRecipeFormComponents() {
        recipeForm.setSizeFull();
        recipeNameField = new TextField("Name");
        recipeNameField.setNullRepresentation("");
        recipeNameField.setInputPrompt("<Enter recipe name>");
        recipeForm.addField("name", recipeNameField);
        final Button saveRecipeButton = new Button("Save");
        saveRecipeButton.addListener(createSaveRecipeListener());
        recipeForm.getFooter().addComponent(saveRecipeButton);
    }

    private void initRecipeWindowComponents() {
        recipeWindow.setWidth("50%");
        recipeWindow.setHeight("50%");
        recipeWindow.addComponent(recipeForm);
    }

    private void saveRecipe() {
        final Field recipeNameField = recipeForm.getField("name");
        logger.debug("Saving recipe: {}", recipeNameField.getValue());
        recipe.setName((String) recipeNameField.getValue());
        presenter.save(recipe);

    }

}
