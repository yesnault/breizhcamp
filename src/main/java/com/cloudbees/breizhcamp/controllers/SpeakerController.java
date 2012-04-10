package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.services.Schedule;
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
 * Handle request for Speaker
 * 
 * @author Alexandre THOMAZO <alex@thomazo.info>
 */
@Controller
public class SpeakerController {

	@Autowired
	private Schedule schedule;
	
	@RequestMapping(value = "/speaker/get/{id}.json", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Speaker get(@PathVariable long id) {
		return schedule.getSpeaker(id);
	}

    @RequestMapping(value = "/speaker/{id}.htm", method = RequestMethod.GET)
    public String speaker(ModelMap model,@PathVariable long id,@RequestParam(defaultValue = "false") boolean hide) {
        model.put("hide", hide);
        model.put("speaker", schedule.getSpeaker(id));
        return "speaker";
    }

    @RequestMapping(value = "/speakers.json", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Speaker> speakers() {
        return schedule.getSpeakers();
    }


    @RequestMapping("/speakers.htm")
    public String index(ModelMap model,
                        @RequestParam(defaultValue = "false") boolean hide) {
        model.put("hide", hide);
        model.put("speakers", schedule.getSpeakers());
        return "speakers";
    }
	
	/**
	 * Launched if an error appears
	 * @param e Exception raised
	 * @return Error message
	 */
	@ExceptionHandler
	public @ResponseBody Map<String, String> handleException(Exception e) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("error", e.getLocalizedMessage());
		return res;
	}
}
