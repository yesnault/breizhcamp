package com.cloudbees.breizhcamp.services;

import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.controllers.crud.DataProgrammeForCrud;
import com.cloudbees.breizhcamp.dao.impl.RoomDao;
import com.cloudbees.breizhcamp.dao.impl.ScheduleDao;
import com.cloudbees.breizhcamp.dao.impl.SpeakerDao;
import com.cloudbees.breizhcamp.dao.impl.TalkDao;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Schedule;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.cloudbees.breizhcamp.domain.Theme;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Service
public class ScheduleService {

    @Autowired
    private TalkDao talkDao;

    @Autowired
    private SpeakerDao speakerDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ScheduleDao scheduleDao;

    public List<Schedule> getSchedule() {
        return scheduleDao.findAll();
    }

    public List<Speaker> getSpeakers() {
        return speakerDao.findAll();
    }

    public List<Talk> getTalks() {
        return talkDao.findAll();
    }

    public List<Room> getRooms() {
        return roomDao.findAll();
    }


    public Speaker getSpeaker(long id) {
        return speakerDao.find(id);
    }

    public Talk getTalk(long id) {
        return talkDao.find(id);
    }

    public List<Talk> getTalkByTheme(Theme theme) {
        return talkDao.findByTheme(theme);
    }

    public DataProgrammeForCrud getDataForCrud() {
        DataProgrammeForCrud data = new DataProgrammeForCrud();
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        Set<Date> dates = new HashSet<Date>();
        for (Schedule schedule : getSchedule()) {
            String roomOfSchedule = schedule.getRoom() == null ? "sansRoom" : schedule.getRoom().getName();
            Date date = DateUtils.truncate(schedule.getStart(), Calendar.DATE);
            dates.add(date);
            if (!data.getCreneaux().containsKey(date)) {
                data.getCreneaux().put(date, new ArrayList<String>());
                data.getSchedules().put(date, new HashMap<String, Map<String, Schedule>>());
            }
                Calendar cal = Calendar.getInstance();
                cal.setTime(schedule.getStart());
                cal.add(Calendar.MINUTE, schedule.getDuree());
            String creneau = sdfHeure.format(schedule.getStart()) + " - " + sdfHeure.format(cal.getTime());
            if (!data.getCreneaux().get(date).contains(creneau)) {
                data.getCreneaux().get(date).add(creneau);
            }
            if (!data.getSchedules().get(date).containsKey(creneau)) {
                data.getSchedules().get(date).put(creneau, new HashMap<String, Schedule>());
            }
            data.getSchedules().get(date).get(creneau).put(roomOfSchedule, schedule);
        }
        data.getDatesOrdonnees().addAll(dates);
        Collections.sort(data.getDatesOrdonnees());

        for (List<String> creneauxForDate : data.getCreneaux().values()) {
            Collections.sort(creneauxForDate);
        }
        data.setRooms(getRooms());

        for (Talk talk : talkDao.findAll()) {
            if (talk.getSchedule() != null) {
                data.getTalksBySchedules().put(talk.getSchedule(), talk);
            } else {
                if (!data.getTalksNotScheduled().containsKey(talk.getDuree())) {
                    data.getTalksNotScheduled().put(talk.getDuree(), new ArrayList<Talk>());
                }
                data.getTalksNotScheduled().put(talk.getDuree(), new ArrayList<Talk>());
            }
        }

        return data;
    }

    public Data getData() {
        Data data = new Data();
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");
        Set<Date> dates = new HashSet<Date>();
        for (Talk talk : getTalks()) {
            if (talk.getSchedule() == null) {
                continue;
            }

            String roomOfTalk = talk.getSchedule().getRoom() == null ? "sansRoom" : talk.getSchedule().getRoom().getName();
            Date date = DateUtils.truncate(new Date(), Calendar.DATE);
            if (talk.getStart() != null) {
                date = DateUtils.truncate(talk.getStart(), Calendar.DATE);
            }
            dates.add(date);
            if (!data.getCreneaux().containsKey(date)) {
                data.getCreneaux().put(date, new ArrayList<String>());
                data.getTalks().put(date, new HashMap<String, Map<String, Talk>>());
            }
            String creneau = "non programmé";
            if (talk.getStart() != null && !sdfHeure.format(talk.getStart()).equals("00:00")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(talk.getStart());
                cal.add(Calendar.MINUTE, talk.getDuree());
                creneau = sdfHeure.format(talk.getStart()) + " - " + sdfHeure.format(cal.getTime());
            }
            if (!data.getCreneaux().get(date).contains(creneau)) {
                data.getCreneaux().get(date).add(creneau);
            }
            if (!data.getTalks().get(date).containsKey(creneau)) {
                data.getTalks().get(date).put(creneau, new HashMap<String, Talk>());
            }
            data.getTalks().get(date).get(creneau).put(roomOfTalk, talk);
        }
        data.getDatesOrdonnees().addAll(dates);
        Collections.sort(data.getDatesOrdonnees());

        for (List<String> creneauxForDate : data.getCreneaux().values()) {
            Collections.sort(creneauxForDate);
        }
        data.setRooms(getRooms());
        return data;
    }
}
