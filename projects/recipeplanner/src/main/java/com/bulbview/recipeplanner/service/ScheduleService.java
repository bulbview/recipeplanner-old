package com.bulbview.recipeplanner.service;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleObjectifyDao    scheduleDao;

    @Autowired
    private ObjectFactory<Schedule> scheduleFactory;

    public Schedule createSchedule() {
        return scheduleFactory.getObject();
    }

    public void save(final Schedule schedule) {
        try {
            scheduleDao.save(schedule);
        } catch (final DaoException e) {
            throw new ScheduleServiceException("Error saving schedule", e);
        }
    }

}
