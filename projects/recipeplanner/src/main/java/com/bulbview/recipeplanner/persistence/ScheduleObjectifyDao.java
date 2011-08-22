package com.bulbview.recipeplanner.persistence;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.google.common.collect.Iterators;
import com.googlecode.objectify.Key;

@Component
public class ScheduleObjectifyDao implements EntityDao<Schedule> {
    
    @Autowired
    private ObjectifyDaoTransaction<Schedule> scheduleDao;
    
    @Override
    public Schedule get(final Key<Schedule> key) {
        return scheduleDao.get(key);
    }
    
    @Override
    public Collection<Schedule> get(final String filter, final Object entity) {
        return scheduleDao.get(filter, entity);
    }
    
    @Override
    public Collection<Schedule> getAll() {
        return scheduleDao.getAll();
    }
    
    public Schedule getByDate(final Date date) {
        final Collection<Schedule> collection = scheduleDao.get("startDate", date);
        if (collection.size() > 1) {
            throw new DuplicateDateException(date.toString());
        }
        return Iterators.getNext(collection.iterator(), null);
    }
    
    @Override
    public Schedule save(final Schedule schedule) throws DaoException {
        return scheduleDao.save(schedule);
    }
    
}
