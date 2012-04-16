package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.ScheduleDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.PossibleDates;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Talk;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yan Bonnel
 */
@Controller
@Transactional
@RequestMapping("/crud/schedule")
public class CrudScheduleController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private TalkDao talkDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private CrudService service;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        model.put("schedules", scheduleDao.findAll());
        return "crud.schedule.index";
    }

    @RequestMapping("/add.htm")
    public String add(ModelMap model) {
        model.put("allRooms", roomDao.findAll());
        model.put("possibleDates", PossibleDates.values());
        model.put("possibleDurees", Duree.values());
        return "crud.schedule.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam int duree, @RequestParam String date, @RequestParam String startTime,
                            @RequestParam(required = false) Long room) {
        boolean hasError = false;

        if (StringUtils.isEmpty(date)) {
            model.put("dateError", "La date est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(startTime)) {
            model.put("startTimeError", "L'heure est obligatoire");
            hasError = true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = null;
        try {
            if (!StringUtils.isEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
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


        if (!hasError && scheduleDao.exists(maRoom, startDate)) {
            model.put("roomError", "Il existe déjà un créneau pour cette date et cette salle.");
            model.put("startTimeError", "Il existe déjà un créneau pour cette date et cette salle.");
            model.put("dateError", "Il existe déjà un créneau pour cette date et cette salle.");
            hasError = true;
        }

        if (hasError) {
            model.put("room", room);
            model.put("date", date);
            model.put("startTime", startTime);
            model.put("allRooms", roomDao.findAll());
            model.put("possibleDates", PossibleDates.values());
            model.put("possibleDurees", Duree.values());
            return "crud.schedule.add";
        }


        service.addSchedule(startDate, maRoom, duree);
        return "redirect:/crud/schedule/index.htm";
    }

    @RequestMapping("/delete/{id}.htm")
    public String delete(@PathVariable Long id) {
        Schedule schedule = scheduleDao.find(id);
        for (Talk talk : talkDao.findBySchedule(schedule)) {
            talk.setSchedule(null);
        }
        scheduleDao.delete(schedule);
        return "redirect:/crud/schedule/index.htm";
    }

    @RequestMapping("/edit/{id}.htm")
    public String edit(ModelMap model, @PathVariable Long id) {
        model.put("schedule", scheduleDao.find(id));
        model.put("allRooms", roomDao.findAll());
        model.put("possibleDates", PossibleDates.values());
        model.put("possibleDurees", Duree.values());
        return "crud.schedule.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String editSubmit(ModelMap model, @RequestParam Long id, @RequestParam String date,
                             @RequestParam String startTime, @RequestParam int duree,
                             @RequestParam(required = false) Long room) {
        Schedule schedule = scheduleDao.find(id);
        model.put("schedule", schedule);

        boolean hasError = true;

        if (StringUtils.isEmpty(date)) {
            model.put("dateError", "Le date est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(startTime)) {
            model.put("startTimeError", "L'heure est obligatoire");
            hasError = true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = null;
        try {
            if (!StringUtils.isEmpty(startTime)) {
                startDate = sdf.parse(date + " " + startTime);
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
        if (hasError) {
            model.put("allRooms", roomDao.findAll());
            model.put("possibleDates", PossibleDates.values());
            model.put("possibleDurees", Duree.values());
            model.put("schedule", schedule);
        }
        schedule.setStart(startDate);
        schedule.setRoom(maRoom);
        schedule.setDuree(duree);
        return "redirect:/crud/schedule/index.htm";
    }

    /**
     * Launched if an error appears
     *
     * @param e Exception raised
     * @return Error message
     */
    @ExceptionHandler
    public
    @ResponseBody
    Map<String, String> handleException(Exception e) {
        Map<String, String> res = new HashMap<String, String>();
        res.put("error", e.getLocalizedMessage());
        return res;
    }
}
