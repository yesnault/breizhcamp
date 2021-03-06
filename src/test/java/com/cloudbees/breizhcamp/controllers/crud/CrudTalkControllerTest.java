package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class CrudTalkControllerTest extends PersistenceTestCase {

    @Autowired
    CrudTalkController controller;


    @Test
    public void index() {
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

        ModelMap model = new ModelMap();
        controller.index(model);

        assertEquals(2, ((List) model.get("talks")).size());
    }


    @Test
    public void addSubmit() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();

        Schedule schedule = new Schedule();
        schedule.setDuree(60);
        schedule.setStart(new Date());
        em.persist(schedule);

        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");

        em.persist(speaker);
        em.flush();

        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }

        List<Long> speakers = new ArrayList<Long>();
        speakers.add(speaker.getId());


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Introduction a Ruby", bigDescription, 60, "DECOUVRIR", speakers, schedule.getId());

        Talk talk = em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();
        assertThat(talk).isNotNull();
    }

    @Test
    public void addSubmit_emptySchedule() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();
        em.flush();

        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Introduction a Ruby", bigDescription, 60, "DECOUVRIR", speakers, null);

        Talk talk = em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();
        assertThat(talk).isNotNull();
    }

    @Test(expected = NoResultException.class)
    public void addSubmit_emptyTitle() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();
        em.flush();

        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "", bigDescription, 60, "DECOUVRIR", speakers, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test(expected = NoResultException.class)
    public void addSubmit_emptyResume() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();
        em.flush();

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Introduction a Ruby", "", 60, "DECOUVRIR", speakers, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test(expected = NoResultException.class)
    public void addSubmit_emptyTheme() throws Exception {
        em.createQuery("delete from Talk t where t.title='Introduction a Ruby'").executeUpdate();
        em.flush();

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Introduction a Ruby", "blabl", 60, "", speakers, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test
    public void editubmit() throws Exception {
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        Schedule schedule = new Schedule();
        schedule.setDuree(60);
        schedule.setStart(new Date());

        em.persist(schedule);
        talk.setSchedule(schedule);

        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");
        speaker.getTalks().add(talk);

        talk.getSpeakers().add(speaker);
        em.persist(speaker);

        em.persist(talk);
        em.flush();


        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }

        List<Long> speakers = new ArrayList<Long>();
        speakers.add(speaker.getId());


        ModelMap model = new ModelMap();
        controller.editSubmit(model, talk.getId(), "Introduction a Ruby", bigDescription, 60, "DECOUVRIR", speakers, schedule.getId());

        talk = em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();
        assertThat(talk).isNotNull();
    }

    @Test(expected = NoResultException.class)
    public void editSubmit_emptyTitle() throws Exception {
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);
        em.flush();

        String bigDescription = "";
        for (int i = 0; i < 80; i++) {
            bigDescription += "Dans cette session vous apprendrez tout sur Ruby.";
        }


        ModelMap model = new ModelMap();
        controller.editSubmit(model, talk.getId(), "", bigDescription, 60, "DECOUVRIR", null, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test(expected = NoResultException.class)
    public void editSubmit_emptyResume() throws Exception {
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);
        em.flush();

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.editSubmit(model, talk.getId(), "Introduction a Ruby", "", 60, "DECOUVRIR", speakers, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test(expected = NoResultException.class)
    public void editSubmit_emptyTheme() throws Exception {
        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);

        em.persist(talk);
        em.flush();

        List<Long> speakers = new ArrayList<Long>();


        ModelMap model = new ModelMap();
        controller.editSubmit(model, talk.getId(), "Introduction a Ruby", "blabl", 60, "", speakers, null);

        em.createQuery("select t from Talk t where t.title='Introduction a Ruby'", Talk.class).getSingleResult();

    }

    @Test(expected = NoResultException.class)
    public void deleteTalk() {

        Speaker speaker = new Speaker();
        speaker.setFirstName("James");
        speaker.setLastName("Gosling");
        em.persist(speaker);

        Talk talk = new Talk();
        talk.setTitle("GWT");
        talk.setTheme(Theme.PRATIQUER);
        talk.setDuree(60);
        talk.getSpeakers().add(speaker);


        em.persist(talk);
        speaker.getTalks().add(talk);
        em.persist(speaker);
        em.flush();


        controller.deleteTalk(talk.getId());

        em.createQuery("select t from Talk t where t.title='GWT'", Talk.class).getSingleResult();
    }

}
