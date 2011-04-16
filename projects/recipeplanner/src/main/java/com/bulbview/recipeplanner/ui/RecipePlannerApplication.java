package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.inject.Inject;
import com.vaadin.Application;
import com.vaadin.ui.Window;

@Configurable(preConstruction = true)
public class RecipePlannerApplication extends Application {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //
    //

    private final Logger      logger;

    @Inject
    public RecipePlannerApplication() {
        this.logger = LoggerFactory.getLogger(getClass());
        // this.applicationName = applicationName;
    }

    @Override
    public void init() {
        initLayout();
    }

    private void initLayout() {
        setMainWindow(new Window("Hello"));

        // mainWindow.setContent(mainWindowLayout);
        // mainWindowLayout.setSizeFull();

    }
}
