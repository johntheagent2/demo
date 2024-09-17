package com.sparkminds.elasticSearch.enumeration;

import lombok.Getter;

@Getter
public enum BatteryType {
    LEAD_ACID("Lead-Acid"),
    AGM("AGM (Absorbent Glass Mat)"),
    GEL("Gel");

    private final String type;

    BatteryType(String type) {
        this.type = type;
    }
}
