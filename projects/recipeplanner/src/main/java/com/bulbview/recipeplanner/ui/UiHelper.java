package com.bulbview.recipeplanner.ui;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiHelper {

    private final Logger     logger;
    private final DateFormat dateFormatter;

    public UiHelper() {
        logger = LoggerFactory.getLogger(getClass());
        dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.UK);
    }

    public String incrementDay(final Date date,
                               final int i) {
        final Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        Date incrementedDate = calendar.getTime();
        logger.debug("incremented date: " + incrementedDate);
        return dateFormatter.format(incrementedDate);
    }

}
