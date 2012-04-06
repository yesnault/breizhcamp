package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.*;
import com.cloudbees.breizhcamp.services.CrudService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        model.put("possibleDurees", Duree.values());
        model.put("allRooms", roomDao.findAll());
        model.put("allSpeakers", speakerDao.findAll());
        return "crud.talk.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam String title, @RequestParam String resume,
                            @RequestParam String date, @RequestParam String startTime, @RequestParam int duree,
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
       
        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Theme monTheme = Theme.fromHtmlValue(theme);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
            }else {
                startDate = sdf2.parse(date);
            }
        } catch (ParseException e) {
            model.put("startTimeError", "Le format est incorrect");
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
            model.put("duree", duree);
            model.put("theme", theme);
            model.put("room", room);
            model.put("speakers", speakers);
            model.put("possibleThemes", Theme.values());
            model.put("possibleDurees", Duree.values());
            model.put("allRooms", roomDao.findAll());
            model.put("allSpeakers", speakerDao.findAll());
            return "crud.talk.add";
        }
        service.addTalk(title, resume, startDate, duree, monTheme, maRoom, mySpeakers);
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
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        String start = sdfHeure.format(talk.getStart());
        if(start.equals("00:00")){
            start="";
        }
        model.put("start", start);
        model.put("possibleThemes", Theme.values());
        model.put("possibleDurees", Duree.values());
        model.put("allRooms", roomDao.findAll());
        model.put("allSpeakers", speakerDao.findAll());
        return "crud.talk.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String editSubmit(ModelMap model, @RequestParam Long id, @RequestParam String title, @RequestParam String resume,
                            @RequestParam String date, @RequestParam String startTime, @RequestParam int duree,
                            @RequestParam String theme, @RequestParam(required = false) Long room, @RequestParam(required = false) List<Long> speakers) {
        if (speakers == null) {
            speakers = new ArrayList<Long>();
        }
        Talk talk = talkDao.find(id);
        model.put("talk", talk);
        model.put("possibleThemes", Theme.values());
        model.put("possibleDurees", Duree.values());
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

        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Theme monTheme = Theme.fromHtmlValue(theme);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            if (StringUtils.isNotEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
            }
            else {
                startDate = sdf2.parse(date);
            }
        } catch (ParseException e) {
            model.put("startTimeError", "Le format est incorrect");
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
        talk.setDuree(duree);
        for (Speaker speaker : mySpeakers) {
            talk.getSpeakers().add(speaker);
            speaker.getTalks().add(talk);
        }
        model.clear();
        return "redirect:/crud/talk/index.htm";
    }


    /**
     * Launched if an error appears
     * @param e Exception raised
     * @return Error message
     */
    @ExceptionHandler
    public @ResponseBody
    Map<String, String> handleException(Exception e) {
        Map<String, String> res = new HashMap<String, String>();
        res.put("error", e.getLocalizedMessage());
        return res;
    }
}
