package com.bulbview.recipeplanner.ui.presenter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.ScheduleHistoryListView;

@Component
public class ScheduleHistoryPresenter extends Presenter implements SessionPresenter {

    @Autowired
    private MainWindowView          mainWindowView;
    @Autowired
    private ScheduleObjectifyDao    scheduleDao;
    @Autowired
    private ScheduleHistoryListView scheduleHistoryListView;

    public void addScheduleToView(final Schedule schedule) {
        logger.debug("Adding schedule to view: {}...", schedule);
        scheduleHistoryListView.addListItem(schedule);
    }

    @Override
    public void init() {
        scheduleHistoryListView.init();
        scheduleHistoryListView.setPresenter(this);
        final Collection<Schedule> schedules = scheduleDao.getAll();
        logger.debug("{} schedules retrieved", schedules.size());
        for ( final Schedule schedule : schedules ) {
            addScheduleToView(schedule);
        }
    }

    public void load(final Schedule schedule) {
        logger.debug("loading schedule: {}...", schedule);

    }

    public void setMainWindowView(final MainWindowView mainWindowView) {
        this.mainWindowView = mainWindowView;
    }

    public void showHistory() {
        mainWindowView.showScheduleHistoryWindow();
    }

    protected void setView(final ScheduleHistoryListView scheduleHistoryListView) {
        this.scheduleHistoryListView = scheduleHistoryListView;
    }

}
