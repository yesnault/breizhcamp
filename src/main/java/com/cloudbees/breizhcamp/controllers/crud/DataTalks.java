package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Talk;

import java.util.List;
import java.util.Map;

/**
 * Classe DataTalks.
 */
public class DataTalks {

    private Map<Schedule, Talk> talksBySchedules = null;
    private Map<Integer, List<Talk>> talksNotScheduled = null;
    private Map<Long, Integer> dureeBySchedule = null;

    public Map<Schedule, Talk> getTalksBySchedules() {
        return talksBySchedules;
    }

    public void setTalksBySchedules(Map<Schedule, Talk> talksBySchedules) {
        this.talksBySchedules = talksBySchedules;
    }

    public Map<Integer, List<Talk>> getTalksNotScheduled() {
        return talksNotScheduled;
    }

    public void setTalksNotScheduled(Map<Integer, List<Talk>> talksNotScheduled) {
        this.talksNotScheduled = talksNotScheduled;
    }

    public Map<Long, Integer> getDureeBySchedule() {
        return dureeBySchedule;
    }

    public void setDureeBySchedule(Map<Long, Integer> dureeBySchedule) {
        this.dureeBySchedule = dureeBySchedule;
    }
}
