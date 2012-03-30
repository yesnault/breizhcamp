package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.hibernate.Criteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.net.URL;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author <a hef="mailto:ybonnel@gmail.com">Yan Bonnel</a>
 */
public class CrudServiceTest extends PersistenceTestCase {

    @Autowired
    private CrudService service;

    @Test
    public void roomExistsTest() throws Exception {
        Room room = new Room();
        room.setName("RoomExist");
        
        em.persist(room);
        em.flush();

        assertThat(service.roomExist("innexistant")).isFalse();
        assertThat(service.roomExist("RoomExist")).isTrue();
    }
    
    @Test
    public void canAddRoom() throws Exception {
        em.createQuery("delete from Room r where r.name='maRoom'").executeUpdate();
        em.flush();

        service.addRoom("maRoom");

        Room room = em.createQuery("select r from Room r where r.name='maRoom'", Room.class).getSingleResult();
        assertThat(room).isNotNull();
    }
}
