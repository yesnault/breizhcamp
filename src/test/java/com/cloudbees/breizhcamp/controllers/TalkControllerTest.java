package com.cloudbees.breizhcamp.controllers;


import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class TalkControllerTest extends PersistenceTestCase {

    @Autowired
    TalkController controller;

    @Test
      public void talks(){

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

        List<Talk> result = controller.talks();
        assertThat(result).startsWith(talk).contains(talk,talkN).isNotEmpty();
    }

    @Test
    public void talk(){

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
        controller.talk(model,talk.getId(),false);
        assertEquals(talk,model.get("talk"));
    }

    @Test
    public void get(){

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


        Talk result = controller.get(talk.getId());
        assertEquals(talk,result);
    }
}

