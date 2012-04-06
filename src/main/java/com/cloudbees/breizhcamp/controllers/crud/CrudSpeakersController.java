package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.services.CrudService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guernion Sylvain
 */
@Controller
@Transactional
@RequestMapping("/crud/speaker")
public class CrudSpeakersController {

    @Autowired
    private SpeakerDao speakerDao;

    @Autowired
    private CrudService service;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        model.put("speakers", speakerDao.findAll());
        return "crud.speaker.index";
    }

    @RequestMapping("/add.htm")
    public String add(ModelMap model) {
        return "crud.speaker.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String picture, @RequestParam String twitter, @RequestParam String description) {
        boolean hasError = false;
        if (StringUtils.isEmpty(lastName)) {
            model.put("lastNameError", "Le nom est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(firstName)) {
            model.put("firstNameError", "Le prénom est obligatoire");
            hasError = true;
        }
        URL pictureUrl = null;
        try {
            if (StringUtils.isNotEmpty(picture)) {
                pictureUrl = new URL(picture);
            }
        } catch (MalformedURLException e) {
            model.put("pictureError", "L'URL " + picture + " n'est pas conforme");
            hasError = true;
        }
        if (hasError) {
            model.put("lastName", lastName);
            model.put("firstName", firstName);
            model.put("picture", picture);
            model.put("twitter", twitter);
            model.put("description", description);
            return "crud.speaker.add";
        }
        if (service.speakerExist(firstName, lastName)) {
            model.put("firstNameError", "Ce speaker existe déjà.");
            model.put("lastNameError", "Ce speaker existe déjà.");
            return "crud.speaker.add";
        }
        service.addSpeaker(firstName, lastName, pictureUrl,twitter,description);
        return "redirect:/crud/speaker/index.htm";
    }

    @RequestMapping("/delete/{id}.htm")
    public String deleteSpeaker(ModelMap model, @PathVariable Long id) {
        Speaker speaker = speakerDao.find(id);
        for (Talk talk : speaker.getTalks()) {
            talk.getSpeakers().remove(speaker);
        }
        speakerDao.delete(speaker);
        return "redirect:/crud/speaker/index.htm";
    }

    @RequestMapping("/edit/{id}.htm")
    public String editSpeaker(ModelMap model, @PathVariable Long id) {
        model.put("speaker", speakerDao.find(id));
        return "crud.speaker.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam Long id, @RequestParam String lastName, @RequestParam String firstName, @RequestParam String picture,@RequestParam String twitter, @RequestParam String description) {
        Speaker speaker = speakerDao.find(id);
        model.put("speaker", speaker);
        boolean hasError = false;
        if (StringUtils.isEmpty(lastName)) {
            model.put("lastNameError", "Le nom est obligatoire");
            hasError = true;
        }
        if (StringUtils.isEmpty(firstName)) {
            model.put("firstNameError", "Le prénom est obligatoire");
            hasError = true;
        }
        URL pictureUrl = null;
        try {
            if (StringUtils.isNotEmpty(picture)) {
                pictureUrl = new URL(picture);
            }
        } catch (MalformedURLException e) {
            model.put("pictureError", "L'URL " + picture + " n'est pas conforme");
            hasError = true;
        }
        if (hasError) {
            return "crud.speaker.edit";
        }
        if ((!speaker.getFirstName().equals(firstName)
        || !speaker.getLastName().equals(lastName)) && service.speakerExist(firstName, lastName)) {
            model.put("firstNameError", "Ce speaker existe déjà.");
            model.put("lastNameError", "Ce speaker existe déjà.");
            return "crud.speaker.add";
        }
        speaker.setFirstName(firstName);
        speaker.setLastName(lastName);
        speaker.setPicture(pictureUrl);
        speaker.setTwitter(twitter);
        speaker.setDescription(description);
        return "redirect:/crud/speaker/index.htm";
    }


    /**
     * Launched if an error appears
     * @param e Exception raised
     * @return Error message
     */
    @ExceptionHandler
    public @ResponseBody
    Map<String, String> handleException(Exception e) {
        Map<String, String> res = new HashMap<String, String>();
        res.put("error", e.getLocalizedMessage());
        return res;
    }
}
