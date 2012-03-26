package com.cloudbees.breizhcamp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.cloudbees.breizhcamp.domain.Room;
import org.springframework.stereotype.Service;

import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Service
public class Schedule {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Talk> getSchedule() {
        TypedQuery<Talk> q = em.createQuery("select t from Talk t order by t.start, t.room", Talk.class);
        return q.getResultList();
    }

    public List<Speaker> getSpeakers() {
        TypedQuery<Speaker> q = em.createQuery("select s from Speaker s order by s.lastName, s.firstName", Speaker.class);
        return q.getResultList();
    }
    
    public Speaker getSpeaker(long id) {
    	return em.find(Speaker.class, id);
    }

    public Talk getTalk(long id) {
        return em.find(Talk.class, id);
    }
}
