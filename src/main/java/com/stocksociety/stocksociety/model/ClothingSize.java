package com.stocksociety.stocksociety.model;

public enum ClothingSize {
    XS("XS"),
    S("S"),
    M("M"),
    L("L"),
    XL("XL"),
    XXL("XXL"),

    SIZE_4_5("4.5"),
    SIZE_5("5"),
    SIZE_5_5("5.5"),
    SIZE_6("6"),
    SIZE_6_5("6.5"),
    SIZE_7("7"),
    SIZE_7_5("7.5"),
    SIZE_8("8"),
    SIZE_8_5("8.5"),
    SIZE_9("9"),
    SIZE_9_5("9.5"),
    SIZE_10("10"),
    SIZE_10_5("10.5"),
    SIZE_11("11"),
    SIZE_11_5("11.5"),
    SIZE_12("12"),
    SIZE_13("13"),

    ONE_SIZE("One Size");

    private final String displayName;

    ClothingSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}