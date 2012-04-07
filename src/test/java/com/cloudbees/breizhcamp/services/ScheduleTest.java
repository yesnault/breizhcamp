package com.cloudbees.breizhcamp.services;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.cloudbees.breizhcamp.domain.Room;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class ScheduleTest extends PersistenceTestCase {

    @Autowired
    private Schedule schedule;
    

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
        talk.setStart(new Date(123456789012L));
        talk.setDuree(60);
        talk.setTheme(Theme.DECOUVRIR);

        talk.getSpeakers().add(speaker);
        talk.setRoom(room);
        speaker.getTalks().add(talk);

        em.persist(speaker);
        em.persist(room);
        em.persist(talk);
        em.flush();

        List<Talk> talks = schedule.getSchedule();
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
        
        Speaker result = schedule.getSpeaker(speaker.getId());
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

        List<Speaker> result = schedule.getSpeakers();
        assertThat(result).startsWith(speakerN).contains(speakerN,speaker).isNotEmpty();
    }
}
