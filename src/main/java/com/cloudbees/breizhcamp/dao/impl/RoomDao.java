package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @autor Guernion Sylvain
 */
@Repository("roomDao")
public class RoomDao extends AbstractDao<Room>  implements Dao<Room> {

    protected TypedQuery<Room> all() {
        return em.createQuery("select r from Room r order by r.name", Room.class);
    }

    public Room find(long id) {
        return em.find(Room.class, id);
    }
    
    public Room findByName(String name) {
        TypedQuery<Room> query = em.createQuery("select r from Room r where r.name = :name", Room.class);
        return query.setParameter("name", name).getSingleResult();
    }

}
