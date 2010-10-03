package com.bulbview.ui.events;

import java.util.Date;

import org.bushe.swing.event.EventBus;

import com.vaadin.data.Property;

public class DateSelectionListener implements Property.ValueChangeListener {

    public void valueChange(final Property.ValueChangeEvent event) {
        EventBus.publish(new InitStartDateEvent((Date) event.getProperty().getValue()));
    }

}