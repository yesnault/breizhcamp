package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

/**
 * Created by IntelliJ IDEA.
 * User: ybonnel
 * Date: 30/03/12
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class CrudService {

    @Autowired
    private RoomDao roomDao;
    
    public boolean roomExist(String name) {
        try {
            roomDao.findByName(name);
            return true;
        } catch (NoResultException ignore){
            return false;
        }
    }
    
    public void addRoom(String name) {
        Room room = new Room();
        room.setName(name);
        roomDao.save(room);
    }
    
    public void deleteRoom(Long id) {
        roomDao.delete(roomDao.find(id));
    }
}
