package com.bulbview.recipeplanner.ui;

import java.util.Locale;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.PresenterFactory;

import com.google.inject.Inject;

public class RecipePlannerPresenterFactory extends PresenterFactory {

    private final RecipePlannerPresenter recipePlannerPresenter;
    private final EventBusManager        ebm;

    @Inject
    public RecipePlannerPresenterFactory(final EventBusManager ebm, final RecipePlannerPresenter recipePlannerPresenter) {
        super(ebm, Locale.UK);
        this.ebm = ebm;
        this.recipePlannerPresenter = recipePlannerPresenter;
    }

    @Override
    protected IPresenter<?, ? extends EventBus> create(final Object arg0) {
        recipePlannerPresenter.setEventBus((RecipePlannerEventBus) ebm.getEventBus(RecipePlannerEventBus.class));
        ebm.addSubscriber(recipePlannerPresenter);
        return recipePlannerPresenter;
    }
}
