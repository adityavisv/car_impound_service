package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleStatus {
    REGISTERED("REGISTERED"),
    APPROVED_FOR_RELEASE("APPROVED_FOR_RELEASE"),
    RELEASED("RELEASED");

    private final String text;

    VehicleStatus(final String text) {
        this.text = text;
    }

    @JsonValue
    public String toValue() {
        return text;
    }
}
