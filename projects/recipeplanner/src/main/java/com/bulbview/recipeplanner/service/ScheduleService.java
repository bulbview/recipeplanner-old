package com.bulbview.recipeplanner.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulbview.recipeplanner.datamodel.factory.ScheduleFactory;
import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.ScheduleObjectifyDao;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleObjectifyDao scheduleDao;
    
    @Autowired
    private ScheduleFactory      scheduleFactory;
    
    // Every schedule created should have the default sections(7 daily plus 1
    // misc)
    public Schedule createSchedule(final Date startDate) {
        return scheduleFactory.create(startDate);
    }
    
    public Schedule getSchedule(final Date startDate) {
        final Collection<Schedule> collection = scheduleDao.get("startDate", startDate);
        if (collection.size() == 1) {
            return collection.iterator().next();
        }
        return null;
        
    }
    
    public void save(final Schedule schedule) {
        try {
            scheduleDao.save(schedule);
        }
        catch (final DaoException e) {
            throw new ScheduleServiceException("Error saving schedule", e);
        }
    }
    
}
