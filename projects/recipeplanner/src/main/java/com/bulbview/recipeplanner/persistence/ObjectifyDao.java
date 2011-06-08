package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.google.appengine.repackaged.com.google.common.collect.Iterators;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class ObjectifyDao<T extends Entity> implements EntityDao<T> {

    protected final Logger   logger;
    private final Class<T>   entityClass;

    @Autowired
    private ObjectifyFactory objectifyFactory;

    public ObjectifyDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public T get(final Key<T> key) {
        return beginObjectify().get(key);

    }

    public T get(final Long id) {
        final Objectify objectify = beginObjectify();
        return objectify.get(entityClass, id);
    }

    public Collection<T> getAll() {
        final Objectify objectify = beginObjectify();
        final Query<T> query = objectify.query(entityClass);
        return query.list();
    }

    public T getByName(final String name) {
        final Collection<T> collection = get("name", name);
        verifyDiscreteEntityReturned(collection);
        return getEntity(collection);
    }

    @PostConstruct
    public void registerEntityType() {
        logger.info("Registering datastore entity type: {}", entityClass.getName());
        objectifyFactory.register(entityClass);

    }

    public T save(final T entity) {
        logger.debug("Saving entity: {}...", entity);
        final Objectify objectify = beginObjectify();
        final Key<T> putEntityKey = objectify.put(entity);
        return objectify.get(putEntityKey);

    }

    protected Collection<T> get(final String filter,
                                final Object value) {
        final Objectify objectify = beginObjectify();
        return objectify.query(entityClass).filter(filter, value).list();
    }

    private Objectify beginObjectify() {
        return objectifyFactory.begin();
    }

    private T getEntity(final Collection<T> collection) {
        return Iterators.getNext(collection.iterator(), null);
    }

    private void verifyDiscreteEntityReturned(final Collection<T> collection) {
        Preconditions.checkState(collection.size() == 1, "name should only return 1 entity");
    }

}