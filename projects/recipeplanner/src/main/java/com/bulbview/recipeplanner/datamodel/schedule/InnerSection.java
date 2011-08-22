package com.bulbview.recipeplanner.datamodel.schedule;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ScheduledItem;
import com.google.appengine.repackaged.com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Serialized;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InnerSection {
    
    private Date                  date;
    
    @Autowired
    @Transient
    private SimpleDateFormat      dateFormatter;
    @Serialized
    private Collection<Key<Item>> items  = Sets.newHashSet();
    @Transient
    private Logger                logger = LoggerFactory.getLogger(getClass());
    private String                name;
    
    public void addItem(ScheduledItem item) {
        
        if (item.getId() != null) {
            // TODO create key factory
            items.add(new Key<Item>(Item.class, item.getId()));
        }
        else {
            // TODO implement hibernate validator to validate item state
            throw new NullEntityIdException("No Id defined for item");
        }
        
    }
    
    public void clear() {
        // TODO Auto-generated method stub
        
    }
    
    public Date getDate() {
        return date;
    }
    
    public Collection<Key<Item>> getItems() {
        return items;
    }
    
    public String getName() {
        return name;
    }
    
    public void setDate(Date date) {
        this.date = date;
        this.name = dateFormatter.format(date);
    }
    
    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
}