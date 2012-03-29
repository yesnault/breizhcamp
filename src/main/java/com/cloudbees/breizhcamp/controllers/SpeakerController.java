package com.cloudbees.breizhcamp.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.cloudbees.breizhcamp.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;

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
	public Speaker get(@PathVariable int id) {
		return schedule.getSpeaker(id);
	}

    @RequestMapping("/speakers.htm")
    	public String index(ModelMap model,@RequestParam(defaultValue="0") int id) {

    		model.put("speakers", schedule.getSpeaker(id));
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
