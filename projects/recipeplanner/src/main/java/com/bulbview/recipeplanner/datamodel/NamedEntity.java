package com.bulbview.recipeplanner.datamodel;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public abstract class NamedEntity {
    
    @Id
    protected Long   id;
    
    @NotNull
    @NotEmpty(message = "Name is a required field")
    protected String name;
    
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
        final NamedEntity other = (NamedEntity) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Entity [id=" + id + ", name=" + name + "]";
    }
    
}