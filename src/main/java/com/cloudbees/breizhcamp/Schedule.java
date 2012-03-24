package com.cloudbees.breizhcamp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public List<Talk> getSchedule(String room) {
        TypedQuery<Talk> q = em.createQuery("select t from Talk t where t.room = :room order by t.start", Talk.class);
        q.setParameter("room", room);
        return q.getResultList();
    }
    
    public Speaker getSpeaker(long id) {
    	return em.find(Speaker.class, id);
    }
}
