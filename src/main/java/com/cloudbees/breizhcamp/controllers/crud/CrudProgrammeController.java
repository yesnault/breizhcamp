package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "crud.programme.index";
    }
}
