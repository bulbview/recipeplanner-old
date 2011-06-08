package com.bulbview.recipeplanner.datamodel;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Embedded;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;

public class Schedule extends Entity {

    public class Day {

        private Date                  date;
        private Collection<Key<Item>> items;

        public void addItem(final Item item) {
            items.add(new Key<Item>(Item.class, item.getName()));
        }

        public void setDate(final Date date) {
            this.date = date;
        }
    }

    @Embedded
    private final Collection<Day> days;

    public Schedule() {
        this.days = Sets.newHashSet();
    }

    public void addDay(final Day day) {
        throw new IllegalStateException("Not implemented");
    }

    public void addItem(final Item item) {
        throw new IllegalStateException("Not implemented");
    }
}
