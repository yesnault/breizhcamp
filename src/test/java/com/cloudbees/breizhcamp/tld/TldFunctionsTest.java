package com.cloudbees.breizhcamp.tld;


import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.Theme;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        assertEquals("T_30", TldFunctions.getDureeName(Duree.T_30));
        assertEquals("T_60", TldFunctions.getDureeName(Duree.T_60));
        assertEquals("T_90", TldFunctions.getDureeName(Duree.T_90));
        assertEquals("T_120", TldFunctions.getDureeName(Duree.T_120));
        assertNull(TldFunctions.getDureeName(null));
    }

    @Test
    public void getDuree() {
        assertEquals("30 min", TldFunctions.getDuree(30));
        assertEquals("1h", TldFunctions.getDuree(60));
        assertEquals("1h30 min", TldFunctions.getDuree(90));
        assertEquals("2h", TldFunctions.getDuree(120));

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
