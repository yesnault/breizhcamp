package com.cloudbees.breizhcamp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudbees.breizhcamp.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;

/**
 * Handle request for Speaker
 * 
 * @author Alexandre THOMAZO <alex@thomazo.info>
 */
@Controller
@RequestMapping("/speaker")
public class SpeakerController {

	@Autowired
	private Schedule schedule;
	
	@RequestMapping(value = "/get/{id}.json", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Speaker get(@PathVariable int id) {
		return schedule.getSpeaker(id);
	}

    @RequestMapping( method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<Speaker> speakers() {
        return schedule.getSpeakers();
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
