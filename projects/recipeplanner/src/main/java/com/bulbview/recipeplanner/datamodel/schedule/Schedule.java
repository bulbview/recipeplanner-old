package com.bulbview.recipeplanner.datamodel.schedule;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Embedded;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.bulbview.recipeplanner.datamodel.Item;
import com.google.common.collect.Sets;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Schedule extends Entity {

    private static final DateFormat   dateFormatter = DateFormat.getDateInstance();

    @Embedded
    private final Collection<Section> Sections;
    private Date                      startDate;

    public Schedule() {
        this.Sections = Sets.newHashSet();
        this.startDate = new Date();
    }

    public void addItem(final Item item) {
        throw new IllegalStateException("Not implemented");
    }

    public void addSection(final Section dateSection) {
        Sections.add(dateSection);
    }

    public Collection<Section> getSections() {
        return Sections;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setName(final String name) {
        throw new UnsupportedOperationException("Schedule uses date as name");
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
        super.setName(dateFormatter.format(startDate));
    }
}
