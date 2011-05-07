package com.bulbview.recipeplanner.ui.helper;

import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet.Tab;

@Component
public class SchedulerUiHelper extends UiManager {

    private static final int DAY_IN_MILLIS = 1 * 24 * 60 * 60 * 1000;
    private Accordion        accordion;
    private final DateFormat dateFormatter;
    private final Logger     logger;

    public SchedulerUiHelper() {
        this.dateFormatter = DateFormat.getDateInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    public void setScheduler(final Accordion accordion) {
        this.accordion = accordion;
        accordion.setStyleName("opaque borderless");
        final Date startDate = new Date();
        createDailyTabs(startDate);
        createTab("Additional Items");
    }

    private void createDailyTabs(final Date date) {
        logger.debug("Creating daily tabs...");
        for ( int i = 0; i < 7; i++ ) {
            createTab(increment(date, i));
        }
    }

    private Tab createTab(final String header) {
        final Panel panel = new Panel();
        panel.setSizeFull();
        return accordion.addTab(panel, header, null);
    }

    private String increment(final Date date,
                             final int incrementBy) {
        final Date incrementedDate = new Date(date.getTime() + incrementBy * DAY_IN_MILLIS);

        return dateFormatter.format(incrementedDate);
    }
}
