package com.bulbview.ui.events;

import java.util.Date;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;
import com.vaadin.data.Property;

public class DateSelectionListener implements Property.ValueChangeListener {

    private final RecipePlannerEventBus recipePlannerEventBus;

    @Inject
    public DateSelectionListener(final RecipePlannerEventBus recipePlannerEventBus) {
        this.recipePlannerEventBus = recipePlannerEventBus;
    }

    public void valueChange(final Property.ValueChangeEvent event) {
        recipePlannerEventBus.setStartDate((Date) event.getProperty().getValue());
    }
}