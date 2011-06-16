package com.bulbview.recipeplanner.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bulbview.recipeplanner.ui.SessionPresenterInitialiser;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    private static final long           serialVersionUID = 1L;

    private Logger                      logger;
    @Autowired
    private Window                      rootWindow;
    @Autowired
    private SessionPresenterInitialiser sessionPresenterInitialiser;
    @Autowired
    private String                      theme;

    @Override
    public void init() {
        this.logger = LoggerFactory.getLogger(getClass());
        logger.info("Initialising application...");
        setTheme(theme);
        setMainWindow(rootWindow);
        sessionPresenterInitialiser.initialise();
    }

}