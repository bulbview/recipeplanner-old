package com.bulbview.recipeplanner.datamodel.schedule;

import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SectionFactory {

    @Autowired
    private ObjectFactory<DateSection> dateSectionFactory;
    @Autowired
    private ObjectFactory<NameSection> nameSectionFactory;

    public Section create(final Object o) {
        Section section = null;
        if( o instanceof String ) {
            section = createSection((String) o);
        } else if( o instanceof Date ) {
            section = createSection((Date) o);
        }
        return section;
    }

    private DateSection createSection(final Date date) {
        final DateSection dateSection = dateSectionFactory.getObject();
        dateSection.setDate(date);
        return dateSection;
    }

    private NameSection createSection(final String name) {
        final NameSection nameSection = nameSectionFactory.getObject();
        nameSection.setName(name);
        return nameSection;
    }

}
