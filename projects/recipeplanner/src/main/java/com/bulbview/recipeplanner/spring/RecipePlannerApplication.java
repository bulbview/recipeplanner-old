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
import com.bulbview.recipeplanner.ui.helper.SchedulerUiHelper;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long      serialVersionUID = 1L;

    @Autowired
    private CategoryTabs           categoryTabs;
    @Autowired
    private CategoryEditor         categoryWindow;
    private Logger                 logger;
    @Autowired
    private MainWindowUiManager    mainWindow;
    @Autowired
    private RecipeEditor   recipeEditor;
    @Autowired
    private RecipeMasterList       recipeMasterList;
    @Autowired
    private RecipePlannerPresenter recipePlannerPresenter;
    @Autowired
    private Window                 rootWindow;
    @Autowired
    private SchedulerUiHelper      scheduler;
    @Autowired
    private String                 theme;

    @Override
    public void init() {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.info("Initialising application...");
        setTheme(theme);
        setMainWindow(rootWindow);
        recipeMasterList.setRecipePanel(mainWindow.getRecipePanel());
        scheduler.setScheduler(mainWindow.getSchedulerAccordion());
        categoryTabs.setComponent(mainWindow.getCategoryAccordion());
        configurePresenter();
    }

    private void configurePresenter() {
        logger.info("Configuring presenter...");
        recipePlannerPresenter.setMainWindow(mainWindow);
        recipePlannerPresenter.setRecipeEditor(recipeEditor);
        recipePlannerPresenter.setRecipeMasterList(recipeMasterList);
        recipePlannerPresenter.setCategoryEditorWindow(categoryWindow);
        recipePlannerPresenter.setCategoryTabs(categoryTabs);
    }

}