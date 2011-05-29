package com.bulbview.recipeplanner.ui.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

@Component
public class MainWindowUiManager extends ViewManager<RecipePlannerPresenter> {

    @Autowired
    private CategoryTabsPresenter categoryTabsPresenter;

    @Autowired
    private Embedded              embeddedLogo;
    @Autowired
    private MainWindow            generatedComponent;
    private final Logger          logger;
    private MenuBar               recipeplannerMenuBar;
    @Autowired
    private Window                recipeWindow;
    @Autowired
    private Window                rootWindow;
    @Autowired
    private ShoppingListPresenter shoppingListPresenter;

    public MainWindowUiManager() {
        this.logger = LoggerFactory.getLogger(getClass());
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

    public void showRecipeWindow() {
        rootWindow.addWindow(recipeWindow);
    }

    @SuppressWarnings("serial")
    private Command addCategoryCommand() {
        return new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                categoryTabsPresenter.addCategoryMenuSelected();
            }
        };
    }

    private Command addRecipeCommand() {
        return new Command() {

            private static final long serialVersionUID = 3565807322395939470L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                logger.debug("menu item selected '{}'", selectedItem.getText());
                presenter.createNewRecipe();
            }
        };
    }

    private void buildMenuBarItems() {
        createMenuItem("add recipe", addRecipeCommand());
        createMenuItem("add category", addCategoryCommand());
        createMenuItem("clear schedule", null);
        createMenuItem("view shopping list", viewShoppingListCommand());
    }

    private MenuItem createMenuItem(final String menuText,
                                    final Command command) {
        final MenuItem menuItem = recipeplannerMenuBar.addItem(menuText, command);
        menuItem.setStyleName("menuitem");
        return menuItem;
    }

    @SuppressWarnings("serial")
    private Command viewShoppingListCommand() {
        return new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                shoppingListPresenter.displayShoppingList();
            }
        };
    }

}
