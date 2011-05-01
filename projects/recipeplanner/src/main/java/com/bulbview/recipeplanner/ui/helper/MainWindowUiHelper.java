package com.bulbview.recipeplanner.ui.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

@Component
public class MainWindowUiHelper extends UiHelper {

    @Autowired
    private Embedded   embeddedLogo;
    @Autowired
    private MainWindow generatedComponent;

    private Window     recipeWindow;
    private Window     rootWindow;

    public void closeRecipeEditor() {
        rootWindow.removeWindow(recipeWindow);
    }

    public Window getComponent() {
        return rootWindow;
    }

    public Panel getRecipePanel() {
        return generatedComponent.getRecipePanel();
    }

    public void init() {
        rootWindow.addComponent(generatedComponent);
        generatedComponent.setApplicationLogo(embeddedLogo);
    }

    public void setMainWindow(final Window rootWindow) {
        this.rootWindow = rootWindow;

    }

    // TODO move presenter interaction to this class
    @Override
    public void setPresenter(final RecipePlannerPresenter presenter) {
        generatedComponent.setPresenter(presenter);
    }

    public void setRecipeWindow(final Window recipeWindow) {
        this.recipeWindow = recipeWindow;
    }

    public void showRecipeWindow() {
        rootWindow.addWindow(recipeWindow);
    }

}
