package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private String name;

    private Set<Speaker> speakers = new HashSet<Speaker>();

    private Set<Room> rooms = new HashSet<Room>();

    private Set<Talk> talks = new HashSet<Talk>();

    public void setName(String name) {
        this.name = name;
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public String getName() {
        return name;
    }
}
