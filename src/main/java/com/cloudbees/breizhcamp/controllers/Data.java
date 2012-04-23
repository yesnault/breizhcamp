package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Talk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Classe Data.
*/
public class Data {
    private Map<Date, List<String>> creneaux = new HashMap<Date, List<String>>();
    private Map<Date, Map<String, Map<String, Talk>>> talks = new HashMap<Date, Map<String, Map<String, Talk>>>();
    private Map<Date, List<Talk>> newTalks = new HashMap<Date, List<Talk>>();
    private List<Date> datesOrdonnees = new ArrayList<Date>();
    private List<Room> rooms = null;
    private Map<Date, Borne> bornes = new HashMap<Date, Borne>();

    public static class Borne {
        private int min;
        private int max;

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public Borne(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public Map<Date, List<String>> getCreneaux() {
        return creneaux;
    }

    public Map<Date, Map<String, Map<String, Talk>>> getTalks() {
        return talks;
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

    public Map<Date, List<Talk>> getNewTalks() {
        return newTalks;
    }

    public Map<Date, Borne> getBornes() {
        return bornes;
    }
}
