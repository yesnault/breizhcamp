package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Service
public class Schedule {

    @Autowired
    private TalkDao talkDao;

    @Autowired
    private SpeakerDao speakerDao;

    @Autowired
    private RoomDao roomDao;

    public List<Talk> getSchedule() {
        return talkDao.findAll();
    }

    public List<Speaker> getSpeakers() {
        return speakerDao.findAll();
    }

    public List<Talk> getTalks() {
        return talkDao.findAll();
    }

    public List<Room> getRooms() {
        return roomDao.findAll();
    }


    public Speaker getSpeaker(long id) {
        return speakerDao.find(id);
    }

    public Talk getTalk(long id) {
        return talkDao.find(id);
    }

    public List<Talk> getTalkByTheme(Theme theme) {
        return talkDao.findByTheme(theme);
    }
}
