package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.net.URL;

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

    public void addSpeaker(String firstName, String lastName, URL picture) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(firstName);
        speaker.setLastName(lastName);
        speaker.setPicture(picture);
        speakerDao.save(speaker);
    }

}
