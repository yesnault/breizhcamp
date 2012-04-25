package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Event;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class IndexControllerTest extends PersistenceTestCase {

    @Autowired
    IndexController controller;


    @Test
    public void event() throws Exception {
        Room room = new Room();
        room.setName("Amphi");
        em.persist(room);

        room = new Room();
        room.setName("I50");
        em.persist(room);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        Schedule schedule1 = new Schedule();
        schedule1.setStart(sdfDate.parse("15/06/2012"));

        em.persist(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setStart(sdfDate.parse("14/06/2012"));

        em.persist(schedule2);

        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setSchedule(schedule1);
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);

        talk = new Talk();
        talk.setTitle("Android");
        talk.setSchedule(schedule2);
        talk.setTheme(Theme.DECOUVRIR);
        talk.setDuree(30);

        em.persist(talk);

        em.flush();



       Event event = controller.event();


        assertEquals(2, event.getRooms().size());

        assertEquals(2, event.getDays().size());
        assertEquals(1, event.getDays().iterator().next().getTimeSlots().size());
    }


    @Test
    public void index() throws Exception {
        Room room = new Room();
        room.setName("Amphi");
        em.persist(room);

        room = new Room();
        room.setName("I50");
        em.persist(room);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfDateH = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Schedule schedule1 = new Schedule();
        schedule1.setStart(sdfDate.parse("15/06/2012"));

        em.persist(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setStart(sdfDateH.parse("14/06/2012 10:00"));

        em.persist(schedule2);

        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setSchedule(schedule1);
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);

        talk = new Talk();
        talk.setTitle("Android");
        talk.setSchedule(schedule2);
        talk.setTheme(Theme.DECOUVRIR);
        talk.setDuree(30);

        em.persist(talk);

        em.flush();


        ModelMap model = new ModelMap();
        controller.index(model,"",false);

        assertEquals(2, ((Map) model.get("talks")).size());
        assertEquals(2, ((List) model.get("dates")).size());
    }
}
