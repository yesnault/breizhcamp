package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.ScheduleDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author <a href='mailto:ybonnel@gmail.com'>Yan Bonnel</a>
 */
@Service
@Transactional
public class CrudService {

    @Autowired
    private RoomDao roomDao;
    
    public boolean roomExist(String name) {
        try {
            roomDao.findByName(name);
            return true;
        } catch (NoResultException ignore){
            return false;
        }
    }
    
    public void addRoom(String name) {
        Room room = new Room();
        room.setName(name);
        roomDao.save(room);
    }

    @Autowired
    private SpeakerDao speakerDao;

    public boolean speakerExist(String firstName, String lastName) {
        try {
            speakerDao.findByFirstNameAndLastName(firstName, lastName);
            return true;
        } catch (NoResultException ignore){
            return false;
        }
    }

    public void addSpeaker(String firstName, String lastName, URL picture, String twitter, String description) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(firstName);
        speaker.setLastName(lastName);
        speaker.setPicture(picture);
        speaker.setTwitter(twitter);
        speaker.setDescription(description);
        speakerDao.save(speaker);
    }

    @Autowired
    private TalkDao talkDao;
    
    public void addTalk(String title, String resume, int duree, Theme theme, List<Speaker> speakers) {
        Talk talk = new Talk();
        talk.setTitle(title);
        talk.setAbstract(resume);
        // TODO gestion des schedule à ajouter.
        talk.setDuree(duree);
        talk.setTheme(theme);
        talk.getSpeakers().addAll(speakers);
        for (Speaker speaker : speakers) {
            speaker.getTalks().add(talk);
        }
        talkDao.save(talk);
    }

    @Autowired
    private ScheduleDao scheduleDao;

    public void addSchedule(Date start, Room room) {
        Schedule schedule = new Schedule();
        schedule.setRoom(room);
        schedule.setStart(start);
        scheduleDao.save(schedule);
    }

}
