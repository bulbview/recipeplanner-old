package com.bulbview.recipeplanner.datamodel;

public class Recipe extends ScheduledItem {

    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}