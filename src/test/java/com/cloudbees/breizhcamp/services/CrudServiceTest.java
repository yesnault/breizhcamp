package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
    public void speakerExist() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Emmanuel");
        speaker.setLastName("Bernard");

        em.persist(speaker);
        em.flush();

        assertThat(service.speakerExist("Nicolas", "Ledez")).isFalse();
        assertThat(service.speakerExist("Emmanuel", "Bernard")).isTrue();

    }

    @Test
    public void canAddRoom() throws Exception {
        em.createQuery("delete from Room r where r.name='maRoom'").executeUpdate();
        em.flush();

        service.addRoom("maRoom");

        Room room = em.createQuery("select r from Room r where r.name='maRoom'", Room.class).getSingleResult();
        assertThat(room).isNotNull();
    }


    @Test
    public void canAddSpeaker() throws Exception {
        em.createQuery("delete from Speaker s where s.firstName='Emmanuel'").executeUpdate();
        em.flush();

        service.addSpeaker("Emmanuel", "Bernard", null, "", "emmanuelbernard");

        Speaker speaker = em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
        assertThat(speaker).isNotNull();
    }


    @Test
    public void canAddTalk() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();
        em.flush();

        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }

        List<Speaker> speakers = new ArrayList<Speaker>();
        speakers.add(new Speaker());

        service.addTalk("Introduction a Ruby", bigDescription, 60, Theme.DECOUVRIR, speakers);

        Talk talk = em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();
        assertThat(talk).isNotNull();
    }
    
    @Test
    public void canAddSchedule() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.flush();
        
        Room room = new Room();
        room.setName("Une Salle");
        
        em.persist(room);
        em.flush();
        
        Date date = new Date();
        
        service.addSchedule(date, room);

        Schedule schedule = em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();
        assertThat(schedule).isNotNull();
    }
}
