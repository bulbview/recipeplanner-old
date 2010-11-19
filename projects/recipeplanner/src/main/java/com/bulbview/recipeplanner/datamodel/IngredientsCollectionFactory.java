package com.bulbview.recipeplanner.datamodel;

import java.util.Collection;
import java.util.HashSet;

public class IngredientsCollectionFactory {

    public Collection<Ingredient> create() {
        return new HashSet<Ingredient>();
    }

}
