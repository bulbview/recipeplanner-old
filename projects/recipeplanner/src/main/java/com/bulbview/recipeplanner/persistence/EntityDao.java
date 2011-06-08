package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Entity;

public interface EntityDao<T extends Entity> {

    public abstract Collection<T> getAll();

    public abstract T getByName(final String name);

    public abstract T save(final T item);

}