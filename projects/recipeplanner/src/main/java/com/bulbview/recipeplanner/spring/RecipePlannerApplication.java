package com.bulbview.recipeplanner.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long   serialVersionUID = 1L;

    private static final String NO_CAPTION       = null;

    @Autowired
    private Window              rootWindow;
    @Autowired
    private MainWindow          recipePlannerMainCustomComponent;
    @Autowired
    private String              applicationName;

    @Override
    public void init() {
        setTheme("recipeplanner-theme");
        setMainWindow(rootWindow);
        rootWindow.addComponent(recipePlannerMainCustomComponent);
        rootWindow.setCaption(applicationName);
        // final Application required to final create classpath resource
        final Resource image = new ThemeResource("images/recipeplannerlogo.jpg");
        recipePlannerMainCustomComponent.setApplicationLogo(new Embedded(NO_CAPTION, image));
    }
}