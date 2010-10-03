package com.bulbview.recipeplanner.ui;

import com.bulbview.ui.events.DateSelectionListener;
import com.google.inject.Inject;
import com.vaadin.ui.InlineDateField;

public class StartDateSelector extends InlineDateField {

    private final DateSelectionListener dateSelectionListener;

    @Inject
    public StartDateSelector(final DateSelectionListener dateSelectionListener) {
        this.dateSelectionListener = dateSelectionListener;
        setCaption("Select the starting date:");

        initialiseToTodaysDate();
        // Set the correct resolution
        setResolution(InlineDateField.RESOLUTION_DAY);
        addListener(dateSelectionListener);
        setImmediate(true);
    }

    private void initialiseToTodaysDate() {
        setValue(new java.util.Date());
    }

}
