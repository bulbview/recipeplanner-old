package com.bulbview.recipeplanner.datamodel.factory;

import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.schedule.Schedule;
import com.bulbview.recipeplanner.datamodel.schedule.SectionFactory;
import com.bulbview.recipeplanner.service.DateIncrement;

@Component
public class ScheduleFactory {
    
    @Autowired
    private DateIncrement           dateUtil;
    @Autowired
    private ObjectFactory<Schedule> scheduleObjectFactory;
    
    @Autowired
    private SectionFactory          sectionFactory;
    
    public Schedule create(final Date startDate) {
        final Schedule schedule = scheduleObjectFactory.getObject();
        schedule.setStartDate(startDate);
        createSchedulesDailySections(startDate, schedule);
        createMiscellaneousSection(schedule);
        return schedule;
    }
    
    private void createMiscellaneousSection(final Schedule schedule) {
        schedule.addStringSection(sectionFactory.create("Miscellaneous Items"));
    }
    
    private void createSchedulesDailySections(final Date startDate, final Schedule schedule) {
        for (int i = 0; i < 7; i++) {
            Date addDay = dateUtil.increment(startDate, i);
            schedule.addDateSection(sectionFactory.create(addDay));
        }
    }
}
