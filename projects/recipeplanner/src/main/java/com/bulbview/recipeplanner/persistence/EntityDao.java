package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Entity;

public interface EntityDao<T extends Entity> {

    public abstract T get(final String name);

    public abstract Collection<T> getAll();

    public abstract T save(final T item);

}