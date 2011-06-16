package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.DailySchedule;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.TabSheet.Tab;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class WeeklySchedule extends ViewManager<WeeklySchedulePresenter> {

    private Accordion                    accordion;

    @Autowired
    private ObjectFactory<DailySchedule> dayScheduleListFactory;
    private final Logger                 logger;

    public WeeklySchedule() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public Tab createTab(final String header) {
        logger.debug("Creating tab with header {}", header);
        final DailySchedule dayScheduleList = createDaySchedule();
        logger.debug("accordion:{}, dayScheduleList:{} ", accordion, dayScheduleList);
        return accordion.addTab(dayScheduleList.getTopLevelPanel(), header, null);
    }

    @Override
    public void init() {
        accordion.setStyleName("opaque borderless");
    }

    @Autowired
    public void setMainComponent(final MainWindow mainWindow) {
        this.accordion = mainWindow.getSchedulerAccordion();
    }

    @Autowired
    @Override
    public void setPresenter(final WeeklySchedulePresenter presenter) {
        super.setPresenter(presenter);
    }

    private DailySchedule createDaySchedule() {
        final DailySchedule dayScheduleList = dayScheduleListFactory.getObject();
        dayScheduleList.init();
        return dayScheduleList;
    }

}
