package com.cloudbees.breizhcamp.controllers;


import com.cloudbees.breizhcamp.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    @Autowired
    private ScheduleService service;

    @RequestMapping(value = "/programme.pdf", method = RequestMethod.GET, produces = "application/pdf")
    protected ModelAndView programme() throws Exception {
        return new ModelAndView("pdfViewResolver", "data", service.getData());

    }
    
    /**
     * Launched if an error appears
     * @param e Exception raised
     * @return Error message
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, String> handleException(Exception e) {
        e.printStackTrace();
        Map<String, String> res = new HashMap<String, String>();
        res.put("error", e.getLocalizedMessage());
        return res;
    }
}
