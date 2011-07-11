package com.bulbview.recipeplanner.datamodel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.objectify.Key;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Ingredient {
    
    private Key<Item> itemKey;
    
    private String    name;
    
    public Key<Item> getItemKey() {
        return itemKey;
    }
    
    public String getName() {
        return name;
    }
    
    public void setItem(final Item item) {
        this.itemKey = new Key<Item>(Item.class, item.getId());
        this.name = item.name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}