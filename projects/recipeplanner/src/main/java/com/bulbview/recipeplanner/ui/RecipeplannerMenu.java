package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter;
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter;
import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@Component
public class RecipeplannerMenu {

    private class Commands {

        @SuppressWarnings("serial")
        public Command clearSchedule() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    weeklySchedulePresenter.clearSchedule();

                }
            };
        }

        @SuppressWarnings("serial")
        public Command showScheduleHistory() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    scheduleHistoryPresenter.showHistory();
                }
            };
        }

        @SuppressWarnings("serial")
        private Command addCategory() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    categoryTabsPresenter.addCategoryMenuSelected();
                }
            };
        }

        @SuppressWarnings("serial")
        private Command addRecipe() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    logger.debug("menu item selected '{}'", selectedItem.getText());
                    recipePlannerPresenter.createNewRecipe();
                }
            };
        }

        @SuppressWarnings("serial")
        private Command saveSchedule() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    weeklySchedulePresenter.saveSchedule();
                }
            };
        }

        @SuppressWarnings("serial")
        private Command viewShoppingList() {
            return new Command() {

                @Override
                public void menuSelected(final MenuItem selectedItem) {
                    shoppingListPresenter.displayShoppingList();
                }
            };
        }
    }

    @Autowired
    private CategoryTabsPresenter    categoryTabsPresenter;

    private final Commands           commands;
    private final Logger             logger;
    private MenuBar                  menuBar;

    @Autowired
    private RecipePresenter          recipePlannerPresenter;
    @Autowired
    private ScheduleHistoryPresenter scheduleHistoryPresenter;
    @Autowired
    private ShoppingListPresenter    shoppingListPresenter;
    @Autowired
    private WeeklySchedulePresenter  weeklySchedulePresenter;

    public RecipeplannerMenu() {
        this.logger = LoggerFactory.getLogger(getClass());
        this.commands = new Commands();
    }

    public void buildMenuBarItems() {
        createMenuItem("add recipe", commands.addRecipe());
        createMenuItem("add category", commands.addCategory());
        createScheduleMenu();
        createMenuItem("view shopping list", commands.viewShoppingList());
    }

    public void setMenuBar(final MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    private MenuItem createMenuItem(final String menuText,
                                    final Command command) {
        final MenuItem menuItem = menuBar.addItem(menuText, command);
        menuItem.setStyleName("menuitem");
        return menuItem;
    }

    private void createScheduleMenu() {
        final MenuItem scheduleMenu = createMenuItem("schedule", null);
        scheduleMenu.addItem("clear", commands.clearSchedule());
        scheduleMenu.addItem("history", commands.showScheduleHistory());
        scheduleMenu.addItem("save", commands.saveSchedule());
    }

}
