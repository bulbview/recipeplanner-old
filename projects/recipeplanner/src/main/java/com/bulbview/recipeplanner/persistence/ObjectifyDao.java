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

    private Objectify        objectify;
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public ObjectifyDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public T get(final Key<T> key) {
        beginObjectify();
        return objectify.get(key);
    }

    public T get(final Long id) {
        beginObjectify();
        return objectify.get(entityClass, id);
    }

    public Collection<T> get(final String filter,
                             final Object entity) {
        beginObjectify();
        return objectify.query(entityClass).filter(filter, entity).list();
    }

    public Collection<T> getAll() {
        beginObjectify();
        final Query<T> query = objectify.query(entityClass);
        return query.list();
    }

    public T getByName(final String name) {
        final Collection<T> collection = get("name", name);
        verifyDiscreteEntityReturned(collection);
        return getEntity(collection);
    }

    public ObjectifyFactory getObjectifyFactory() {
        return objectifyFactory;
    }

    @PostConstruct
    public void registerEntityType() {
        logger.info("Registering datastore entity type: {}", entityClass.getName());
        objectifyFactory.register(entityClass);

    }

    public T save(final T entity) throws DaoException {
        logger.debug("Saving entity: {}...", entity);
        beginObjectify();
        if( !isUnique(entity) ) {
            throw new DaoException("Attempting to persist duplicate name: " + entity.getName());
        }
        final Key<T> putEntityKey = objectify.put(entity);
        return objectify.get(putEntityKey);

    }

    public void setObjectify(final Objectify objectify) {
        this.objectify = objectify;

    }

    private void beginObjectify() {
        this.objectify = objectifyFactory.begin();
    }

    private T getEntity(final Collection<T> collection) {
        return Iterators.getNext(collection.iterator(), null);
    }

    private boolean isUnique(final T entity) {
        final T persistenceEntity = getByName(entity.getName());
        return persistenceEntity == null;
    }

    private void verifyDiscreteEntityReturned(final Collection<T> collection) {
        Preconditions.checkState(collection.size() < 2, "name should be unique");
    }

}