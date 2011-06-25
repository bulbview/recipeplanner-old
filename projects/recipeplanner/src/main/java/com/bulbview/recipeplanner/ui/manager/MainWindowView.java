package com.bulbview.recipeplanner.ui.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.RecipeplannerMenu;
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

@Component
public class MainWindowView extends ViewManager<RecipePresenter> {

    @Autowired
    private Embedded          embeddedLogo;

    @Autowired
    private MainWindow        generatedComponent;
    @Autowired
    private RecipeplannerMenu recipeplannerMenu;

    @Autowired
    private Window            recipeWindow;
    @Autowired
    private Window            rootWindow;
    @Autowired
    private Window            scheduleHistoryWindow;

    public void closeRecipeEditor() {
        rootWindow.removeWindow(recipeWindow);
    }

    public Accordion getCategoryAccordion() {
        return generatedComponent.getCategoriesAccordion();
    }

    public Window getComponent() {
        return rootWindow;
    }

    public Panel getRecipePanel() {
        return generatedComponent.getRecipePanel();
    }

    public Accordion getSchedulerAccordion() {
        return generatedComponent.getSchedulerAccordion();
    }

    @Override
    public void init() {
        rootWindow.addComponent(generatedComponent);
        generatedComponent.setApplicationLogo(embeddedLogo);
        recipeplannerMenu.setMenuBar(generatedComponent.getRecipeplannerMenuBar());
        recipeplannerMenu.buildMenuBarItems();
    }

    public void showRecipeWindow() {
        rootWindow.addWindow(recipeWindow);
    }

    public void showScheduleHistoryWindow() {
        rootWindow.addWindow(scheduleHistoryWindow);
    }

}
