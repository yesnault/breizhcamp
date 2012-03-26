package com.cloudbees.breizhcamp.dao;

import com.cloudbees.breizhcamp.domain.Room;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @autor Guernion Sylvain
 */
public interface Dao<T> {
    void save(T item);
    void delete(T item);
    List<T> findAll();
    T find(long id);
}
