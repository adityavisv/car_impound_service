package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleStatus {
    REGISTERED("REGISTERED"),
    PRE_RELEASE("PRE_RELEASE"),
    RELEASE("RELEASE");

    private final String text;

    VehicleStatus(final String text) {
        this.text = text;
    }

    @JsonValue
    public String toValue() {
        return text;
    }
}
