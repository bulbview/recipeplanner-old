package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RecipeEditorModalWindow extends Window {

    @Inject
    public RecipeEditorModalWindow(final RecipeEditorForm editRecipeForm) {
        setCaption("Create a New Recipe");
        setModal(true);
        getContent().addComponent(editRecipeForm);
    }

}
