package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ViewManager {
    
    protected final Logger logger;
    
    public ViewManager() {
        this.logger = LoggerFactory.getLogger(getClass());
    }
    
    public abstract void init();
    
}
