package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.controllers.crud.DataProgrammeForCrud;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.fest.assertions.MapAssert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        assertThat(data.getCreneaux().keySet()).contains(sdfDateHeure.parse("10/11/2012 00:00")).hasSize(1)
                .isNotEmpty();
        assertThat(data.getCreneaux().values()).contains(creneaux).hasSize(1).isNotEmpty();
        assertThat(data.getTalks()).hasSize(1).isNotEmpty();
        assertThat(data.getTalks().keySet()).contains(sdfDateHeure.parse("10/11/2012 00:00")).hasSize(1).isNotEmpty();

    }

    @Test
    public void getDataForCrud_should_return_dates_of_schedules() throws ParseException {
        SimpleDateFormat sdfDateHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Schedule schedule1 = new Schedule();
        schedule1.setDuree(60);
        schedule1.setStart(sdfDateHeure.parse("10/11/2012 10:00"));
        em.persist(schedule1);
        Schedule schedule2 = new Schedule();
        schedule2.setDuree(60);
        schedule2.setStart(sdfDateHeure.parse("10/11/2012 16:00"));
        em.persist(schedule2);
        Schedule schedule3 = new Schedule();
        schedule3.setDuree(60);
        schedule3.setStart(sdfDateHeure.parse("12/11/2012 16:00"));
        em.persist(schedule3);
        em.flush();

        // 3 schedules sur 2 jours, on doit donc avoir 2 dates dans le retour de la méthode.

        DataProgrammeForCrud data = scheduleService.getDataForCrud();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        Date date1Expected = sdfDate.parse("10/11/2012");
        Date date2Expected = sdfDate.parse("12/11/2012");

        assertThat(data.getDatesOrdonnees()).hasSize(2);

        assertThat(data.getDatesOrdonnees()).containsExactly(date1Expected, date2Expected);

    }

    @Test
    public void getTalksBySchedule_should_return_talks_classified_by_schedule() throws ParseException {
        SimpleDateFormat sdfDateHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Schedule schedule1 = new Schedule();
        schedule1.setDuree(60);
        schedule1.setStart(sdfDateHeure.parse("10/11/2012 10:00"));
        em.persist(schedule1);
        Schedule schedule2 = new Schedule();
        schedule2.setDuree(60);
        schedule2.setStart(sdfDateHeure.parse("10/11/2012 16:00"));
        em.persist(schedule2);

        Talk talk1 = new Talk();
        talk1.setTitle("Titre1");
        talk1.setSchedule(schedule1);
        talk1.setDuree(1);
        em.persist(talk1);

        Talk talk2 = new Talk();
        talk2.setTitle("Titre2");
        talk2.setSchedule(schedule2);
        talk2.setDuree(2);
        em.persist(talk2);

        Talk talk3 = new Talk();
        talk3.setTitle("Titre3");
        talk3.setSchedule(null);
        talk3.setDuree(3);
        em.persist(talk3);

        Talk talk4 = new Talk();
        talk4.setTitle("Titre4");
        talk4.setSchedule(null);
        talk4.setDuree(3);
        em.persist(talk4);

        em.flush();

        Map<Schedule, Talk> result = scheduleService.getTalksBySchedule();

        assertThat(result).includes(MapAssert.entry(schedule1, talk1), MapAssert.entry(schedule2, talk2));
        assertThat(result).hasSize(2);

        // Test de TalksBotScheduled avec le même jeu de donnée.
        Map<Integer, List<Talk>> resultNotScheduled = scheduleService.getTalksNotScheduled();

        assertThat(resultNotScheduled).hasSize(1);
        assertThat(resultNotScheduled.get(3)).hasSize(2);
        assertThat(resultNotScheduled.get(3)).contains(talk3, talk4);
    }

    @Test
    public void getDureeBySchedule_shoult_return_duree_of_schedules() {
        Schedule schedule1 = new Schedule();
        schedule1.setDuree(1);

        em.persist(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setDuree(2);

        em.persist(schedule2);

        em.flush();

        Map<Long, Integer> result = scheduleService.getDureeBySchedule();

        assertThat(result).hasSize(2);
        assertThat(result).includes(MapAssert.entry(schedule1.getId(), schedule1.getDuree()),
                MapAssert.entry(schedule2.getId(), schedule2.getDuree()));
    }

    @Test
    public void associateScheduleAndTalk_should_associate_schedule_and_talk() {
        Schedule schedule = new Schedule();
        schedule.setDuree(3);

        em.persist(schedule);

        Talk talk = new Talk();
        talk.setTitle("Titre");

        em.persist(talk);

        em.flush();

        scheduleService.associateScheduleAndTalk(schedule.getId(), talk.getId());

        talk = em.find(Talk.class, talk.getId());

        assertThat(talk.getSchedule()).isSameAs(schedule);
    }

    @Test
    public void associateScheduleAndTalk_should_delete_schedule_of_talk_if_idTalk_negative() {
        Schedule schedule = new Schedule();
        schedule.setDuree(3);

        em.persist(schedule);

        Talk talk = new Talk();
        talk.setTitle("Titre");
        talk.setSchedule(schedule);

        em.persist(talk);

        em.flush();

        scheduleService.associateScheduleAndTalk(schedule.getId(), -1);

        talk = em.find(Talk.class, talk.getId());

        assertThat(talk.getSchedule()).isNull();
    }
}
