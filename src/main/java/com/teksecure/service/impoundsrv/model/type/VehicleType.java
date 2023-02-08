package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleType {
    TRUCK("TRUCK"),
    CAR("CAR"),
    MOTORCYCLE("MOTORCYCLE");

    private final String text;

    VehicleType(final String text) {
        this.text = text;
    }

    @JsonValue
    public String toValue() {
        return text;
    }
}
