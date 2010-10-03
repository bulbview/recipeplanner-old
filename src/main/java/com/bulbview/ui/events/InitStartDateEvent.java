package com.bulbview.ui.events;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitStartDateEvent {

    private final Date   startDate;
    private final Logger logger;

    public InitStartDateEvent(final Date startDate) {
        this.startDate = startDate;
        this.logger = LoggerFactory.getLogger(getClass());
        logger.debug("Start date event created...");
    }

    public Date getStartDate() {
        return startDate;
    }

}
