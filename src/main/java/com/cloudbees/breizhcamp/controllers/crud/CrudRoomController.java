package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.services.CrudService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Guernion Sylvain
 */
@Controller
@Transactional
@RequestMapping("/crud/room")
public class CrudRoomController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private CrudService service;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        model.put("rooms", roomDao.findAll());
        return "crud.room.index";
    }

    @RequestMapping("/add.htm")
    public String add(ModelMap model) {
        return "crud.room.add";
    }

    @RequestMapping("/add/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            model.put("error", true);
            model.put("nameError", "Le nom est obligatoire");
            return "crud.room.add";
        }
        if (service.roomExist(name)) {
            model.put("error", true);
            model.put("nameError", "Le nom '" + name + "' est déjà pris");
            return "crud.room.add";
        }
        service.addRoom(name);
        return "redirect:/crud/room/index.htm";
    }

    @RequestMapping("/delete/{id}.htm")
    public String deleteRoom(ModelMap model, @PathVariable Long id) {
        roomDao.delete(id);
        return "redirect:/crud/room/index.htm";
    }
    
    @RequestMapping("/edit/{id}.htm")
    public String editRoom(ModelMap model, @PathVariable Long id) {
        model.put("room", roomDao.find(id));
        return "crud.room.edit";
    }

    @RequestMapping("/edit/submit.htm")
    public String addSubmit(ModelMap model, @RequestParam Long id, @RequestParam String name) {
        Room room = roomDao.find(id);
        model.put("room", room);
        if (StringUtils.isEmpty(name)) {
            model.put("error", true);
            model.put("nameError", "Le nom est obligatoire");
            return "crud.room.edit";
        }
        if (!room.getName().equals(name) && service.roomExist(name)) {
            model.put("error", true);
            model.put("nameError", "Le nom '" + name + "' est déjà pris");
            return "crud.room.edit";
        }
        room.setName(name);
        return "redirect:/crud/room/index.htm";
    }
}
