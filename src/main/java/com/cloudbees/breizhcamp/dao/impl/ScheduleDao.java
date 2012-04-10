package com.cloudbees.breizhcamp.dao.impl;

import com.cloudbees.breizhcamp.dao.Dao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

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

}
