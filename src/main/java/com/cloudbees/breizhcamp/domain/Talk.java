package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Talk {
    
    @Id
    @GeneratedValue
    private long id;

    private String title;

    @Column(name = "abstract")
    @JsonProperty("abstract")
    private String abstract_;
    
    private Date start;

    private Date end;

    private String room;

    private Theme theme;

    @ManyToMany(mappedBy = "talks")
    private Set<Speaker> speakers = new HashSet<Speaker>();

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
    public String toString() {
        return "Talk{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abstract_='" + abstract_ + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", room='" + room + '\'' +
                ", theme=" + theme +
                '}';
    }
}
