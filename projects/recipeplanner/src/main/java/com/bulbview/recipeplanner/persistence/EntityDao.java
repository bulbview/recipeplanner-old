package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.googlecode.objectify.Key;

public interface EntityDao<T extends Entity> {

    public abstract T get(Key<T> key);

    public abstract Collection<T> get(String filter,
                                      Object category);

    public abstract Collection<T> getAll();

    public abstract T getByName(final String name);

    public abstract T save(final T item) throws DaoException;

}