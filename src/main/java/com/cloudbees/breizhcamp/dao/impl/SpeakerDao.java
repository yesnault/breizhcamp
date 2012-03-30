package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @autor Guernion Sylvain
 */
@Repository("speakerDao")
public class SpeakerDao extends AbstractDao<Speaker>  implements Dao<Speaker> {

    protected TypedQuery<Speaker> all() {
        return em.createQuery("select s from Speaker s order by s.lastName, s.firstName", Speaker.class);
    }

    public Speaker find(long id) {
        return em.find(Speaker.class, id);
    }
}
