package com.cloudbees.breizhcamp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.common.base.Objects;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Entity
public class Talk {
    
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(name = "abstract")
    private String abstract_;
    
    private Date start;

    private Date end;

    private String room;

    private Theme theme;

    @ManyToMany(mappedBy = "talks")
    private final Set<Speaker> speakers = new HashSet<Speaker>();

    public long getId() {
        return id;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public String getAbstract() {
        return abstract_;
    }

    public void setAbstract(String abstract_) {
        this.abstract_ = abstract_;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Talk) {
            Talk other=(Talk)o;
            // Un talk par salle à un moment donné
            return Objects.equal(other.room, room)
            		&& Objects.equal(other.start, start)
            		&& Objects.equal(other.end, end);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
    	return Objects.hashCode(room, start, end);
    }
    
    @Override
    public String toString() {
    	return Objects.toStringHelper(this).add("id", id).add("title", title).add("theme", theme).add("room", room).add("start", start).add("end", end).toString();
    }
    
}
