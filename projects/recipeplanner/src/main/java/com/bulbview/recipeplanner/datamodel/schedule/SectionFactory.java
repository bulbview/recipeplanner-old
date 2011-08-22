package com.bulbview.recipeplanner.datamodel.schedule;

import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SectionFactory {
    
    @Autowired
    private ObjectFactory<InnerSection> dateSectionFactory;
    
    // @Autowired
    // private ObjectFactory<NameSection> nameSectionFactory;
    
    public InnerSection create(final Object o) {
        InnerSection section = null;
        if (o instanceof String) {
            section = createSection((String) o);
        }
        else if (o instanceof Date) {
            section = createSection((Date) o);
        }
        return section;
    }
    
    private InnerSection createSection(final Date date) {
        final InnerSection dateSection = dateSectionFactory.getObject();
        dateSection.setDate(date);
        return dateSection;
    }
    
    private InnerSection createSection(final String name) {
        final InnerSection nameSection = dateSectionFactory.getObject();
        nameSection.setName(name);
        return nameSection;
    }
    
}
