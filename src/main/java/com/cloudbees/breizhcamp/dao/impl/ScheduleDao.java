package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author Yan Bonnel
 */
@Repository("scheduleDao")
public class ScheduleDao extends AbstractDao<Schedule>  implements Dao<Schedule> {

    protected TypedQuery<Schedule> all() {
        return em.createQuery("select s from Schedule s order by s.start, s.room", Schedule.class);
    }

    public Schedule find(long id) {
        return em.find(Schedule.class, id);
    }
    
    public boolean exists(Room room, Date startDate) {
        try {
            em.createQuery("select s from Schedule s where (s.room = :room or s.room is null) and s.start = :start", Schedule.class).setParameter("room", room).setParameter("start", startDate).getSingleResult();
            return true;
        } catch (NoResultException noResultException) {
            return false;
        }
    }

    public List<Schedule> findAllNotUsed() {
        return em.createQuery("select s from Schedule s where not exists (select t from Talk t where t.schedule = s)", Schedule.class).getResultList();
    }

}
