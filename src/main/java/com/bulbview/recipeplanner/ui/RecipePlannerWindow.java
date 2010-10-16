package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RecipePlannerWindow extends Window implements WindowView {

    private RecipeEditorModalWindow createRecipeModalWindow;

    public void displayRecipeEditorModalWindow() {
        addWindow(createRecipeModalWindow);
    }

    public void hideRecipeEditorModalWindow() {
        removeWindow(createRecipeModalWindow);
    }

    @Inject
    public void setCreateRecipeModalWindow(final RecipeEditorModalWindow createRecipeModalWindow) {
        this.createRecipeModalWindow = createRecipeModalWindow;
    }

}
