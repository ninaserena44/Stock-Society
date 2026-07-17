package com.stocksociety.stocksociety.model;

public enum ClothingSize {
    XS("XS"),
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("XXL"),
    ONE_SIZE("One Size");

    private final String displayName;

    ClothingSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}