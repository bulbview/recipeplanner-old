package com.bulbview.recipeplanner.datamodel;

import javax.persistence.Id;

public class ScheduledItem {

    @Id
    protected String name;

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
        final ScheduledItem other = (ScheduledItem) obj;
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
        return String.format("ScheduledItem [name=%s]", name);
    }

}