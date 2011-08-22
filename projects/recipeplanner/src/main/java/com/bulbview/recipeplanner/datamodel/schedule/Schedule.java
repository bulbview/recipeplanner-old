package com.bulbview.recipeplanner.datamodel.schedule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.bulbview.recipeplanner.datamodel.Item;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Schedule extends Entity {
    
    @Autowired
    @Transient
    private SimpleDateFormat         dateFormatter;
    
    @Embedded
    private Collection<InnerSection> dateSections = Lists.newArrayList();
    
    @Transient
    private final Logger             logger;
    private Date                     startDate;
    @Embedded
    private Collection<InnerSection> stringSections;
    
    public Schedule() {
        this.logger = LoggerFactory.getLogger(getClass());
        this.stringSections = Sets.newLinkedHashSet();
        this.startDate = new Date();
    }
    
    public void addDateSection(final InnerSection section) {
        dateSections.add(section);
        logger.info("section added to schedule: {}", section);
    }
    
    public void addItem(final Item item) {
        throw new IllegalStateException("Not implemented");
    }
    
    public void addStringSection(InnerSection create) {
        stringSections.add(create);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Schedule other = (Schedule) obj;
        if (startDate == null) {
            if (other.startDate != null) {
                return false;
            }
        }
        else if (!startDate.equals(other.startDate)) {
            return false;
        }
        return true;
    }
    
    public Collection<InnerSection> getDateSections() {
        return dateSections;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public Collection<InnerSection> getStringSections() {
        return stringSections;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }
    
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
        updateDateSchedules(startDate);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Schedule [startDate=");
        builder.append(startDate);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
    
    private void updateDateSchedules(Date startDate) {
        for (InnerSection section : dateSections) {
            if (section instanceof InnerSection) {
                section.setDate(startDate);
            }
        }
        
    }
}