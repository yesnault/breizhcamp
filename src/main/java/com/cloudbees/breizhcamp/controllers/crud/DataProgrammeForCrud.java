package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Talk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe DataProgrammeForCrud.
 */
public class DataProgrammeForCrud {
    private Map<Date, List<String>> creneaux = new HashMap<Date, List<String>>();
    private Map<Date, Map<String, Map<String, Schedule>>> schedules = new HashMap<Date, Map<String, Map<String, Schedule>>>();
    private List<Date> datesOrdonnees = new ArrayList<Date>();
    private List<Room> rooms = null;

    private Map<Schedule, Talk> talksBySchedules = null;
    private Map<Integer, List<Talk>> talksNotScheduled = null;

    public Map<Date, List<String>> getCreneaux() {
        return creneaux;
    }

    public Map<Date, Map<String, Map<String, Schedule>>> getSchedules() {
        return schedules;
    }

    public List<Date> getDatesOrdonnees() {
        return datesOrdonnees;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Map<Schedule, Talk> getTalksBySchedules() {
        return talksBySchedules;
    }

    public void setTalksBySchedules(Map<Schedule, Talk> talksBySchedules) {
        this.talksBySchedules = talksBySchedules;
    }

    public void setTalksNotScheduled(Map<Integer, List<Talk>> talksNotScheduled) {
        this.talksNotScheduled = talksNotScheduled;
    }

    public Map<Integer, List<Talk>> getTalksNotScheduled() {
        return talksNotScheduled;
    }
}
