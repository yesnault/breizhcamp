package com.cloudbees.breizhcamp.tld;


import com.cloudbees.breizhcamp.domain.Theme;

import java.util.Collection;
import java.util.List;

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
}
