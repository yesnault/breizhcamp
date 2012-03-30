package com.cloudbees.breizhcamp.controllers.crud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Guernion Sylvain
 */
@Controller
@RequestMapping("/crud/room")
public class RoomController {

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        return "crud.room.index";
    }
}
