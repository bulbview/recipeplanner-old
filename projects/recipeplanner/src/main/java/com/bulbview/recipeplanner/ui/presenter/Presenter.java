package com.bulbview.recipeplanner.ui.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.ui.manager.ViewManager;

public abstract class Presenter<V extends ViewManager> implements IPresenter<V> {
    
    private V              view;
    protected final Logger logger;
    
    public Presenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }
    
    @Override
    public abstract void init();
    
    @Override
    public void setView(final V viewManager) {
        this.view = viewManager;
    }
    
    protected V getView() {
        return view;
    }
    
}
