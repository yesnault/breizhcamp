package com.cloudbees.breizhcamp.controllers;


import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Speaker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpeakerControllerTest extends PersistenceTestCase {

    @Autowired
    SpeakerController controller;


    @Test
      public void get() throws Exception {
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


        Speaker result = controller.get(speaker.getId());

        assertEquals(speaker,result);
    }

    @Test
    public void speakers() throws Exception {
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


        List<Speaker> result = controller.speakers();

        assertEquals(2,result.size());
    }

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
        controller.index(model,false);

        assertEquals(2,((List)model.get("speakers")).size());
    }
}
