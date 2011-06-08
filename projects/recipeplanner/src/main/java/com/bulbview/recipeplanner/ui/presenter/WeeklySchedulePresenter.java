package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;

@Component
public class WeeklySchedulePresenter extends Presenter {

    @Autowired
    private ScheduleObjectifyDao scheduleDao;

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

    public void saveSchedule() {
        // TODO Auto-generated method stub

    }

}
