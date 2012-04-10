package com.cloudbees.breizhcamp.controllers.crud;

import com.cloudbees.breizhcamp.PersistenceTestCase;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


public class CrudScheduleControllerTest extends PersistenceTestCase {

    @Autowired
    CrudScheduleController controller;


    @SuppressWarnings("unchecked")
    @Test
    public void index() {
        em.createQuery("delete from Schedule").executeUpdate();
        em.createQuery("delete from Room").executeUpdate();
        em.flush();

        Room room = new Room();
        room.setName("Une Salle");

        em.persist(room);
        
        Schedule schedule1 = new Schedule();
        schedule1.setStart(new Date());
        schedule1.setRoom(room);
        
        em.persist(schedule1);
        
        Schedule schedule2 = new Schedule();
        schedule2.setStart(new Date());
        schedule2.setRoom(room);
        
        em.persist(schedule2);

        em.flush();

        ModelMap model = new ModelMap();
        controller.index(model);
        
        List<Schedule> schedules = (List<Schedule>) model.get("schedules");
        assertThat(schedules.size()).isEqualTo(2);

        assertThat(schedules.get(0).getStart()).isNotNull();
        assertThat(schedules.get(0).getRoom()).isSameAs(room);

        assertThat(schedules.get(1).getStart()).isNotNull();
        assertThat(schedules.get(1).getRoom()).isSameAs(room);
    }


    @Test(expected = NoResultException.class)
    public void addSubmit_emptyDate() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.flush();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "", "08:00", null);

        assertThat((String)model.get("dateError")).isEqualTo("La date est obligatoire");

        em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();

    }


    @Test(expected = NoResultException.class)
    public void addSubmit_emptyStartTime() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.flush();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "14/06/2012", "", null);

        assertThat((String)model.get("startTimeError")).isEqualTo("L'heure est obligatoire");

        em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();

    }


    @Test(expected = NoResultException.class)
    public void addSubmit_emptyStartTimeFormat() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.flush();


        ModelMap model = new ModelMap();
        controller.addSubmit(model, "14/06/2012", "tutu", null);

        assertThat((String)model.get("startTimeError")).isEqualTo("Le format est incorrect");

        em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();

    }

    @Test
    public void addSubmit() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.createQuery("delete from Room").executeUpdate();
        em.flush();

        Room room = new Room();
        room.setName("Une Salle");
        
        em.persist(room);
        em.flush();
        
        ModelMap model = new ModelMap();
        String redirect = controller.addSubmit(model, "14/06/2012", "08:00", room.getId());
        assertThat(redirect).isEqualTo("redirect:/crud/schedule/index.htm");
        
        Schedule schedule = em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();
        assertThat(schedule).isNotNull();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        assertThat(schedule.getStart()).isEqualTo(sdf.parse("14/06/2012 08:00"));
        assertThat(schedule.getRoom()).isSameAs(room);
    }

    @Test
    public void editubmit() throws Exception {
        em.createQuery("delete from Schedule").executeUpdate();
        em.createQuery("delete from Room").executeUpdate();
        em.flush();

        Room room1 = new Room();
        room1.setName("Salle1");
        em.persist(room1);

        Room room2 = new Room();
        room2.setName("Salle2");
        em.persist(room2);
        
        em.flush();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        Schedule schedule = new Schedule();
        schedule.setStart(sdf.parse("14/06/2012 08:00"));
        schedule.setRoom(room1);

        em.persist(schedule);
        em.flush();

        ModelMap model = new ModelMap();
        String redirect = controller.editSubmit(model, schedule.getId(), "15/06/2012", "10:00", room2.getId());
        assertThat(redirect).isEqualTo("redirect:/crud/schedule/index.htm");

        schedule = em.createQuery("select s from Schedule s", Schedule.class).getSingleResult();
        assertThat(schedule).isNotNull();
        assertThat(schedule.getStart()).isEqualTo(sdf.parse("15/06/2012 10:00"));
        assertThat(schedule.getRoom()).isSameAs(room2);
        
    }


}
