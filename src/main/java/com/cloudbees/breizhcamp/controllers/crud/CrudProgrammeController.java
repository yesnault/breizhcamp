package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Classe CrudProgrammeController.
 */
@Controller
@Transactional
@RequestMapping("/crud/programme")
public class CrudProgrammeController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        DataProgrammeForCrud data = scheduleService.getDataForCrud();
        model.put("data", data);
        model.put("sansRoom", "sansRoom");
        model.put("allDurees", Duree.values());
        return "crud.programme.index";
    }

    @RequestMapping(value = "/talks.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTalks getTalks() {
        DataTalks talks = new DataTalks();
        talks.setTalksBySchedules(scheduleService.getTalksBySchedule());
        talks.setTalksNotScheduled(scheduleService.getTalksNotScheduled());
        talks.setDureeBySchedule(scheduleService.getDureeBySchedule());
        return talks;
    }

    @RequestMapping("/addTalk.htm")
    @ResponseBody
    public String add(@RequestParam long idSchedule, @RequestParam long idTalk) {
        scheduleService.associateScheduleAndTalk(idSchedule, idTalk);
        return "OK";
    }
}
