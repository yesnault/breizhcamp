package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.services.Schedule;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Handle request for index page
 * 
 * @author Alexandre THOMAZO <alex@thomazo.info>
 */
@Controller
public class IndexController {


    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private TalkDao talkDao;
	
	@RequestMapping("/index.htm")
	public String index(ModelMap model, @RequestParam(defaultValue="Amphi") String room) {
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        Set<Date> dates = new HashSet<Date>();
        Map<Date, List<String>> creneaux = new HashMap<Date, List<String>>();
        Map<Date, Map<String,Map<String, Talk>>> talks = new HashMap<Date, Map<String, Map<String, Talk>>>();
        for (Talk talk : talkDao.findAll()) {

            String roomOfTalk = talk.getRoom() == null ? "sansRoom" :talk.getRoom().getName();
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
        List<Room> rooms = roomDao.findAll();
        model.put("dates", datesOrdonnees);
        model.put("rooms", rooms);
        model.put("creneaux", creneaux);
        model.put("talks", talks);
        model.put("sansRoom", "sansRoom");
		return "index";
	}

    @RequestMapping("/error/notie.htm")
    public String index(ModelMap model) {
        return "error/notie";
    }

}
