package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.ScheduleDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import com.cloudbees.breizhcamp.services.CrudService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private ScheduleDao scheduleDao;

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
        model.put("allSpeakers", speakerDao.findAll());
        model.put("allSchedules", scheduleDao.findAll());
        return "crud.talk.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam String title, @RequestParam String resume,
                            @RequestParam int duree, @RequestParam String theme,
                            @RequestParam(required = false) List<Long> speakers,
                            @RequestParam(required = false) Long schedule) {
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
       
        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Theme monTheme = Theme.fromHtmlValue(theme);

        List<Speaker> mySpeakers = new ArrayList<Speaker>();
        for (Long idSpeaker : speakers) {
            try {
                mySpeakers.add(speakerDao.find(idSpeaker));
            } catch (NoResultException noResultException) {
                model.put("speakersError", "Un speaker n'existe pas");
                hasError = true;
            }
        }

        Schedule monSchedule = null;
        if (schedule != null && schedule != -1) {
            try {
                monSchedule = scheduleDao.find(schedule);
            } catch (NoResultException noResultException) {
                model.put("scheduleError", "Le créneau n'existe pas");
                hasError = true;
            }
        }

        if (hasError) {
            model.put("title", title);
            model.put("resume", resume);
            model.put("duree", duree);
            model.put("theme", theme);
            model.put("speakers", speakers);
            model.put("schedule", schedule);
            model.put("possibleThemes", Theme.values());
            model.put("possibleDurees", Duree.values());
            model.put("allSpeakers", speakerDao.findAll());
            model.put("allSchedules", scheduleDao.findAll());
            return "crud.talk.add";
        }
        service.addTalk(title, resume, duree, monTheme, mySpeakers, monSchedule);
        return "redirect:/crud/talk/index.htm";
    }

    @RequestMapping("/delete/{id}.htm")
    public String deleteTalk(@PathVariable Long id) {
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
        model.put("possibleDurees", Duree.values());
        model.put("allSpeakers", speakerDao.findAll());
        model.put("allSchedules", scheduleDao.findAll());
        return "crud.talk.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String editSubmit(ModelMap model, @RequestParam Long id, @RequestParam String title,
                             @RequestParam String resume, @RequestParam int duree, @RequestParam String theme,
                             @RequestParam(required = false) List<Long> speakers,
                             @RequestParam(required = false) Long schedule) {
        if (speakers == null) {
            speakers = new ArrayList<Long>();
        }
        Talk talk = talkDao.find(id);
        model.put("talk", talk);
        model.put("possibleThemes", Theme.values());
        model.put("possibleDurees", Duree.values());
        model.put("allSpeakers", speakerDao.findAll());
        model.put("allSchedules", scheduleDao.findAll());

        boolean hasError = false;
        if (StringUtils.isEmpty(title)) {
            model.put("titleError", "Le titre est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(resume)) {
            model.put("resumeError", "Le résumé est obligatoire");
            hasError = true;
        }

        if (StringUtils.isEmpty(theme)) {
            model.put("themeError", "Le thème est obligatoire");
            hasError = true;
        }
        Schedule monSchedule = null;
        if (schedule != null && schedule != -1) {
            try {
                monSchedule = scheduleDao.find(schedule);
            } catch (NoResultException noResultException) {
                model.put("scheduleError", "Le créneau n'existe pas");
                hasError = true;
            }
        }
        Theme monTheme = Theme.fromHtmlValue(theme);

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
        talk.setDuree(duree);
        for (Speaker speaker : mySpeakers) {
            talk.getSpeakers().add(speaker);
            speaker.getTalks().add(talk);
        }
        talk.setSchedule(monSchedule);
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
