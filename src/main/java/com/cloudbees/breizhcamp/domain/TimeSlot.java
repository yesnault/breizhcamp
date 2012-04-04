package com.cloudbees.breizhcamp.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class TimeSlot implements  Comparable<TimeSlot>{


    @JsonProperty("date")
    public String date;

    @JsonProperty("name")
    public String name;

    public Set<Talk> sessions = new HashSet<Talk>();


    public int compareTo(TimeSlot o) {
        int result = date.compareTo(o.date);
        if(result == 0){
            result = name.compareTo(o.name);
        }
        return result;
    }

}
