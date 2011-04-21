package com.bulbview.recipeplanner.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long      serialVersionUID = 1L;

    private static final String    NO_CAPTION       = "";

    @Autowired
    @Qualifier("rootWindow")
    private Window                 rootWindow;
    @Autowired
    @Qualifier("recipeWindow")
    private Window                 recipeWindow;
    @Autowired
    private MainWindow             generatedComponent;
    @Autowired
    private String                 theme;
    @Autowired
    private Resource               applicationLogo;
    @Autowired
    private Form                   recipeForm;
    @Autowired
    private RecipePlannerPresenter presenter;

    private Logger                 logger;
    @Autowired
    private Embedded               embeddedLogo;

    // TODO move config components to helper
    public void displayRecipeWindow(final Recipe recipe) {
        addToRecipeForm(recipe);
        rootWindow.addWindow(recipeWindow);
    }

    @Override
    public void init() {
        this.logger = LoggerFactory.getLogger(getClass());
        presenter.setRecipePlannerApplication(this);
        generatedComponent.setPresenter(presenter);
        setTheme(theme);
        setMainWindow(rootWindow);
        rootWindow.addComponent(generatedComponent);
        generatedComponent.setApplicationLogo(embeddedLogo);
        initRecipeEditorComponents();
    }

    private void addToRecipeForm(final Recipe recipe) {
        logger.debug("adding recipe to form: " + recipe);
    }

    private void initRecipeEditorComponents() {
        initRecipeWindow();
        initRecipeForm();
    }

    // TODO poss implement nested ingredient form as customfield
    private void initRecipeForm() {
        recipeForm.addField("name", new TextField("Name"));
    }

    private void initRecipeWindow() {
        recipeWindow.setWidth("50%");
        recipeWindow.setHeight("50%");
        recipeWindow.addComponent(recipeForm);
    }
}