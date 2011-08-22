package com.bulbview.recipeplanner.datamodel;

import javax.persistence.Id;

public abstract class Entity {
    
    @Id
    protected Long id = null;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }
    
}