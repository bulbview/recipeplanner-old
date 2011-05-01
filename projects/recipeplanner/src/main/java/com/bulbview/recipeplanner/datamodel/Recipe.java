package com.bulbview.recipeplanner.datamodel;

import javax.persistence.Id;

public class Recipe {

    @Id
    private String name;

    public Recipe() {
        this.name = "<Enter Name>";
    }

    @Override
    public boolean equals(final Object obj) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if( name == null ) {
            if( other.name != null ) {
                return false;
            }
        } else if( !name.equals(other.name) ) {
            return false;
        }
        return true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}