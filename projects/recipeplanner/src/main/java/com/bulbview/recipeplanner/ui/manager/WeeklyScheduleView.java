package com.bulbview.recipeplanner.ui.manager;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.ui.DailyScheduleView;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.WeeklySchedulePresenter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.DateField;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class WeeklyScheduleView extends ViewManager<WeeklySchedulePresenter> {

    private Accordion    accordion;
    @Autowired
    private String       dateFormat;

    private final Logger logger;

    private DateField    startDateField;

    public WeeklyScheduleView() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addTab(final DailyScheduleView dayScheduleList) {
        accordion.addTab(dayScheduleList.getTopLevelPanel(), dayScheduleList.getHeader(), null);
    }

    public void clearSectionsFromSchedule() {
        accordion.removeAllComponents();
    }

    @Override
    public void init() {
        accordion.setStyleName("opaque borderless");
        accordion.setImmediate(true);
        startDateField.setDescription("Select schedule start date");
        startDateField.addListener(dateFieldValueChangeListener());
        startDateField.setDateFormat(dateFormat);
        startDateField.setImmediate(true);
    }

    @Autowired
    public void setMainComponent(final MainWindow mainWindow) {
        this.accordion = mainWindow.getSchedulerAccordion();
        this.startDateField = mainWindow.getStartPopupDateField();
    }

    @Autowired
    @Override
    public void setPresenter(final WeeklySchedulePresenter presenter) {
        super.setPresenter(presenter);
    }

    public void setStartDateField(final Date startDate) {
        this.startDateField.setValue(startDate);
    }

    @SuppressWarnings("serial")
    private ValueChangeListener dateFieldValueChangeListener() {
        return new ValueChangeListener() {

            @Override
            public void valueChange(final ValueChangeEvent event) {
                final Date startDate = (Date) startDateField.getValue();
                logger.debug("new start date selected {}", startDate);
                presenter.setStartDate(startDate);

            }
        };
    }

}
