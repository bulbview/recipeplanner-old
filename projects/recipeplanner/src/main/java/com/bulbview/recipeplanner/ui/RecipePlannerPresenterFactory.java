package com.bulbview.recipeplanner.ui;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;

public class RecipePlannerPresenterFactory<T extends IPresenter<?, RecipePlannerEventBus>> extends PresenterFactory {

    private final T               presenter;
    private final EventBusManager ebm;
    private final Logger          logger;

    @Inject
    public RecipePlannerPresenterFactory(final EventBusManager ebm, final T recipePlannerPresenter) {
        super(ebm, Locale.UK);
        this.ebm = ebm;
        this.presenter = recipePlannerPresenter;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    protected IPresenter<?, RecipePlannerEventBus> create(final Object arg0) {
        presenter.setEventBus((RecipePlannerEventBus) ebm.getEventBus(RecipePlannerEventBus.class));
        ebm.addSubscriber(presenter);
        logger.info("registering presenter with eventbus {}", presenter);
        return presenter;
    }
}
