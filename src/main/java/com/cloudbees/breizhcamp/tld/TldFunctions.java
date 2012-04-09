package com.cloudbees.breizhcamp.tld;


import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.Theme;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class TldFunctions {
    
    public static boolean contains(Collection list, Object object) {
        return list != null && list.contains(object);
    }
    
    public static String getThemeName(Theme theme) {
        if (theme == null) {
            return null;
        }
        return theme.name();
    }

    public static String getDureeName(Duree duree) {
        if (duree == null) {
            return null;
        }
        return duree.name();
    }

    public static String getDebut(Date startTime) {
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");

        if (startTime == null) {
            return "";
        }
        String start = sdfHeure.format(startTime);
        if(start.equals("00:00")){
            return "";
        }
        return start;
    }


    public static String getDuree(int minute) {
        Duree duree= Duree.fromTime(minute);
        if (duree == null) {
            return null;
        }
        return duree.getHtml();
    }
    
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }
}
