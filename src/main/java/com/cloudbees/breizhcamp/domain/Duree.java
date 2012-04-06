package com.cloudbees.breizhcamp.domain;


public enum Duree {

    T_30(30, "30 min"), T_60(60, "1h"), T_90(90, "1h30 min"), T_120(120, "2h");

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