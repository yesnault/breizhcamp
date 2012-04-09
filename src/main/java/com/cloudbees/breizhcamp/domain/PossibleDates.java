package com.cloudbees.breizhcamp.domain;

/**
 * Dates possibles.
 */
public enum PossibleDates {
    
    _14_JUIN("14 Juin", "14/06/2012"),
    _15_JUIN("15 Juin", "15/06/2012");
    
    private String sortieFr;
    
    private String sortieDate;
    
    private PossibleDates(String sortieFr, String sortieDate) {
        this.sortieFr = sortieFr;
        this.sortieDate = sortieDate;
    }

    public String getSortieFr() {
        return sortieFr;
    }

    public String getSortieDate() {
        return sortieDate;
    }
}
