package com.bulbview.recipeplanner.ui.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.ui.manager.ViewManager;

public abstract class Presenter {

    protected final Logger logger;

    public Presenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public abstract void init();

    protected void setUiManager(final ViewManager viewManager) {
        viewManager.setPresenter(this);
    }
}
