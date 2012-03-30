package com.cloudbees.breizhcamp.controllers.crud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Guernion Sylvain
 */
@Controller
@RequestMapping("/crud/talk")
public class CrudTalkController {

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        return "crud.talk.index";
    }
}
