package com.cloudbees.breizhcamp.controllers.crud;


import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Talk;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CrudRoomControllerTest extends PersistenceTestCase {

    @Autowired
    CrudRoomController controller;

    @Test
    public void index() {
        Room room = new Room();
        room.setName("RoomExist");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        controller.index(model);

        assertEquals(1, ((List) model.get("rooms")).size());
    }

    @Test
    public void addSubmit() {


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Amphi");

        Room room = em.createQuery("select r from Room r where r.name='Amphi'", Room.class).getSingleResult();
        assertThat(room).isNotNull();
    }

    @Test
    public void addSubmit_roomExist() {
        Room room = new Room();
        room.setName("Amphi");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, "Amphi");

        assertEquals("crud.room.add", result);
        assertEquals(true, model.get("error"));
    }


    @Test
    public void addSubmit_empty() {
        Room room = new Room();
        room.setName("Amphi");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, "");

        assertEquals("crud.room.add", result);
        assertEquals(true, model.get("error"));
    }

    @Test
    public void editSubmit() {
        Room room = new Room();
        room.setName("Amphi");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        controller.addSubmit(model, room.getId(), "I50");

        room = em.createQuery("select r from Room r where r.name='I50'", Room.class).getSingleResult();
        assertThat(room).isNotNull();
    }

    @Test
    public void editSubmit_exist() {
        Room room1 = new Room();
        room1.setName("Amphi");

        em.persist(room1);
        Room room = new Room();
        room.setName("I50");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, room1.getId(), "I50");

        assertEquals("crud.room.edit", result);
        assertEquals(true, model.get("error"));
    }

    @Test
    public void editSubmit_empty() {
        Room room1 = new Room();
        room1.setName("Amphi");

        em.persist(room1);
        Room room = new Room();
        room.setName("I50");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, room1.getId(), "");

        assertEquals("crud.room.edit", result);
        assertEquals(true, model.get("error"));
    }

    @Test(expected = NoResultException.class)
    public void deleteRoom() {
        Room room = new Room();
        room.setName("I50");

        em.persist(room);
        em.flush();

        ModelMap model = new ModelMap();
        controller.deleteRoom(model, room.getId());

        em.createQuery("select r from Room r where r.name='I50'", Room.class).getSingleResult();
    }
}
