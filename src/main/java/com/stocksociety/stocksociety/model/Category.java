package com.stocksociety.stocksociety.model;

public enum Category {
    TOPS("Tops"),
    BOTTOMS("Bottoms"),
    OUTERWEAR("Outerwear"),
    FOOTWEAR("Footwear"),
    ACCESSORIES("Accessories");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}