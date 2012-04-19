package com.cloudbees.breizhcamp.tld;


import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TldFunctionsTest {

    @Test
    public void getThemmeName() {
        assertEquals("PRATIQUER", TldFunctions.getThemeName(Theme.PRATIQUER));
        assertEquals("APPROFONDIR", TldFunctions.getThemeName(Theme.APPROFONDIR));
        assertEquals("DECOUVRIR", TldFunctions.getThemeName(Theme.DECOUVRIR));
        assertNull(TldFunctions.getThemeName(null));
    }

    @Test
    public void getDureeName() {
        assertEquals("T_15", TldFunctions.getDureeName(Duree.T_15));
        assertEquals("T_30", TldFunctions.getDureeName(Duree.T_30));
        assertEquals("T_60", TldFunctions.getDureeName(Duree.T_60));
        assertEquals("T_180", TldFunctions.getDureeName(Duree.T_180));
        assertNull(TldFunctions.getDureeName(null));
    }

    @Test
    public void contains() {
        List<Room> rooms = new ArrayList<Room>();
        Room room = new Room();
        room.setName("Amphi");
        rooms.add(room);

        Room room2 = new Room();
        room2.setName("I50");

        assertTrue(TldFunctions.contains(rooms, room));
        assertFalse(TldFunctions.contains(rooms, room2));
        assertFalse(TldFunctions.contains(null, null));
    }

    @Test
    public void getProfilUrl() {
        assertEquals("https://twitter.com/#!/sguernion", TldFunctions.getProfilUrl("sguernion"));
        assertEquals("https://plus.google.com/123456789456", TldFunctions.getProfilUrl("+123456789456"));
    }

    @Test
    public void getDuree() {
        assertEquals("Quickies(15 min)", TldFunctions.getDuree(15));
        assertEquals("Tools in action(30 min)", TldFunctions.getDuree(30));
        assertEquals("Conférence(1 heure)", TldFunctions.getDuree(60));
        assertEquals("Université(3 heures)", TldFunctions.getDuree(180));

        assertNull(TldFunctions.getDuree(66));
    }

    @Test
    public void getDebut() throws Exception {
        SimpleDateFormat sdfDateHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");


        assertEquals("", TldFunctions.getDebut(sdfDate.parse("10/11/2012")));
        assertEquals("", TldFunctions.getDebut(null));
        assertEquals("10:00", TldFunctions.getDebut(sdfDateHeure.parse("10/11/2012 10:00")));
    }

    @Test
    public void format() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("14/06/2012");
        assertEquals("", TldFunctions.format(null, "dd/MM/yyyy"));
        assertEquals("14/06/2012", TldFunctions.format(date, "dd/MM/yyyy"));
    }
}
