package com.cloudbees.breizhcamp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author <a hef="mailto:ybonnel@gmail.com">Yan Bonnel</a>
 */
@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Room room;

    private Date start;

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Date getStart() {
        return start;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
