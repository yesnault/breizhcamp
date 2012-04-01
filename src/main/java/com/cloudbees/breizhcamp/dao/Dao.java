package com.cloudbees.breizhcamp.dao;

import java.util.List;

/**
 * @autor Guernion Sylvain
 */
public interface Dao<T> {
    void save(T item);
    void delete(T item);
    List<T> findAll();
    T find(long id);
    void delete(long id);
}
