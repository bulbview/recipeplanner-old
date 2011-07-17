package com.bulbview.recipeplanner.ui.presenter;

import com.bulbview.recipeplanner.ui.manager.ViewManager;

public interface IPresenter<V extends ViewManager> {
    
    public void init();
    
    public void setView(final V viewManager);
    
}