package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Schedule;

@Component
public class ScheduleObjectifyDao implements EntityDao<Schedule> {

    @Autowired
    private ObjectifyDao<Schedule> dao;

    @Override
    public Collection<Schedule> getAll() {
        return dao.getAll();
    }

    @Override
    public Schedule getByName(final String name) {
        return dao.getByName(name);
    }

    @Override
    public Schedule save(final Schedule schedule) {
        return dao.save(schedule);
    }

}
