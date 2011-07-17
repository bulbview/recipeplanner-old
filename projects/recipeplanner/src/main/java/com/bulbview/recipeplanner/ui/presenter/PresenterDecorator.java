package com.bulbview.recipeplanner.ui.presenter;

import com.bulbview.recipeplanner.ui.manager.ViewManager;

public abstract class PresenterDecorator<T extends IPresenter<V>, V extends ViewManager> extends Presenter<V> {
    
    protected final T decoratedPresenter;
    
    public PresenterDecorator(final T presenter) {
        this.decoratedPresenter = presenter;
    }
    
    @Override
    public void init() {
        decoratedPresenter.init();
    }
    
    @Override
    public void setView(final V viewManager) {
        decoratedPresenter.setView(viewManager);
    };
    
}
