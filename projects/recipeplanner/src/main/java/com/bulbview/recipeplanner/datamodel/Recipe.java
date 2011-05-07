package com.bulbview.recipeplanner.datamodel;

public class Recipe extends ScheduledItem {

    public Recipe() {
        this.name = "<Enter Name>";
    }

    @Override
    public boolean equals(final Object obj) {
        if( getClass() != obj.getClass() ) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}