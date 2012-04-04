package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private String name;

    private Set<Speaker> speakers = new HashSet<Speaker>();

    private Set<Room> rooms = new HashSet<Room>();

    private Set<Day> days = new HashSet<Day>();

    public void setName(String name) {
        this.name = name;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<Day> getDays() {
        return days;
    }

    public String getName() {
        return name;
    }
}
