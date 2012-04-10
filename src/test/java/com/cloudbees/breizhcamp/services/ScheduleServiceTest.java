package com.cloudbees.breizhcamp.services;

import static junit.framework.Assert.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.cloudbees.breizhcamp.domain.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudbees.breizhcamp.PersistenceTestCase;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class ScheduleServiceTest extends PersistenceTestCase {

    @Autowired
    private ScheduleService scheduleService;
    

    @Test
    public void should_get_schedule_by_room() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");
        speaker.setPicture(new URL("http://upload.wikimedia.org/wikipedia/commons/0/00/James_Gosling_2005.jpg"));

        Room room = new Room();
        room.setName("Amphi");
        
        Talk talk = new Talk();
        talk.setTitle("Java and more");
        talk.setAbstract("What's coming in Java 9, 1O, 11");
        talk.setSchedule(new Schedule());
        talk.getSchedule().setStart(new Date(123456789012L));
        talk.setDuree(60);
        talk.setTheme(Theme.DECOUVRIR);

        talk.getSpeakers().add(speaker);
        talk.getSchedule().setRoom(room);
        speaker.getTalks().add(talk);

        em.persist(speaker);
        em.persist(room);
        em.persist(talk);
        em.flush();

        List<Talk> talks = scheduleService.getSchedule();
        assertThat(talks).isNotNull().hasSize(1).containsExactly(talk);
    }
    
    @Test
    public void should_find_speaker() throws Exception{
        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");
        speaker.setPicture(new URL("http://upload.wikimedia.org/wikipedia/commons/0/00/James_Gosling_2005.jpg"));
        
        em.persist(speaker);
        em.flush();
        
        Speaker result = scheduleService.getSpeaker(speaker.getId());
        assertThat(result).isNotNull().isEqualTo(speaker);
    }

    @Test
    public void find_speakers() throws Exception{
        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");
        speaker.setPicture(new URL("http://upload.wikimedia.org/wikipedia/commons/0/00/James_Gosling_2005.jpg"));

        em.persist(speaker);

        Speaker speakerN = new Speaker();
        speakerN.setFirstName("Nicolas");
        speakerN.setLastName("Deloof");

        em.persist(speakerN);
        em.flush();

        List<Speaker> result = scheduleService.getSpeakers();
        assertThat(result).startsWith(speakerN).contains(speakerN,speaker).isNotEmpty();
    }

    @Test
    public void find_Talks() throws Exception{
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);

        Talk talkN = new Talk();
        talkN.setTitle("Android");
        talkN.setTheme(Theme.PRATIQUER);
        talkN.setDuree(60);

        em.persist(talkN);
        em.flush();

        List<Talk> result = scheduleService.getTalks();
        assertThat(result).startsWith(talk).contains(talk,talkN).isNotEmpty();
    }

    @Test
    public void find_Talk() throws Exception{
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);
        em.flush();

        Talk result = scheduleService.getTalk(talk.getId());
        assertEquals(talk,result);
    }


    @Test
    public void find_Rooms() throws Exception{
        Room room = new Room();
        room.setName("Amphi");


        em.persist(room);

        Room roomN = new Room();
        roomN.setName("I50");

        em.persist(roomN);
        em.flush();

        List<Room> result = scheduleService.getRooms();
        assertThat(result).startsWith(room).contains(room,roomN).isNotEmpty();
    }
}
