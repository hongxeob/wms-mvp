package org.example.wmsmvp.product.feature;

public enum TemperatureZone {
    ROOM_TEMPERATURE("상온"),

    ;

    private final String description;

    TemperatureZone(String description) {
        this.description = description;
    }
}
