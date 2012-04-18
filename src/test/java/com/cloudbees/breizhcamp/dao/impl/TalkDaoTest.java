package com.cloudbees.breizhcamp.dao.impl;


import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TalkDaoTest extends PersistenceTestCase {

    @Autowired
    TalkDao dao;

    @Test
    public void findByTheme() {

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

        List<Talk> result = dao.findByTheme(Theme.PRATIQUER);
        assertThat(result).startsWith(talk).contains(talk, talkN).isNotEmpty();
    }

    @Test
    public void findBySchedule() {

        Schedule schedule = new Schedule();
        schedule.setDuree(60);
        schedule.setStart(new Date());
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

        List<Talk> result = dao.findBySchedule(schedule);
        assertThat(result).startsWith(talk).contains(talk, talk2).isNotEmpty();
    }
}

