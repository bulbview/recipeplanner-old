package com.bulbview.recipeplanner.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.bulbview.recipeplanner.ui.helper.CategoryEditor;
import com.bulbview.recipeplanner.ui.helper.CategoryTabs;
import com.bulbview.recipeplanner.ui.helper.MainWindowUiManager;
import com.bulbview.recipeplanner.ui.helper.RecipeEditor;
import com.bulbview.recipeplanner.ui.helper.RecipeMasterList;
import com.bulbview.recipeplanner.ui.helper.WeeklySchedule;
import com.bulbview.recipeplanner.ui.presenter.CategoryTabsPresenter;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long      serialVersionUID = 1L;

    @Autowired
    private CategoryEditor         categoryEditor;
    @Autowired
    private CategoryTabs           categoryTabs;
    @Autowired
    private CategoryTabsPresenter  categoryTabsPresenter;
    private Logger                 logger;
    @Autowired
    private MainWindowUiManager    mainWindow;
    @Autowired
    private RecipeEditor           recipeEditor;
    @Autowired
    private RecipeMasterList       recipeMasterList;
    @Autowired
    private RecipePlannerPresenter recipePlannerPresenter;
    @Autowired
    private Window                 rootWindow;
    @Autowired
    private WeeklySchedule         scheduler;
    @Autowired
    private String                 theme;

    @Override
    public void init() {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.info("Initialising application...");
        setTheme(theme);
        setMainWindow(rootWindow);
        recipeMasterList.setTopLevelPanel(mainWindow.getRecipePanel());
        scheduler.setScheduler(mainWindow.getSchedulerAccordion());
        categoryTabs.setComponent(mainWindow.getCategoryAccordion());
        initPresenterViews();
        recipePlannerPresenter.init();
        categoryTabsPresenter.init();
        // TODO create schedulerPresenter
        scheduler.init();
    }

    private void initPresenterViews() {
        logger.info("Configuring presenters...");
        recipePlannerPresenter.setMainWindow(mainWindow);
        recipePlannerPresenter.setRecipeEditor(recipeEditor);
        recipePlannerPresenter.setRecipeMasterList(recipeMasterList);
        categoryTabsPresenter.setCategoryEditorWindow(categoryEditor);
        categoryTabsPresenter.setCategoryTabs(categoryTabs);
        categoryTabsPresenter.setMainWindow(mainWindow);
    }
}