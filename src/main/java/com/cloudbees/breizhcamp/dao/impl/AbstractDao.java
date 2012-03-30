package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @autor Guernion Sylvain
 */
public abstract class AbstractDao<T> implements Dao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected abstract TypedQuery<T> all();

    public List<T> findAll() {
        return all().getResultList();
    }

    public void save(T item) {
        em.persist(item);
    }

    public void delete(T item) {
        em.remove(item);
    }
}
