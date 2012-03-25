package com.cloudbees.breizhcamp.controllers;

import com.cloudbees.breizhcamp.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudbees.breizhcamp.Schedule;

/**
 * Handle request for index page
 * 
 * @author Alexandre THOMAZO <alex@thomazo.info>
 */
@Controller
public class IndexController {
	
	@Autowired
	private Schedule schedule;
	
	@RequestMapping("/index.htm")
	public String index(ModelMap model, @RequestParam(defaultValue="Amphi") String room) {

		model.put("talks", schedule.getSchedule());
		return "index";
	}

}
