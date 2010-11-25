package com.bulbview.recipeplanner.ui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

public class UiHelper {

    private final Logger     logger;
    private final DateFormat dateFormatter;

    public UiHelper() {
        logger = LoggerFactory.getLogger(getClass());
        dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.UK);
    }

    public ComboBox getComboBox(final Item item,
                                final String propertyId) {
        return (ComboBox) item.getItemProperty(propertyId).getValue();
    }

    public String incrementDay(final Date date,
                               final int i) {
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        final Date incrementedDate = calendar.getTime();
        logger.debug("incremented date: " + incrementedDate);
        return dateFormatter.format(incrementedDate);
    }

}
