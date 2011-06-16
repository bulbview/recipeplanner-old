package com.bulbview.recipeplanner.ui;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.presenter.SessionPresenter;

@Component
public class SessionPresenterInitialiser {

    private final Logger                 logger;
    @Autowired
    private Collection<SessionPresenter> presenters;

    public SessionPresenterInitialiser() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void initialise() {
        for ( final SessionPresenter presenter : presenters ) {
            presenter.init();
            logger.debug("...{} presenter initialised", presenter);
        }
    }
}
