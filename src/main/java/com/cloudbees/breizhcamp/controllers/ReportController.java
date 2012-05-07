package com.cloudbees.breizhcamp.controllers;


import com.cloudbees.breizhcamp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    @Autowired
    private ScheduleService service;

    @RequestMapping(value = "/programme.pdf", method = RequestMethod.GET, produces = "application/pdf")
    protected ModelAndView programme(@RequestParam(defaultValue = "false") boolean a3) throws Exception {
        ModelAndView model = new ModelAndView("pdfViewResolver", "data", service.getData());
        model.getModelMap().put("modeA3",a3);
        return model;

    }
    

}
