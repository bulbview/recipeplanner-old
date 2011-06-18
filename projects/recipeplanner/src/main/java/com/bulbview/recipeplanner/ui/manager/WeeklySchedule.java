package com.bulbview.recipeplanner.ui.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.DailyScheduleView;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.ui.Accordion;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class WeeklySchedule extends ViewManager<WeeklySchedulePresenter> {

    private Accordion    accordion;

    private final Logger logger;

    public WeeklySchedule() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addTab(final String header,
                       final DailyScheduleView dayScheduleList) {
        accordion.addTab(dayScheduleList.getTopLevelPanel(), header, null);
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

}
