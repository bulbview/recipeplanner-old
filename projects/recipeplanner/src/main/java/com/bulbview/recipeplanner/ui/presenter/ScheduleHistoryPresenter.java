package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.ScheduleHistoryListView;

@Component
public class ScheduleHistoryPresenter extends Presenter<ScheduleHistoryListView> implements SessionPresenter {
    
    @Autowired
    private MainWindowView          mainWindowView;
    @Autowired
    private ScheduleObjectifyDao    scheduleDao;
    @Autowired
    private WeeklySchedulePresenter weeklySchedulePresenter;
    
    public void addScheduleToView(final Schedule schedule) {
        logger.debug("Adding schedule to view: {}...", schedule);
        getView().addListItem(schedule);
    }
    
    @Override
    public void init() {
        getView().init();
        final Collection<Schedule> schedules = scheduleDao.getAll();
        logger.debug("{} schedules retrieved", schedules.size());
        for (final Schedule schedule : schedules) {
            addScheduleToView(schedule);
        }
    }
    
    //TODO modify to receive a date to load - schedule view should display a list of dates 
    public void load(final Schedule schedule) {
        logger.debug("loading schedule: {}...", schedule);
        weeklySchedulePresenter.setStartDate(schedule.getStartDate());
    }
    
    public void setMainWindowView(final MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
    }
    
    @Override
    @Autowired
    public void setView(final ScheduleHistoryListView scheduleHistoryListView) {
        super.setView(scheduleHistoryListView);
        getView().setPresenter(this);
    }
    
    public void setWeeklySchedulePresenter(final WeeklySchedulePresenter weeklySchedulePresenter) {
        this.weeklySchedulePresenter = weeklySchedulePresenter;
    }
    
    public void showHistory() {
        mainWindowView.showScheduleHistoryWindow();
    }
    
}
