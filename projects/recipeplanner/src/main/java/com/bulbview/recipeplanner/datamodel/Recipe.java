package com.bulbview.recipeplanner.datamodel;

public class Recipe extends Entity {

    @Override
    public String toString() {
        return String.format("Recipe [name=%s]", name);
    }
}