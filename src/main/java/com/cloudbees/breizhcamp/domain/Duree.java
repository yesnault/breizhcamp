package com.cloudbees.breizhcamp.domain;


public enum Duree {

    T_15(15, "Quickies(15 min)"), T_30(30, "Tools in action(30 min)"), T_60(60, "Conférence(1 heure)"), T_120(120, "Lab (2 heures)"), T_180(180, "Université(3 heures)");

    private int minute;
    private String htmlValeur;

    private Duree(int minute, String htmlValeur) {
        this.minute = minute;
        this.htmlValeur = htmlValeur;
    }


    public String getHtml() {
        return htmlValeur;
    }

    public int getMinute() {
        return minute;
    }

    public static Duree fromTime(int value) {
        for (Duree duree : values()) {
            if (duree.minute == value) {
                return duree;
            }
        }
        return null;
    }

}
