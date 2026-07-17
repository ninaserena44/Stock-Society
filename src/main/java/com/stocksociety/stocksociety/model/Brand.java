package com.stocksociety.stocksociety.model;

public enum Brand {
    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma"),
    NEW_BALANCE("New Balance"),
    THE_NORTH_FACE("The North Face"),
    JORDAN("Jordan"),
    CONVERSE("Converse"),
    VANS("Vans"),
    OTHER("Other");

    private final String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
