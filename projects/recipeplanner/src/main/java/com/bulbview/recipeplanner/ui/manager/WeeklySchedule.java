package com.bulbview.recipeplanner.ui.manager;

import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.DailySchedule;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.TabSheet.Tab;

@Component
public class WeeklySchedule extends ViewManager<WeeklySchedulePresenter> {

    private static final int             DAY_IN_MILLIS = 1 * 24 * 60 * 60 * 1000;
    private Accordion                    accordion;
    private final DateFormat             dateFormatter;
    @Autowired
    private ObjectFactory<DailySchedule> dayScheduleListFactory;
    private final Logger                 logger;

    public WeeklySchedule() {
        this.dateFormatter = DateFormat.getDateInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void init() {
        presenter.init();
        accordion.setStyleName("opaque borderless");
        final Date startDate = new Date();
        createDailyTabs(startDate);
        createTab("Additional Items");
    }

    @Autowired
    @Override
    public void setPresenter(final WeeklySchedulePresenter presenter) {
        super.setPresenter(presenter);
    }

    public void setScheduler(final Accordion accordion) {
        this.accordion = accordion;
    }

    private void createDailyTabs(final Date date) {
        logger.debug("Creating daily tabs...");
        for ( int i = 0; i < 7; i++ ) {
            createTab(increment(date, i));
        }
    }

    private Tab createTab(final String header) {
        logger.debug("Creating tab with header {}", header);
        final DailySchedule dayScheduleList = dayScheduleListFactory.getObject();
        dayScheduleList.init();
        return accordion.addTab(dayScheduleList.getTopLevelPanel(), header, null);
    }

    private String increment(final Date date,
                             final int incrementBy) {
        final Date incrementedDate = new Date(date.getTime() + incrementBy * DAY_IN_MILLIS);

        return dateFormatter.format(incrementedDate);
    }
}
