package com.cloudbees.breizhcamp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Service
public class Schedule {

    @Autowired
    private TalkDao talkDao;

    @Autowired
    private SpeakerDao speakerDao;
    
    public List<Talk> getSchedule() {
        return talkDao.findAll();
    }

    public List<Speaker> getSpeakers() {
        return speakerDao.findAll();
    }
    
    public Speaker getSpeaker(long id) {
    	return speakerDao.find(id);
    }

    public Talk getTalk(long id) {
        return talkDao.find(id);
    }
}
