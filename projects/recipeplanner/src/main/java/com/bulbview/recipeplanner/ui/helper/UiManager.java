package com.bulbview.recipeplanner.ui.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.ui.RecipePlannerPresenter;

public abstract class UiManager {

    protected final Logger           logger;

    protected RecipePlannerPresenter presenter;

    public UiManager() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public abstract void init();

    public void setPresenter(final RecipePlannerPresenter presenter) {
        this.presenter = presenter;
    }

}
