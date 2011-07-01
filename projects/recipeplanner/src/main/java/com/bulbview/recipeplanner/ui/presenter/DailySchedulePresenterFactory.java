package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailySchedulePresenterFactory {

    @Autowired
    private ObjectFactory<DailySchedulePresenter> factory;

    public DailySchedulePresenter create() {
        final DailySchedulePresenter daySchedulePresenter = factory.getObject();
        daySchedulePresenter.init();
        return daySchedulePresenter;
    }

    public void setDailyScheduleListFactory(final ObjectFactory<DailySchedulePresenter> daySchedulePresenterFactory) {
        this.factory = daySchedulePresenterFactory;
    }
}
