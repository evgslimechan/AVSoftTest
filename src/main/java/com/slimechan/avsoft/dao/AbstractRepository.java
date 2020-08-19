package com.slimechan.avsoft.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T, ID extends Serializable> {

    private Class<ID> idClass;
    private Class<T> entityClass;

    protected AbstractRepository(Class<ID> idClass, Class<T> entityClass) {
        this.idClass = idClass;
        this.entityClass = entityClass;
    }

    public Class<ID> getIdClass() {
        return idClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public abstract Optional<T> find(ID id);

    public abstract List<T> findByExample(T example);

    public abstract List<T> findAll();

    public abstract T save(T entity);

    public abstract Iterable<T> save(Iterable<T> entities);

    public abstract T delete(ID id);

    public abstract T delete(T entity);

    public abstract Iterable<T> delete(Iterable<T> entities);


}
