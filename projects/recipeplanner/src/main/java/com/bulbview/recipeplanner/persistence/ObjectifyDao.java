package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bulbview.recipeplanner.datamodel.Entity;
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

    public T get(final String name) {
        final Objectify objectify = beginObjectify();
        return objectify.get(entityClass, name);
    }

    public Collection<T> get(final String filter,
                             final Object value) {
        final Objectify objectify = beginObjectify();
        return objectify.query(entityClass).filter(filter, value).list();
    }

    public Collection<T> getAll() {
        final Objectify objectify = beginObjectify();
        final Query<T> query = objectify.query(entityClass);
        return query.list();
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

    private Objectify beginObjectify() {
        return objectifyFactory.begin();
    }

}