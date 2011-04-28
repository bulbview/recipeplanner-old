package com.bulbview.recipeplanner.ui;

import com.bulbview.ui.events.DateSelectionListener;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.PopupDateField;

public class StartDateSelector extends PopupDateField {

    
    public StartDateSelector(final DateSelectionListener dateSelectionListener) {
        setCaption("Select the starting date:");
        setResolution(InlineDateField.RESOLUTION_DAY);
        addListener(dateSelectionListener);
        setImmediate(true);

        initialiseToTodaysDate();
    }

    private void initialiseToTodaysDate() {
        setValue(new java.util.Date());
    }

}
