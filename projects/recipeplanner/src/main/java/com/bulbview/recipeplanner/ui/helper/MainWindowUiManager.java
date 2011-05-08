package com.bulbview.recipeplanner.ui.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

@Component
public class MainWindowUiManager extends UiManager {

    @Autowired
    private Window       categoryWindow;
    @Autowired
    private Embedded     embeddedLogo;
    @Autowired
    private MainWindow   generatedComponent;
    private final Logger logger;
    private MenuBar      recipeplannerMenuBar;
    @Autowired
    private Window       recipeWindow;
    @Autowired
    private Window       rootWindow;

    public MainWindowUiManager() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void closeCategoryWindow() {
        rootWindow.removeWindow(categoryWindow);

    }

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
        this.recipeplannerMenuBar = generatedComponent.getRecipeplannerMenuBar();
        buildMenuBarItems();
    }

    public void showCategoryWindow() {
        rootWindow.addWindow(categoryWindow);
    }

    public void showRecipeWindow() {
        rootWindow.addWindow(recipeWindow);
    }

    private void buildMenuBarItems() {
        createMenuItem("add recipe", new Command() {

            private static final long serialVersionUID = 3565807322395939470L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                logger.debug("menu item selected '{}'", selectedItem.getText());
                presenter.createNewRecipe();
            }
        });
        createMenuItem("add category", new Command() {

            private static final long serialVersionUID = 5984417970543192534L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                presenter.addCategoryMenuSelected();
            }
        });
        createMenuItem("clear schedule", null);
        createMenuItem("view shopping list", null);
    }

    private MenuItem createMenuItem(final String menuText,
                                    final Command addRecipeMenuItemCommand) {
        final MenuItem menuItem = recipeplannerMenuBar.addItem(menuText, addRecipeMenuItemCommand);
        menuItem.setStyleName("menuitem");
        return menuItem;
    }

}
