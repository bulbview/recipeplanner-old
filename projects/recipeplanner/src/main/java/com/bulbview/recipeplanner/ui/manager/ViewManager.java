package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.ui.presenter.Presenter;

public abstract class ViewManager<P extends Presenter> {

    protected final Logger logger;

    protected P            presenter;

    public ViewManager() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public abstract void init();

    public void setPresenter(final P presenter) {
        this.presenter = presenter;
    }

}
