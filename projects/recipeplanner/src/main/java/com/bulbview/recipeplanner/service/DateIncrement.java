package com.bulbview.recipeplanner.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class DateIncrement {
    
    public Date increment(Date date, int incrementBy) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, incrementBy);
        return calendar.getTime();
    }
    
}
