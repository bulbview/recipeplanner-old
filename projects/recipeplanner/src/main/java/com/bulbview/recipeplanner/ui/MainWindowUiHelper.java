package com.bulbview.recipeplanner.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Embedded;
import com.vaadin.ui.Window;

@Component
public class MainWindowUiHelper {

    @Autowired
    private Embedded   embeddedLogo;
    @Autowired
    private MainWindow generatedComponent;

    private Window     recipeWindow;
    private Window     rootWindow;

    public MainWindowUiHelper() {

    }

    public void closeRecipeEditor() {
        rootWindow.removeWindow(recipeWindow);
    }

    public Window getComponent() {
        return rootWindow;
    }

    public void init() {
        rootWindow.addComponent(generatedComponent);
        generatedComponent.setApplicationLogo(embeddedLogo);
    }

    public void setMainWindow(final Window rootWindow) {
        this.rootWindow = rootWindow;

    }

    public void setPresenter(final RecipePlannerPresenter presenter) {
        generatedComponent.setPresenter(presenter);
    }

    public void setRecipeFormUiHelper(final RecipeEditorUiHelper recipeFormUiHelper) {

    }

    public void setRecipeWindow(final Window recipeWindow) {
        this.recipeWindow = recipeWindow;
    }

    public void showRecipeWindow() {
        rootWindow.addWindow(recipeWindow);
    }

}
