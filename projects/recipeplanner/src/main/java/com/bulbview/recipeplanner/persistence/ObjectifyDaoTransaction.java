package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

public class ObjectifyDaoTransaction<T extends Entity> implements EntityDao<T> {

    private final ObjectifyDao<T>  dao;

    private final ObjectifyFactory objectifyFactory;

    public ObjectifyDaoTransaction(final ObjectifyDao<T> dao) {
        this.dao = dao;
        this.objectifyFactory = dao.getObjectifyFactory();
    }

    @Override
    public T get(final Key<T> key) {
        // TODO Auto-generated method stub
        return dao.get(key);
    }

    public Collection<T> get(final String filter,
                             final Object entity) {
        return dao.get(filter, entity);
    }

    @Override
    public Collection<T> getAll() {
        return dao.getAll();
    }

    @Override
    public T getByName(final String name) {
        return dao.getByName(name);
    }

    @Override
    public T save(final T entity) throws DaoException {
        T savedEntity;
        final Transaction transaction = beginTransaction();
        try {
            savedEntity = dao.save(entity);
            transaction.commit();
        } catch (final DaoException e) {
            transaction.rollback();
            throw e;
        }
        return savedEntity;
    }

    private Transaction beginTransaction() {
        final Objectify transactionObjectify = objectifyFactory.beginTransaction();
        final Transaction transaction = transactionObjectify.getTxn();
        dao.setObjectify(transactionObjectify);
        return transaction;
    }
}
