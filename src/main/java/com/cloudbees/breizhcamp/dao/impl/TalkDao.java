package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @autor Guernion Sylvain
 */
@Repository("talkDao")
public class TalkDao extends AbstractDao<Talk> implements Dao<Talk> {

    protected TypedQuery<Talk> all() {
        return em.createQuery("select t from Talk t order by t.start, t.room", Talk.class);
    }

    public Talk find(long id) {
        return em.find(Talk.class, id);
    }

    public List<Talk> findByRoom(Room room) {
        return em.createQuery("select t from Talk t where t.room = :room", Talk.class).setParameter("room", room)
                .getResultList();
    }

    public List<Talk> findByTheme(Theme theme) {
        return em.createQuery("select t from Talk t where t.theme = :theme", Talk.class).setParameter("theme", theme)
                .getResultList();
    }
}
