package com.bulbview.recipeplanner.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bulbview.recipeplanner.ui.MainWindow;
import com.vaadin.Application;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long   serialVersionUID = 1L;

    private static final String NO_CAPTION       = "";

    @Autowired
    private Window              rootWindow;
    @Autowired
    private MainWindow          recipePlannerMainCustomComponent;
    @Autowired
    private String              applicationName;
    @Autowired
    private String              theme;
    @Autowired
    private Resource            applicationLogo;

    @Override
    public void init() {
        setTheme(theme);
        setMainWindow(rootWindow);
        rootWindow.addComponent(recipePlannerMainCustomComponent);
        rootWindow.setCaption(applicationName);
        recipePlannerMainCustomComponent.setApplicationLogo(new Embedded(NO_CAPTION, applicationLogo));
    }
}