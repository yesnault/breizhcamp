package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.domain.Day;
import com.cloudbees.breizhcamp.domain.Event;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.TimeSlot;
import com.cloudbees.breizhcamp.services.Schedule;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Handle request for index page
 *
 * @author Alexandre THOMAZO <alex@thomazo.info>
 */
@Controller
public class IndexController {

    @Autowired
    private Schedule schedule;

    @RequestMapping("/index.htm")
    public String index(ModelMap model, @RequestParam(defaultValue = "Amphi") String room,
                        @RequestParam(defaultValue = "false") boolean hide) {
        model.put("hide", hide);
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        Set<Date> dates = new HashSet<Date>();
        Map<Date, List<String>> creneaux = new HashMap<Date, List<String>>();
        Map<Date, Map<String, Map<String, Talk>>> talks = new HashMap<Date, Map<String, Map<String, Talk>>>();
        for (Talk talk : schedule.getTalks()) {

            String roomOfTalk = talk.getRoom() == null ? "sansRoom" : talk.getRoom().getName();
            Date date = DateUtils.truncate(talk.getStart(), Calendar.DATE);
            dates.add(date);
            if (!creneaux.containsKey(date)) {
                creneaux.put(date, new ArrayList<String>());
                talks.put(date, new HashMap<String, Map<String, Talk>>());
            }
            String creneau = sdfHeure.format(talk.getStart()) + " - " + sdfHeure.format(talk.getEnd());
            if (!creneaux.get(date).contains(creneau)) {
                creneaux.get(date).add(creneau);
            }
            if (!talks.get(date).containsKey(creneau)) {
                talks.get(date).put(creneau, new HashMap<String, Talk>());
            }
            talks.get(date).get(creneau).put(roomOfTalk, talk);
        }
        List<Date> datesOrdonnees = new ArrayList<Date>(dates);
        Collections.sort(datesOrdonnees);

        for (List<String> creneauxForDate : creneaux.values()) {
            Collections.sort(creneauxForDate);
        }
        List<Room> rooms = schedule.getRooms();
        model.put("dates", datesOrdonnees);
        model.put("rooms", rooms);
        model.put("creneaux", creneaux);
        model.put("talks", talks);
        model.put("sansRoom", "sansRoom");
        return "index";
    }

    @RequestMapping("/contact.htm")
    public String contact(ModelMap model) {
       return "contact";
    }

    @RequestMapping(value = "/event.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Event event() {
        Event event = new Event();
        event.setName("BreizhCamp - 14-15 Juin 2012");
        event.getSpeakers().addAll(schedule.getSpeakers());
        event.getRooms().addAll(schedule.getRooms());

        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        Set<String> dates = new HashSet<String>();
        Map<String, ArrayList<TimeSlot>> creneaux = new HashMap<String,ArrayList<TimeSlot>>();
        Map<String, TimeSlot> slots = new HashMap<String,TimeSlot>();
        for (Talk talk :  schedule.getTalks()) {
            String date =  sdfDate.format(talk.getStart());
            dates.add(date);
            if (!creneaux.containsKey(date)) {
                creneaux.put(date, new ArrayList<TimeSlot>());
            }
            TimeSlot slot = new TimeSlot();
            slot.name = sdfHeure.format(talk.getStart()) + " - " + sdfHeure.format(talk.getEnd());
            slot.date = date;

            if (!slots.containsKey(date+slot.name)) {
                slots.put(date+slot.name,slot);
                creneaux.get(date).add(slot);
            }
            slots.get(date+slot.name).sessions.add(talk);
        }

        Set<Day> days = new HashSet<Day>();

        for (Map.Entry<String, ArrayList<TimeSlot>> entry : creneaux.entrySet()) {
            Day day = new Day();
            day.jour = entry.getKey();

            Collections.sort(entry.getValue());
            day.getTimeSlots().addAll(entry.getValue());

            days.add(day);
        }

        event.getDays().addAll(days);
        return event;
    }

    @RequestMapping("/error/notie.htm")
    public String index(ModelMap model) {
        return "error/notie";
    }

}
