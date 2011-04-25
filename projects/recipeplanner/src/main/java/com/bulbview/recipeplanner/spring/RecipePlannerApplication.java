package com.bulbview.recipeplanner.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bulbview.recipeplanner.ui.MainWindowUiHelper;
import com.bulbview.recipeplanner.ui.RecipeEditorUiHelper;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long      serialVersionUID = 1L;

    private Logger                 logger;
    @Autowired
    private MainWindowUiHelper     mainWindowUiHelper;
    @Autowired
    private RecipeEditorUiHelper   recipeEditorUiHelper;
    @Autowired
    private RecipePlannerPresenter recipePlannerPresenter;
    @Autowired
    private Window                 recipeWindow;
    @Autowired
    private Window                 rootWindow;
    @Autowired
    private String                 theme;

    @Override
    public void init() {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.info("Initialising application");
        setTheme(theme);
        setMainWindow(rootWindow);
        mainWindowUiHelper.setMainWindow(rootWindow);
        mainWindowUiHelper.setRecipeWindow(recipeWindow);
        mainWindowUiHelper.init();
        recipeEditorUiHelper.setRecipeWindow(recipeWindow);
        recipeEditorUiHelper.init();
        recipePlannerPresenter.setMainWindow(mainWindowUiHelper);
        recipePlannerPresenter.setRecipeEditorUiHelper(recipeEditorUiHelper);
    }

}