package com.cloudbees.breizhcamp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: <a hef="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public enum Theme {

    DECOUVRIR("Découvrir"),
    PRATIQUER("Pratiquer"),
    APPROFONDIR("Approfondir");
    
    private String htmlValue;

    private Theme(String htmlValue) {
        this.htmlValue = htmlValue;
    }
    
    public static List<String> htmlValues() {
        List<String> htmlValues = new ArrayList<String>();
        for (Theme theme : Theme.values()) {
            htmlValues.add(theme.htmlValue);
        }
        return htmlValues;
    }
    
    public static Theme fromHtmlValue(String htmlValue) {
        for (Theme theme : values()) {
            if (theme.htmlValue.equals(htmlValue)) {
                return theme;
            }
        }
        return null;
    }

    public String getHtmlValue() {
        return htmlValue;
    }
}
