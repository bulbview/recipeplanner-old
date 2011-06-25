package com.bulbview.recipeplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;
import com.bulbview.recipeplanner.ui.presenter.ScheduleHistoryPresenter;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleObjectifyDao     scheduleDao;
    @Autowired
    private ScheduleHistoryPresenter scheduleHistoryPresenter;

    public void save(final Schedule schedule) {
        try {
            scheduleDao.save(schedule);
            updateScheduleHistoryView(schedule);
        } catch (final DaoException e) {
            throw new ScheduleServiceException("Error saving schedule", e);
        }
    }

    public void setScheduleHistoryPresenter(final ScheduleHistoryPresenter scheduleHistoryPresenter) {
        this.scheduleHistoryPresenter = scheduleHistoryPresenter;
    }

    private void updateScheduleHistoryView(final Schedule schedule) {
        scheduleHistoryPresenter.addScheduleToView(schedule);
    }
}
