package com.stocksociety.stocksociety.model;

public enum Supplier {
    NORTHSTAR_DISTRIBUTION("NorthStar Distribution"),
    URBAN_APPAREL_SUPPLY("Urban Apparel Supply"),
    METRO_FOOTWEAR_GROUP("Metro Footwear Group"),
    GLOBAL_STYLE_WAREHOUSE("Global Style Warehouse"),
    DIRECT_FROM_BRAND("Direct from Brand");

    private final String displayName;

    Supplier(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}