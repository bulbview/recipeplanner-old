package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@org.springframework.stereotype.Component
public class RecipeEditorUiHelper {

    private final Logger           logger;
    private RecipePlannerPresenter presenter;
    private Recipe                 recipe;
    @Autowired
    private Form                   recipeForm;
    private TextField              recipeNameField;
    private Window                 recipeWindow;

    public RecipeEditorUiHelper() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Window getComponent() {
        return recipeWindow;
    }

    public Component getRecipeForm() {
        return recipeForm;
    }

    public void init() {
        initRecipeFormComponents();
        initRecipeWindowComponents();
    }

    public void set(final Recipe recipe) {
        logger.debug("adding recipe to form: " + recipe);
        recipeNameField.setValue(recipe.getName());
        this.recipe = recipe;
    }

    public void setPresenter(final RecipePlannerPresenter presenter) {
        this.presenter = presenter;

    }

    public void setRecipeWindow(final Window recipeWindow) {
        this.recipeWindow = recipeWindow;
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
