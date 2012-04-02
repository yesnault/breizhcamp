package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import com.cloudbees.breizhcamp.services.CrudService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Guernion Sylvain
 */
@Controller
@Transactional
@RequestMapping("/crud/talk")
public class CrudTalkController {

    @Autowired
    private TalkDao talkDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private SpeakerDao speakerDao;

    @Autowired
    private CrudService service;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        model.put("talks", talkDao.findAll());
        return "crud.talk.index";
    }

    @RequestMapping("/add.htm")
    public String add(ModelMap model) {
        model.put("possibleThemes", Theme.values());
        model.put("allRooms", roomDao.findAll());
        model.put("allSpeakers", speakerDao.findAll());
        return "crud.talk.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam String title, @RequestParam String resume,
                            @RequestParam String date, @RequestParam String startTime, @RequestParam String endTime,
                            @RequestParam String theme, @RequestParam(required = false) Long room, @RequestParam(required = false) List<Long> speakers) {
        if (speakers == null) {
            speakers = new ArrayList<Long>();
        }
        boolean hasError = false;
        if (StringUtils.isEmpty(title)) {
            model.put("titleError", "Le titre est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(resume)) {
            model.put("resumeError", "Le résumé est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(date)) {
            model.put("dateError", "Le date est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(startTime)) {
            model.put("startTimeError", "L'heure de début est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(endTime)) {
            model.put("endTimeError", "L'heure de fin est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Theme monTheme = Theme.fromHtmlValue(theme);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
            }
        } catch (ParseException e) {
            model.put("startTimeError", "Le format est incorrect");
            hasError = true;
        }
        Date endDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                endDate = sdf.parse(date + " " + endTime);
            }
        } catch (ParseException e) {
            model.put("endTimeError", "Le format est incorrect");
            hasError = true;
        }

        Room maRoom = null;
        if (room != null && room != -1) {
            try {
                maRoom = roomDao.find(room);
            } catch (NoResultException noResultException) {
                model.put("roomError", "La salle " + room + " n'existe pas");
                hasError = true;
            }
        }
        
        List<Speaker> mySpeakers = new ArrayList<Speaker>();
        for (Long idSpeaker : speakers) {
            try {
                mySpeakers.add(speakerDao.find(idSpeaker));
            } catch (NoResultException noResultException) {
                model.put("speakersError", "Un speaker n'existe pas");
                hasError = true;
            }
        }

        if (hasError) {
            model.put("title", title);
            model.put("resume", resume);
            model.put("date", date);
            model.put("startTime", startTime);
            model.put("endTime", endTime);
            model.put("theme", theme);
            model.put("room", room);
            model.put("speakers", speakers);
            model.put("possibleThemes", Theme.values());
            model.put("allRooms", roomDao.findAll());
            model.put("allSpeakers", speakerDao.findAll());
            return "crud.talk.add";
        }
        service.addTalk(title, resume, startDate, endDate, monTheme, maRoom, mySpeakers);
        return "redirect:/crud/talk/index.htm";
    }

    @RequestMapping("/delete/{id}.htm")
    public String deleteTalk(ModelMap model, @PathVariable Long id) {
        Talk talk = talkDao.find(id);
        for (Speaker speaker : talk.getSpeakers()) {
            speaker.getTalks().remove(talk);
        }
        talkDao.delete(talk);
        return "redirect:/crud/talk/index.htm";
    }

    @RequestMapping("/edit/{id}.htm")
    public String editTalk(ModelMap model, @PathVariable Long id) {
        Talk talk = talkDao.find(id);
        model.put("talk", talk);
        model.put("possibleThemes", Theme.values());
        model.put("allRooms", roomDao.findAll());
        model.put("allSpeakers", speakerDao.findAll());
        return "crud.talk.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String editSubmit(ModelMap model, @RequestParam Long id, @RequestParam String title, @RequestParam String resume,
                            @RequestParam String date, @RequestParam String startTime, @RequestParam String endTime,
                            @RequestParam String theme, @RequestParam(required = false) Long room, @RequestParam(required = false) List<Long> speakers) {
        if (speakers == null) {
            speakers = new ArrayList<Long>();
        }
        Talk talk = talkDao.find(id);
        model.put("talk", talk);
        model.put("possibleThemes", Theme.values());
        model.put("allRooms", roomDao.findAll());
        model.put("allSpeakers", speakerDao.findAll());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        boolean hasError = false;
        if (StringUtils.isEmpty(title)) {
            model.put("titleError", "Le titre est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(resume)) {
            model.put("resumeError", "Le résumé est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(date)) {
            model.put("dateError", "Le date est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(startTime)) {
            model.put("startTimeError", "L'heure de début est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(endTime)) {
            model.put("endTimeError", "L'heure de fin est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Theme monTheme = Theme.fromHtmlValue(theme);
        Date startDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
            }
        } catch (ParseException e) {
            model.put("startTimeError", "Le format est incorrect");
            hasError = true;
        }
        Date endDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                endDate = sdf.parse(date + " " + endTime);
            }
        } catch (ParseException e) {
            model.put("endTimeError", "Le format est incorrect");
            hasError = true;
        }
        
        if (room != null && room != -1) {
            try {
                talk.setRoom(roomDao.find(room));
            } catch (NoResultException noResultException) {
                model.put("roomError", "La salle " + room + " n'existe pas");
                hasError = true;
            }
        } else {
            talk.setRoom(null);
        }

        if (hasError) {
            return "crud.talk.edit";
        }
        
        Set<Speaker> mySpeakers = new HashSet<Speaker>();
        for (Long idSpeaker : speakers) {
            mySpeakers.add(speakerDao.find(idSpeaker));
        }

        Iterator<Speaker> itSpeaker = talk.getSpeakers().iterator();
        while (itSpeaker.hasNext()) {
            Speaker oldSpeaker = itSpeaker.next();
            if (mySpeakers.contains(oldSpeaker)) {
                mySpeakers.remove(oldSpeaker);
            } else {
                oldSpeaker.getTalks().remove(talk);
                itSpeaker.remove();
            }
        }
        
        talk.setAbstract(resume);
        talk.setTitle(title);
        talk.setTheme(monTheme);
        talk.setStart(startDate);
        talk.setEnd(endDate);
        for (Speaker speaker : mySpeakers) {
            talk.getSpeakers().add(speaker);
            speaker.getTalks().add(talk);
        }
        model.clear();
        return "redirect:/crud/talk/index.htm";
    }
}
