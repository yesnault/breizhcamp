package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
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

        Schedule schedule = new Schedule();
        schedule.setStart(new Date(123456789012L));
        schedule.setRoom(room);

        Talk talk = new Talk();
        talk.setTitle("Java and more");
        talk.setAbstract("What's coming in Java 9, 1O, 11");
        talk.setDuree(60);
        talk.setTheme(Theme.DECOUVRIR);

        talk.getSpeakers().add(speaker);
        speaker.getTalks().add(talk);

        talk.setSchedule(schedule);

        em.persist(speaker);
        em.persist(room);
        em.persist(schedule);
        em.persist(talk);
        em.flush();

        List<Talk> talks = scheduleService.getTalks();
        assertThat(talks).isNotNull().hasSize(1).containsExactly(talk);
    }

    @Test
    public void should_find_speaker() throws Exception {
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
    public void find_speakers() throws Exception {
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
        assertThat(result).startsWith(speakerN).contains(speakerN, speaker).isNotEmpty();
    }

    @Test
    public void find_Talks() throws Exception {
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
        assertThat(result).startsWith(talk).contains(talk, talkN).isNotEmpty();
    }

    @Test
    public void find_Talk() throws Exception {
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);
        em.flush();

        Talk result = scheduleService.getTalk(talk.getId());
        assertEquals(talk, result);
    }

    @Test
    public void getTalkByTheme() {
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

        Talk talk2 = new Talk();
        talk2.setTitle("Ruby");
        talk2.setTheme(Theme.APPROFONDIR);
        talk2.setDuree(60);

        em.persist(talk2);

        em.flush();

        List<Talk> result = scheduleService.getTalkByTheme(Theme.PRATIQUER);
        assertThat(result).startsWith(talk).contains(talk, talkN).isNotEmpty();
    }

    @Test
    public void find_Rooms() throws Exception {
        Room room = new Room();
        room.setName("Amphi");


        em.persist(room);

        Room roomN = new Room();
        roomN.setName("I50");

        em.persist(roomN);
        em.flush();

        List<Room> result = scheduleService.getRooms();
        assertThat(result).startsWith(room).contains(room, roomN).isNotEmpty();
    }

    @Test
    public void getData() throws Exception {
        SimpleDateFormat sdfDateHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Schedule schedule = new Schedule();
        schedule.setDuree(60);
        schedule.setStart(sdfDateHeure.parse("10/11/2012 10:00"));
        em.persist(schedule);

        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);
        talk.setSchedule(schedule);

        em.persist(talk);

        Talk talkN = new Talk();
        talkN.setTitle("Android");
        talkN.setTheme(Theme.PRATIQUER);
        talkN.setDuree(60);

        em.persist(talkN);

        Talk talk2 = new Talk();
        talk2.setTitle("Ruby");
        talk2.setTheme(Theme.APPROFONDIR);
        talk2.setDuree(60);
        talk2.setSchedule(schedule);

        em.persist(talk2);

        em.flush();

        Data data = scheduleService.getData();

        assertThat(data.getRooms()).isEmpty();
        assertThat(data.getCreneaux()).hasSize(1).isNotEmpty();
        List<String> creneaux = new ArrayList<String>();
        creneaux.add("10:00 - 11:00");
        assertThat(data.getCreneaux().keySet()).contains(sdfDateHeure.parse("10/11/2012 00:00")).hasSize(1).isNotEmpty();
        assertThat(data.getCreneaux().values()).contains(creneaux).hasSize(1).isNotEmpty();
        assertThat(data.getTalks()).hasSize(1).isNotEmpty();
        assertThat(data.getTalks().keySet()).contains(sdfDateHeure.parse("10/11/2012 00:00")).hasSize(1).isNotEmpty();

    }
}
