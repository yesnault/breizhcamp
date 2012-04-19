package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import java.net.URL;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class CrudSpeakersControllerTest extends PersistenceTestCase {

    @Autowired
    CrudSpeakersController controller;

    @Test
    public void index() throws Exception {
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

        ModelMap model = new ModelMap();
        controller.index(model, false);

        assertEquals(2, ((List) model.get("speakers")).size());
    }

    @Test
    public void addSubmit() throws Exception {
        em.createQuery("delete from Speaker s where s.firstName='Bernard'").executeUpdate();
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, "Emmanuel", "Bernard", null, "emmanuelbernard", "", true);


        assertEquals("redirect:/crud/speaker/index.htm?hide=true", result);
        Speaker speaker = em.createQuery("select s from Speaker s where s.firstName='Bernard'", Speaker.class).getSingleResult();
        assertThat(speaker).isNotNull();
        assertThat(speaker.getTwitter()).isEqualTo("emmanuelbernard");
        assertThat(speaker.getFirstName()).isEqualTo("Bernard");
        assertThat(speaker.getLastName()).isEqualTo("Emmanuel");
        assertThat(speaker.getDescription()).isEqualTo("");
    }

    @Test(expected = NoResultException.class)
    public void addSubmit_emptyFirstName() throws Exception {
        em.createQuery("delete from Speaker s where s.firstName='Emmanuel'").executeUpdate();
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, "", "Emmanuel", null, "", "emmanuelbernard", false);


        assertEquals("crud.speaker.add", result);
        em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
    }

    @Test(expected = NoResultException.class)
    public void addSubmit_emptyLastName() throws Exception {
        em.createQuery("delete from Speaker s where s.firstName='Emmanuel'").executeUpdate();
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, "Bernard", "", null, "", "emmanuelbernard", false);


        assertEquals("crud.speaker.add", result);
        em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
    }

    @Test
    public void addSubmit_emptyExist() throws Exception {
        em.createQuery("delete from Speaker s where s.firstName='Emmanuel'").executeUpdate();
        em.flush();

        ModelMap model = new ModelMap();
        controller.addSubmit(model, "Bernard", "Emmanuel", null, "", "emmanuelbernard", false);

        String result = controller.addSubmit(model, "Bernard", "Emmanuel", null, "", "emmanuelbernard", false);
        assertEquals("crud.speaker.add", result);

    }

    @Test
    public void editSubmit() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Sylvain");
        speaker.setLastName("Guernion");

        em.persist(speaker);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, speaker.getId(), "Guernion", "Sylvain", null, "http://api.twitter.com/1/users/profile_image/sguernion", "sguernion");


        assertEquals("redirect:/crud/speaker/index.htm", result);
        speaker = em.createQuery("select s from Speaker s where s.firstName='Sylvain'", Speaker.class).getSingleResult();
        assertThat(speaker).isNotNull();
    }

    @Test
    public void editSubmit_emptyFirstName() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Emmanuel");
        speaker.setLastName("Bernard");

        em.persist(speaker);
        em.flush();
        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, speaker.getId(), "", "Emmanuel", null, "", "emmanuelbernard");


        assertEquals("crud.speaker.edit", result);
        em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
    }

    @Test
    public void editSubmit_emptyLastName() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Emmanuel");
        speaker.setLastName("Bernard");

        em.persist(speaker);
        em.flush();

        ModelMap model = new ModelMap();
        String result = controller.addSubmit(model, speaker.getId(), "Bernard", "", null, "", "emmanuelbernard");


        assertEquals("crud.speaker.edit", result);
        em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
    }

    @Test
    public void editSubmit_emptyExist() throws Exception {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Emmanuel");
        speaker.setLastName("Bernard");

        em.persist(speaker);
        em.flush();

        ModelMap model = new ModelMap();
        controller.addSubmit(model, speaker.getId(), "Bernard", "Emmanuel", null, "", "emmanuelbernard");

        String result = controller.addSubmit(model, "Bernard", "Emmanuel", null, "", "emmanuelbernard", false);
        assertEquals("crud.speaker.add", result);

    }

    @Test(expected = NoResultException.class)
    public void deleteSpeaker() {
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


        ModelMap model = new ModelMap();
        controller.deleteSpeaker(model, speaker.getId());

        em.createQuery("select s from Speaker s where s.firstName='Emmanuel'", Speaker.class).getSingleResult();
    }
}
