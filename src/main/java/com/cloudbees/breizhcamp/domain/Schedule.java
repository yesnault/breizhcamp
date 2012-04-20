package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author <a hef="mailto:ybonnel@gmail.com">Yan Bonnel</a>
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Schedule {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private long id;

    @ManyToOne
    private Room room;

    private Date start;

    private int duree;

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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return Long.toString(id);
    }
}
