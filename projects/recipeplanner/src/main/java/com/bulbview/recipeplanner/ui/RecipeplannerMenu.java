package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@Component
public class RecipeplannerMenu {

    @Autowired
    private CategoryTabsPresenter  categoryTabsPresenter;
    private final Logger           logger;

    private MenuBar                menuBar;
    @Autowired
    private RecipePresenter recipePlannerPresenter;

    @Autowired
    private ShoppingListPresenter  shoppingListPresenter;

    public RecipeplannerMenu() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void buildMenuBarItems() {
        createMenuItem("add recipe", addRecipeCommand());
        createMenuItem("add category", addCategoryCommand());
        createScheduleMenu();
        createMenuItem("view shopping list", viewShoppingListCommand());
    }

    public void setMenuBar(final MenuBar menuBar) {
        this.menuBar = menuBar;
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

    @SuppressWarnings("serial")
    private Command addRecipeCommand() {
        return new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                logger.debug("menu item selected '{}'", selectedItem.getText());
                recipePlannerPresenter.createNewRecipe();
            }
        };
    }

    private MenuItem createMenuItem(final String menuText,
                                    final Command command) {
        final MenuItem menuItem = menuBar.addItem(menuText, command);
        menuItem.setStyleName("menuitem");
        return menuItem;
    }

    private void createScheduleMenu() {
        final MenuItem scheduleMenu = createMenuItem("schedule", null);
        scheduleMenu.addItem("clear schedule", null);
        scheduleMenu.addItem("save schedule", null);
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
