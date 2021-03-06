package com.cloudbees.breizhcamp.tld;


import com.cloudbees.breizhcamp.domain.Duree;
import com.cloudbees.breizhcamp.domain.Theme;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getProfilUrl(String profil) {
        if (profil == null || profil.length() == 0) {
            return null;
        }
        if (profil.startsWith("+")) {
            return "https://plus.google.com/" + profil.substring(1);
        } else {
            return "https://twitter.com/#!/" + profil;
        }

    }

    public static String getDebut(Date startTime) {
        SimpleDateFormat sdfHeure = new SimpleDateFormat("HH:mm");

        if (startTime == null) {
            return "";
        }
        String start = sdfHeure.format(startTime);
        if (start.equals("00:00")) {
            return "";
        }
        return start;
    }
    
    public static Date getFin(Date startTime, int duree) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.MINUTE, duree);
        return cal.getTime();
    }


    public static String getDuree(int minute) {
        Duree duree = Duree.fromTime(minute);
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
    
    public static String formatChaine(String chaine) {
        if (chaine == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (char car : chaine.toCharArray()) {
            if (car == '\\') {
                continue;
            }
            if (car == '\'' || car == '"') {
                builder.append('\\');
            }
            builder.append(car);
        }
        return builder.toString();
    }
}
