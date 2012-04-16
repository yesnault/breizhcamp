package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.domain.Theme;
import com.cloudbees.breizhcamp.services.ScheduleService;
import com.cloudbees.breizhcamp.domain.Talk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guernion Sylvain
 */
@Controller
public class TalkController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/talks.json",method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Talk> talks() {
        return scheduleService.getTalks();
    }

    @RequestMapping(value = "/talk/{id}.htm", method = RequestMethod.GET)
    public String talk(ModelMap model,@PathVariable long id,
                       @RequestParam(defaultValue = "false") boolean hide) {
        model.put("hide", hide);
        Talk talk = scheduleService.getTalk(id);
        model.put("talk", talk);
        return "talk";
    }

    @RequestMapping(value = "/theme/{id}.htm", method = RequestMethod.GET)
    public String theme(ModelMap model,@PathVariable String id,
                       @RequestParam(defaultValue = "false") boolean hide) {
        model.put("hide", hide);
        Theme theme = Theme.valueOf(id);
        List<Talk> talks = scheduleService.getTalkByTheme(theme);
        model.put("talks", talks);
        model.put("theme", theme);
        return "theme";
    }


    @RequestMapping(value = "/talk/get/{id}.json", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Talk get(@PathVariable long id) {
        return scheduleService.getTalk(id);
    }

    /**
     * Launched if an error appears
     * @param e Exception raised
     * @return Error message
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, String> handleException(Exception e) {
        Map<String, String> res = new HashMap<String, String>();
        res.put("error", e.getLocalizedMessage());
        return res;
    }
}
