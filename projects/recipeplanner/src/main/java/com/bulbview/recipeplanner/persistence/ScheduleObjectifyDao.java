package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
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
    public Collection<Schedule> get(final String filter,
                                    final Object entity) {
        return scheduleDao.get(filter, entity);
    }

    @Override
    public Collection<Schedule> getAll() {
        return scheduleDao.getAll();
    }

    @Override
    public Schedule getByName(final String name) {
        return scheduleDao.getByName(name);
    }

    @Override
    public Schedule save(final Schedule schedule) throws DaoException {
        return scheduleDao.save(schedule);
    }

}
