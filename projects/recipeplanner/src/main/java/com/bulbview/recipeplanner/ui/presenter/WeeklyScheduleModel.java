package com.bulbview.recipeplanner.ui.presenter;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.datamodel.schedule.Section;

@Component
public class WeeklyScheduleModel {

    private final Logger            logger;

    private Schedule                schedule;
    @Autowired
    private ObjectFactory<Schedule> scheduleFactory;

    public WeeklyScheduleModel() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addSection(final Section section) {
        schedule.addSection(section);

    }

    public void createSchedule() {
        this.schedule = scheduleFactory.getObject();
        logger.debug("...Schedule created {} ", schedule);

    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Date getStartDate() {
        return schedule.getStartDate();
    }

    public void setStartDate(final Date startDate) {
        schedule.setStartDate(startDate);
        logger.debug("start date: {}", startDate);
    }
}
