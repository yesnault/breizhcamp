package com.cloudbees.breizhcamp.domain;

import com.google.common.base.Objects;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Talk {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @Column(name = "abstract",length = 4000)
    @JsonProperty("abstract")
    private String abstract_;

    @JsonProperty("duree")
    private int duree;

    @JsonProperty("room")
    public String getRoomName() {
        if (schedule != null && schedule.getRoom() != null) {
            return schedule.getRoom().getName();
        }
        return null;
    }

    @Nullable
    @JsonProperty("start")
    public Date getStart() {
        if (schedule != null) {
            return schedule.getStart();
        }
        return null;
    }
    
    @OneToOne
    private Schedule schedule;

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "NUMBER")
    @JsonProperty("theme")
    private Theme theme;

    @ManyToMany(mappedBy = "talks", fetch = FetchType.EAGER)
    private final Set<Speaker> speakers = new HashSet<Speaker>();

    public long getId() {
        return id;
    }

    @JsonProperty("speakers")
    public Set<Long> getSpeakersId() {
        Set<Long> speakersId = new HashSet<Long>();
        for (Speaker speaker : speakers) {
            speakersId.add(speaker.getId());
        }
        return speakersId;
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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Talk) {
            Talk other = (Talk) o;
            return Objects.equal(other.id, id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("title", title).add("theme", theme).add("schedule", schedule).add("duree", duree).toString();
    }

}
