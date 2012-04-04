package com.cloudbees.breizhcamp.domain;


import java.util.HashSet;
import java.util.Set;

public class Day {
    
    public String jour;

    Set<TimeSlot> timeSlots = new HashSet<TimeSlot>();

    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
