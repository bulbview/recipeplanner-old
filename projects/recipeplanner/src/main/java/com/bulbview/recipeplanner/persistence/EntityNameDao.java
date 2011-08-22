package com.bulbview.recipeplanner.persistence;


import com.bulbview.recipeplanner.datamodel.Entity;

public interface EntityNameDao<T extends Entity> extends EntityDao<T> {
    
    public abstract T getByName(final String name);
    
}